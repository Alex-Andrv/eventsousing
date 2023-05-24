package gym.manager;

import akka.Done;
import akka.NotUsed;
import akka.actor.testkit.typed.javadsl.TestKitJunitResource;
import akka.persistence.query.Offset;
import akka.projection.ProjectionId;
import akka.projection.eventsourced.EventEnvelope;
import akka.projection.javadsl.Handler;
import akka.projection.testkit.javadsl.ProjectionTestKit;
import akka.projection.testkit.javadsl.TestProjection;
import akka.projection.testkit.javadsl.TestSourceProvider;
import akka.stream.javadsl.Source;
import gym.manager.repository.AnalyticsRepository;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static org.junit.Assert.assertEquals;

public class AnalyticsProjectionTest {
  // stub out the db layer and simulate recording item count updates
  private final long ONE_MONTH = 2629800000L;

  private final long ONE_HOUR = 60 * 60 * 1000;

  private final long ONE_DAY = 24 * 60 * 60 * 1000;

  static class TestAnalyticsRepository implements AnalyticsRepository {
    private final Map<String, Analytics> itemPops = new HashMap<>();

    @Override
    public Analytics save(Analytics analytics) {
      itemPops.put(analytics.getCartId(), analytics);
      return analytics;
    }

    @Override
    public Optional<Analytics> findById(String id) {
      return Optional.ofNullable(itemPops.get(id));
    }
  }

  @ClassRule public static final TestKitJunitResource testKit = new TestKitJunitResource();

  static final ProjectionTestKit projectionTestKit = ProjectionTestKit.create(testKit.system());

  private EventEnvelope<Subscription.Event> createEnvelope(Subscription.Event event, long seqNo) {
    return new EventEnvelope<>(Offset.sequence(seqNo), "persistenceId", seqNo, event, 0L);
  }

  private Handler<EventEnvelope<Subscription.Event>> toAsyncHandler(
          AnalyticsProjectionHandler analyticsHandler) {
    return new Handler<EventEnvelope<Subscription.Event>>() {
      @Override
      public CompletionStage<Done> process(EventEnvelope<Subscription.Event> eventEventEnvelope)
          throws Exception {
        return CompletableFuture.supplyAsync(
            () -> {
              analyticsHandler.process(
                  // session = null is safe.
                  // The real handler never uses the session. The connection is provided to the repo
                  // by Spring itself
                  null, eventEventEnvelope);
              return Done.getInstance();
            });
      }
    };
  }

  @Test
  public void itemPopularityUpdateUpdate() {
    long now = System.currentTimeMillis();
    Source<EventEnvelope<Subscription.Event>, NotUsed> events =
        Source.from(
            Arrays.asList(
                createEnvelope(new Subscription.NewSubscriptionEvent("a7079", now + ONE_MONTH), 0L),
                createEnvelope(
                    new Subscription.RenewSubscriptionEvent("a7079", now + 2 * ONE_MONTH), 1L),
                createEnvelope(
                    new Subscription.EnterEvent("a7079", now),
                    2L),
                createEnvelope(new Subscription.ExitEvent("a7079", now + ONE_HOUR), 3L),
                createEnvelope(new Subscription.EnterEvent("a7079", now + 2 * ONE_DAY), 4L),
                createEnvelope(new Subscription.ExitEvent("a7079", now + 2 * ONE_DAY + ONE_HOUR), 5L)));

    TestAnalyticsRepository repository = new TestAnalyticsRepository();
    ProjectionId projectionId = ProjectionId.of("analytics", "enter-exit");

    TestSourceProvider<Offset, EventEnvelope<Subscription.Event>> sourceProvider =
        TestSourceProvider.create(events, EventEnvelope::offset);

    TestProjection<Offset, EventEnvelope<Subscription.Event>> projection =
        TestProjection.create(
            projectionId,
            sourceProvider,
            () -> toAsyncHandler(new AnalyticsProjectionHandler("enter-exit", repository)));

    projectionTestKit.run(
        projection,
        () -> {
          assertEquals(1, repository.itemPops.size());
          assertEquals(2L, repository.itemPops.get("a7079").getCount());
          assertEquals(true, repository.itemPops.get("a7079").getIn());
        });
  }
}
