package gym.manager;

import akka.actor.typed.ActorSystem;
import akka.actor.typed.DispatcherSelector;
import akka.cluster.sharding.typed.javadsl.ClusterSharding;
import akka.cluster.sharding.typed.javadsl.EntityRef;
import akka.grpc.GrpcServiceException;
import gym.manager.proto.*;
import gym.manager.repository.AnalyticsRepository;
import io.grpc.Status;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeoutException;

public class GymManagerServiceImpl implements GymManagerService {
    private final AnalyticsRepository repository;

    private final Executor blockingJdbcExecutor;
    private final ClusterSharding sharding;

    private final Duration timeout;

    public GymManagerServiceImpl(
            ActorSystem<?> system, AnalyticsRepository repository) {

        DispatcherSelector dispatcherSelector =
                DispatcherSelector.fromConfig("akka.projection.jdbc.blocking-jdbc-dispatcher");
        this.blockingJdbcExecutor = system.dispatchers().lookup(dispatcherSelector);
        this.repository = repository;
        sharding = ClusterSharding.get(system);
        timeout = system.settings().config().getDuration("gym-manager-service.ask-timeout");
    }


    @Override
    public CompletionStage<gym.manager.proto.Subscription> newSubscription(NewSubscriptionRequest in) {
        EntityRef<Subscription.Command> entityRef =
                sharding.entityRefFor(Subscription.USERS_ENTITY_TYPE_KEY, in.getCartId());
        CompletionStage<Subscription.Summary> reply =
                entityRef.askWithStatus(
                        replyTo -> new Subscription.NewSubscription(in.getCartId(), in.getExpirationTime(), replyTo),
                        timeout);
        CompletionStage<gym.manager.proto.Subscription> subscription = reply.thenApply(GymManagerServiceImpl::toProtoSubscription);
        return convertError(subscription);
    }

    @Override
    public CompletionStage<gym.manager.proto.Subscription> renewSubscription(RenewSubscriptionRequest in) {
        return null;
    }

    @Override
    public CompletionStage<gym.manager.proto.Subscription> getSubscription(GetSubscriptionRequest in) {
        return null;
    }

    private static gym.manager.proto.Subscription toProtoSubscription(Subscription.Summary cart) {
        return gym.manager.proto.Subscription.newBuilder().setCartId(cart.cartId).setExpirationTime(cart.expirationTime).build();
    }

    private static <T> CompletionStage<T> convertError(CompletionStage<T> response) {
        return response.exceptionally(
                ex -> {
                    if (ex instanceof TimeoutException) {
                        throw new GrpcServiceException(
                                Status.UNAVAILABLE.withDescription("Operation timed out"));
                    } else {
                        throw new GrpcServiceException(
                                Status.INVALID_ARGUMENT.withDescription(ex.getMessage()));
                    }
                });
    }

    @Override
    public CompletionStage<GetAnalyticsResponse> getAnalytics(GetAnalyticsRequest in) {

        CompletionStage<Optional<Analytics>> analytics =
                CompletableFuture.supplyAsync(
                        () -> repository.findById(in.getCartId()), blockingJdbcExecutor);

        return analytics.thenApply(
                analytic -> {
                    long count = analytic.map(Analytics::getCount).orElse(0L);
                    String cartId = analytic.map(Analytics::getCartId).orElse("");
                    boolean isIn = analytic.map(Analytics::getIn).orElse(false);
                    return GetAnalyticsResponse.newBuilder()
                            .setCartId(cartId)
                            .setIn(isIn)
                            .setCount(count).build();
                });
    }
}
