package gym.manager;

import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.SupervisorStrategy;
import akka.actor.typed.javadsl.Behaviors;
import akka.cluster.sharding.typed.javadsl.ClusterSharding;
import akka.cluster.sharding.typed.javadsl.Entity;
import akka.cluster.sharding.typed.javadsl.EntityTypeKey;
import akka.pattern.StatusReply;
import akka.persistence.typed.PersistenceId;
import akka.persistence.typed.javadsl.*;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.time.Duration;
import java.util.*;

public class Subscription extends EventSourcedBehaviorWithEnforcedReplies<
        Subscription.Command, Subscription.Event, Subscription.State> {

    public static final List<String> TAGS =
            Collections.unmodifiableList(
                    Arrays.asList("enter-exit"));

    public static void init(ActorSystem<?> system) {
        ClusterSharding.get(system)
                .init(
                        Entity.of(
                                USERS_ENTITY_TYPE_KEY,
                                entityContext -> {
                                    return Subscription.create(entityContext.getEntityId());
                                }));
    }

    @Override
    public Set<String> tagsFor(Event event) {
        return Collections.singleton(TAGS.get(0));
    }

    private final static long ONE_MONTH = 2629800000L;

    private final String cartId;
    private Subscription(String cartId) {
        super(
                PersistenceId.of(USERS_ENTITY_TYPE_KEY.name(), cartId),
                SupervisorStrategy.restartWithBackoff(Duration.ofMillis(200), Duration.ofSeconds(5), 0.1));
        this.cartId = cartId;
    }

    public static Behavior<Command> create(String cartId) {
        return Behaviors.setup(
                ctx -> EventSourcedBehavior.start(new Subscription(cartId), ctx));
    }

    public static final EntityTypeKey<Command> USERS_ENTITY_TYPE_KEY =
            EntityTypeKey.create(Command.class, "Users");

    @Override
    public State emptyState() {
        return null;
    }

    @Override
    public CommandHandlerWithReply<Command, Event, State> commandHandler() {
        var builder = newCommandHandlerWithReplyBuilder();

        builder.forStateType(State.class)
                .onCommand(NewSubscription.class, this::handlerNewSubscription)
                .onCommand(RenewSubscription.class, this::handlerRenewSubscription)
                .onCommand(Get.class, (state, cmd) -> Effect().reply(cmd.replyTo, state.toSummary()))
                .onCommand(Enter.class, this::handlerEnter)
                .onCommand(Exit.class, this::handlerExit);

        return builder.build();
    }

    private ReplyEffect<Event, State> handlerExit(State state, Exit cmd) {
        long now = System.currentTimeMillis();
        return Effect().persist(new ExitEvent(cartId, now))
                    .thenReply(cmd.replyTo, updatedCart -> StatusReply.success(updatedCart.toSummary()));
    }

    private ReplyEffect<Event, State> handlerEnter(State state, Enter cmd) {
        long now = System.currentTimeMillis();
        if (state.expirationTime > now) {
            return Effect().persist(new EnterEvent(cartId, now))
                    .thenReply(cmd.replyTo, updatedCart -> StatusReply.success(updatedCart.toSummary()));
        } else {
            return Effect()
                    .reply(
                            cmd.replyTo,
                            StatusReply.error(
                                    "Cart '" + cmd.cartId + "' was expire"));
        }
    }

    private ReplyEffect<Event, State> handlerRenewSubscription(State state, RenewSubscription cmd) {
        if (state == null) {
            return Effect()
                    .reply(
                            cmd.replyTo,
                            StatusReply.error(
                                    "Cart '" + cmd.cartId + "' wasn't already create"));
        } else {
            return Effect()
                    .persist(new RenewSubscriptionEvent(cartId, cmd.expirationTime))
                    .thenReply(cmd.replyTo, updatedCart -> StatusReply.success(updatedCart.toSummary()));
        }
    }

    private ReplyEffect<Event, State> handlerNewSubscription(State state, NewSubscription cmd) {
        if (state != null) {
            return Effect()
                    .reply(
                            cmd.replyTo,
                            StatusReply.error(
                                    "Cart '" + cmd.cartId + "' was already create"));
        } else {
            return Effect()
                    .persist(new NewSubscriptionEvent(cartId, cmd.expirationTime))
                    .thenReply(cmd.replyTo, updatedCart -> StatusReply.success(updatedCart.toSummary()));
        }
    }

    @Override
    public EventHandler<State, Event> eventHandler() {
        return newEventHandlerBuilder()
                .forAnyState()
                .onEvent(NewSubscriptionEvent.class, (state, evt) -> new State(evt.cartId, evt.expirationTime))
                .onEvent(RenewSubscriptionEvent.class, (state, evt) -> state.updateExpirationTime(evt.expirationTime))
                .onEvent(EnterEvent.class, (state, evt) -> state)
                .onEvent(ExitEvent.class, (state, evt) -> state)
                .build();
    }


    /** This interface defines all the commands (messages) that the ShoppingCart actor supports. */
    interface Command extends CborSerializable {}

    static final class State implements CborSerializable {

        private final String cartId;
        private long expirationTime;

        public State(String cartId, long expirationTime) {
            this.cartId = cartId;
            this.expirationTime = expirationTime;
        }

        public Summary toSummary() {
            return new Summary(cartId, expirationTime);
        }

        public State updateExpirationTime(long expirationTime) {
            this.expirationTime = expirationTime;
            return this;
        }
    }

    abstract static class Event implements CborSerializable {
        public final String cartId;

        public Event(String cartId) {
            this.cartId = cartId;
        }
    }

    static final class RenewSubscriptionEvent extends Event {
        final long expirationTime;

        public RenewSubscriptionEvent(String cartId, long expirationTime) {
            super(cartId);
            this.expirationTime = expirationTime;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RenewSubscriptionEvent that = (RenewSubscriptionEvent) o;
            return expirationTime == that.expirationTime && Objects.equals(cartId, that.cartId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(expirationTime) + Objects.hash(cartId);
        }
    }

    static final class NewSubscriptionEvent extends Event {
        final long expirationTime;

        public NewSubscriptionEvent(String cartId, long expirationTime) {
            super(cartId);
            this.expirationTime = expirationTime;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            NewSubscriptionEvent that = (NewSubscriptionEvent) o;
            return expirationTime == that.expirationTime && Objects.equals(cartId, that.cartId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(expirationTime) + Objects.hash(cartId);
        }
    }

    static final class EnterEvent extends Event {
        public final long enterTime;

        public EnterEvent(String cartId, long enterTime) {
            super(cartId);
            this.enterTime = enterTime;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            EnterEvent other = (EnterEvent) o;

            if (!cartId.equals(other.cartId)) return false;
            return enterTime == other.enterTime;
        }

        @Override
        public int hashCode() {
            int result = cartId.hashCode();
            return result + Long.hashCode(enterTime);
        }
    }

    static final class ExitEvent extends Event {
        public final long enterTime;

        public ExitEvent(String cartId, long enterTime) {
            super(cartId);
            this.enterTime = enterTime;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ExitEvent other = (ExitEvent) o;

            if (!cartId.equals(other.cartId)) return false;
            return enterTime == other.enterTime;
        }

        @Override
        public int hashCode() {
            int result = cartId.hashCode();
            return result + Long.hashCode(enterTime);
        }
    }

    public static class NewSubscription implements Command {
        final String cartId;
        final long expirationTime;
        final ActorRef<StatusReply<Summary>> replyTo;

        public NewSubscription(String cartId, long expirationTime, ActorRef<StatusReply<Summary>> replyTo) {
            this.cartId = cartId;
            this.expirationTime = expirationTime;
            this.replyTo = replyTo;
        }
    }

    public static final class Enter implements Command {

        final String cartId;
        final ActorRef<StatusReply<Summary>> replyTo;

        public Enter(String cartId, ActorRef<StatusReply<Summary>> replyTo) {
            this.cartId = cartId;
            this.replyTo = replyTo;
        }
    }

    public static final class Exit implements Command {
        final String cartId;
        final ActorRef<StatusReply<Summary>> replyTo;

        public Exit(String cartId, ActorRef<StatusReply<Summary>> replyTo) {
            this.cartId = cartId;
            this.replyTo = replyTo;
        }
    }


    /** A command to get the current state of the shopping cart. */
    public static final class Get implements Command {
        final ActorRef<Summary> replyTo;

        @JsonCreator
        public Get(ActorRef<Summary> replyTo) {
            this.replyTo = replyTo;
        }
    }

    public static class RenewSubscription implements Command {
        final String cartId;
        final long expirationTime;
        final ActorRef<StatusReply<Summary>> replyTo;

        public RenewSubscription(String cartId, long expirationTime, ActorRef<StatusReply<Summary>> replyTo) {
            this.cartId = cartId;
            this.expirationTime = expirationTime;
            this.replyTo = replyTo;
        }
    }


    /** Summary of the shopping cart state, used in reply messages. */
    public static final class Summary implements CborSerializable {
        final String cartId;
        final long expirationTime;

        public Summary(String cartId, long expirationTime) {
            // defensive copy since items is a mutable object
            this.cartId = cartId;
            this.expirationTime = expirationTime;
        }
    }
}
