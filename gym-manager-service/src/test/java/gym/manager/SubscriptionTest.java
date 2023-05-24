package gym.manager;

import static org.junit.Assert.*;

import static akka.persistence.testkit.javadsl.EventSourcedBehaviorTestKit.CommandResultWithReply;

import akka.actor.testkit.typed.javadsl.TestKitJunitResource;
import akka.pattern.StatusReply;
import akka.persistence.testkit.javadsl.EventSourcedBehaviorTestKit;
import com.typesafe.config.ConfigFactory;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

public class SubscriptionTest {

    private static final String CART_ID = "testCart";

    @ClassRule
    public static final TestKitJunitResource testKit =
            new TestKitJunitResource(
                    ConfigFactory.parseString(
                                    "akka.actor.serialization-bindings {\n"
                                            + "  \"gym.manager.CborSerializable\" = jackson-cbor\n"
                                            + "}")
                            .withFallback(EventSourcedBehaviorTestKit.config()));

    private EventSourcedBehaviorTestKit<Subscription.Command, Subscription.Event, Subscription.State>
            eventSourcedTestKit =
            EventSourcedBehaviorTestKit.create(
                    testKit.system(), Subscription.create(CART_ID));

    @Before
    public void beforeEach() {
        eventSourcedTestKit.clear();
    }

    private final long ONE_MONTH = 2629800000L;

    @Test
    public void newCart() {
        long now = System.currentTimeMillis();
        CommandResultWithReply<
                Subscription.Command,
                Subscription.Event,
                Subscription.State,
                StatusReply<Subscription.Summary>>
                result =
                eventSourcedTestKit.runCommand(replyTo -> new Subscription.NewSubscription(CART_ID, now + ONE_MONTH, replyTo));
        assertTrue(result.reply().isSuccess());
        Subscription.Summary summary = result.reply().getValue();
        assertEquals("testCart", summary.cartId);
        assertEquals(now + ONE_MONTH, summary.expirationTime);
        assertEquals(new Subscription.NewSubscriptionEvent(CART_ID, now + ONE_MONTH), result.event());
    }

    @Test
    public void renewCart() {
        long now = System.currentTimeMillis();
        CommandResultWithReply<
                Subscription.Command,
                Subscription.Event,
                Subscription.State,
                StatusReply<Subscription.Summary>>
                addResult =
        eventSourcedTestKit.runCommand(replyTo -> new Subscription.NewSubscription(CART_ID, now + ONE_MONTH, replyTo));
        CommandResultWithReply<
                Subscription.Command,
                Subscription.Event,
                Subscription.State,
                StatusReply<Subscription.Summary>>
                renewResult =
                eventSourcedTestKit.runCommand(replyTo -> new Subscription.RenewSubscription(CART_ID, now + 2 * ONE_MONTH, replyTo));
        assertTrue(renewResult.reply().isSuccess());
        Subscription.Summary summary = renewResult.reply().getValue();
        assertEquals("testCart", summary.cartId);
        assertEquals(now + 2 * ONE_MONTH, summary.expirationTime);
        assertEquals(new Subscription.RenewSubscriptionEvent(CART_ID, now + 2 * ONE_MONTH), renewResult.event());
    }

    @Test
    public void getCart() {
        long now = System.currentTimeMillis();
        CommandResultWithReply<
                Subscription.Command,
                Subscription.Event,
                Subscription.State,
                StatusReply<Subscription.Summary>>
                addResult =
                eventSourcedTestKit.runCommand(replyTo -> new Subscription.NewSubscription(CART_ID, now + ONE_MONTH, replyTo));
        CommandResultWithReply<
                Subscription.Command,
                Subscription.Event,
                Subscription.State,
                Subscription.Summary>
                getResult =
                eventSourcedTestKit.runCommand(replyTo -> new Subscription.Get(replyTo));
        Subscription.Summary summary = getResult.reply();
        assertEquals("testCart", summary.cartId);
        assertEquals(now + ONE_MONTH, summary.expirationTime);
    }

    @Test
    public void enter() {
        long now = System.currentTimeMillis();
        CommandResultWithReply<
                Subscription.Command,
                Subscription.Event,
                Subscription.State,
                StatusReply<Subscription.Summary>>
                addResult =
                eventSourcedTestKit.runCommand(replyTo -> new Subscription.NewSubscription(CART_ID, now + ONE_MONTH, replyTo));
        CommandResultWithReply<
                Subscription.Command,
                Subscription.Event,
                Subscription.State,
                StatusReply<Subscription.Summary>>
                enterResult =
                eventSourcedTestKit.runCommand(replyTo -> new Subscription.Enter(CART_ID, replyTo));
        assertTrue(enterResult.reply().isSuccess());
        Subscription.Summary summary = enterResult.reply().getValue();
        assertEquals("testCart", summary.cartId);
        assertEquals(now + ONE_MONTH, summary.expirationTime);
        assertTrue(enterResult.event() instanceof Subscription.EnterEvent);
    }

    @Test
    public void exit() {
        long now = System.currentTimeMillis();
        CommandResultWithReply<
                Subscription.Command,
                Subscription.Event,
                Subscription.State,
                StatusReply<Subscription.Summary>>
                addResult =
                eventSourcedTestKit.runCommand(replyTo -> new Subscription.NewSubscription(CART_ID, now + ONE_MONTH, replyTo));
        CommandResultWithReply<
                Subscription.Command,
                Subscription.Event,
                Subscription.State,
                StatusReply<Subscription.Summary>>
                enterResult =
                eventSourcedTestKit.runCommand(replyTo -> new Subscription.Exit(CART_ID, replyTo));
        assertTrue(enterResult.reply().isSuccess());
        Subscription.Summary summary = enterResult.reply().getValue();
        assertEquals("testCart", summary.cartId);
        assertEquals(now + ONE_MONTH, summary.expirationTime);
        assertTrue(enterResult.event() instanceof Subscription.ExitEvent);
    }

}
