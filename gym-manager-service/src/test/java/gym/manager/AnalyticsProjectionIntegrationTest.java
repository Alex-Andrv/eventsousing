package gym.manager;

import akka.actor.testkit.typed.javadsl.TestKitJunitResource;
import akka.actor.testkit.typed.javadsl.TestProbe;
import akka.actor.typed.ActorSystem;
import akka.cluster.MemberStatus;
import akka.cluster.sharding.typed.javadsl.ClusterSharding;
import akka.cluster.sharding.typed.javadsl.EntityRef;
import akka.cluster.typed.Cluster;
import akka.cluster.typed.Join;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import gym.manager.repository.AnalyticsRepository;
import gym.manager.repository.SpringIntegration;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.jpa.JpaTransactionManager;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AnalyticsProjectionIntegrationTest {
    private final long ONE_MONTH = 2629800000L;
    private final long ONE_HOUR = 60 * 60 * 1000;
    private final long ONE_DAY = 24 * 60 * 60 * 1000;

  private static Config config() {
    return ConfigFactory.load("item-popularity-integration-test.conf");
  }

  @ClassRule public static final TestKitJunitResource testKit = new TestKitJunitResource(config());

  private static ActorSystem<?> system = testKit.system();
  private static AnalyticsRepository analyticsRepository;

  @BeforeClass
  public static void beforeClass() throws Exception {

    ApplicationContext springContext = SpringIntegration.applicationContext(system);
    analyticsRepository = springContext.getBean(AnalyticsRepository.class);
    JpaTransactionManager transactionManager = springContext.getBean(JpaTransactionManager.class);
    // create schemas
    CreateTableTestUtils.createTables(transactionManager, system);

    Subscription.init(system);

    AnalyticsProjection.init(system, transactionManager, analyticsRepository);

    // form a single node cluster and make sure that completes before running the test
    Cluster node = Cluster.get(system);
    node.manager().tell(Join.create(node.selfMember().address()));

    // let the node join and become Up
    TestProbe<Object> probe = testKit.createTestProbe();
    probe.awaitAssert(
        () -> {
          assertEquals(MemberStatus.up(), node.selfMember().status());
          return null;
        });
  }

  @Test
  public void consumeCartEventsAndUpdatePopularityCount() throws Exception {
    ClusterSharding sharding = ClusterSharding.get(system);

    final String cartId1 = "cart1";

    EntityRef<Subscription.Command> cart1 = sharding.entityRefFor(Subscription.USERS_ENTITY_TYPE_KEY, cartId1);

    final Duration timeout = Duration.ofSeconds(3);

    long now = System.currentTimeMillis();

    CompletionStage<Subscription.Summary> reply1 =
        cart1.askWithStatus(replyTo -> new Subscription.NewSubscription(cartId1, now + ONE_MONTH, replyTo), timeout);
    Subscription.Summary summary1 = reply1.toCompletableFuture().get(3, SECONDS);
    assertEquals(cartId1, summary1.cartId);

      CompletionStage<Subscription.Summary> reply2 =
              cart1.askWithStatus(replyTo -> new Subscription.Enter(cartId1, replyTo), timeout);

    TestProbe<Object> probe = testKit.createTestProbe();
    probe.awaitAssert(
        () -> {
          Optional<Analytics> analytics = analyticsRepository.findById(cartId1);
          assertTrue(analytics.isPresent());
          assertEquals(0L, analytics.get().getCount());
          return null;
        });
  }
}
