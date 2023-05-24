package gym.manager;

import akka.actor.typed.ActorSystem;
import akka.cluster.sharding.typed.ShardedDaemonProcessSettings;
import akka.cluster.sharding.typed.javadsl.ShardedDaemonProcess;
import akka.persistence.jdbc.query.javadsl.JdbcReadJournal;
import akka.persistence.query.Offset;
import akka.projection.ProjectionBehavior;
import akka.projection.ProjectionId;
import akka.projection.eventsourced.EventEnvelope;
import akka.projection.eventsourced.javadsl.EventSourcedProvider;
import akka.projection.javadsl.ExactlyOnceProjection;
import akka.projection.javadsl.SourceProvider;
import akka.projection.jdbc.javadsl.JdbcProjection;
import java.util.Optional;

import gym.manager.repository.AnalyticsRepository;
import gym.manager.repository.HibernateJdbcSession;
import org.springframework.orm.jpa.JpaTransactionManager;

public final class AnalyticsProjection {

  private AnalyticsProjection() {}

  public static void init(
          ActorSystem<?> system,
          JpaTransactionManager transactionManager,
          AnalyticsRepository repository) {

    ShardedDaemonProcess.get(system)
            .init(
                    ProjectionBehavior.Command.class,
                    "AnalyticsProjection",
                    Subscription.TAGS.size(),
                    index ->
                            ProjectionBehavior.create(
                                    createProjectionFor(system, transactionManager, repository, index)),
                    ShardedDaemonProcessSettings.create(system),
                    Optional.of(ProjectionBehavior.stopMessage()));
  }


  private static ExactlyOnceProjection<Offset, EventEnvelope<Subscription.Event>> createProjectionFor(
          ActorSystem<?> system,
          JpaTransactionManager transactionManager,
          AnalyticsRepository repository,
          int index) {

    String tag = Subscription.TAGS.get(index);

    SourceProvider<Offset, EventEnvelope<Subscription.Event>> sourceProvider =
            EventSourcedProvider.eventsByTag(
                    system,
                    JdbcReadJournal.Identifier(),
                    tag);

    return JdbcProjection.exactlyOnce(
            ProjectionId.of("AnalyticsProjection", tag),
            sourceProvider,
            () -> new HibernateJdbcSession(transactionManager),
            () -> new AnalyticsProjectionHandler(tag, repository),
            system);
  }
}

