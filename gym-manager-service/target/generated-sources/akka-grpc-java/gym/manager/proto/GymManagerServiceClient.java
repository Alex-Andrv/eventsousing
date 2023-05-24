
// Generated by Akka gRPC. DO NOT EDIT.
package gym.manager.proto;

import akka.actor.ClassicActorSystemProvider;
import akka.stream.Materializer;
import akka.stream.SystemMaterializer;

import akka.grpc.internal.*;
import akka.grpc.GrpcChannel;
import akka.grpc.GrpcClientCloseException;
import akka.grpc.GrpcClientSettings;
import akka.grpc.javadsl.AkkaGrpcClient;

import io.grpc.MethodDescriptor;

import static gym.manager.proto.GymManagerService.Serializers.*;

import scala.concurrent.ExecutionContext;

import akka.grpc.AkkaGrpcGenerated;


import akka.grpc.javadsl.SingleResponseRequestBuilder;


@AkkaGrpcGenerated
public abstract class GymManagerServiceClient extends GymManagerServiceClientPowerApi implements GymManagerService, AkkaGrpcClient {
  public static final GymManagerServiceClient create(GrpcClientSettings settings, ClassicActorSystemProvider sys) {
    return new DefaultGymManagerServiceClient(akka.grpc.GrpcChannel$.MODULE$.apply(settings, sys), true, sys);
  }

  public static final GymManagerServiceClient create(GrpcChannel channel, ClassicActorSystemProvider sys) {
    return new DefaultGymManagerServiceClient(channel, false, sys);
  }

  @AkkaGrpcGenerated
  protected final static class DefaultGymManagerServiceClient extends GymManagerServiceClient {

      private final GrpcChannel channel;
      private final boolean isChannelOwned;
      private final GrpcClientSettings settings;
      private final io.grpc.CallOptions options;
      private final Materializer mat;
      private final ExecutionContext ec;

      private DefaultGymManagerServiceClient(GrpcChannel channel, boolean isChannelOwned, ClassicActorSystemProvider sys) {
        this.channel = channel;
        this.isChannelOwned = isChannelOwned;
        this.settings = channel.settings();
        this.mat = SystemMaterializer.get(sys).materializer();
        this.ec = sys.classicSystem().dispatcher();
        this.options = NettyClientUtils.callOptions(settings);

        sys.classicSystem().getWhenTerminated().whenComplete((v, e) -> close());
      }

  
    
      private final SingleResponseRequestBuilder<gym.manager.proto.NewSubscriptionRequest, gym.manager.proto.Subscription> newSubscriptionRequestBuilder(akka.grpc.internal.InternalChannel channel){
        return new JavaUnaryRequestBuilder<>(newSubscriptionDescriptor, channel, options, settings, ec);
      }
    
  
    
      private final SingleResponseRequestBuilder<gym.manager.proto.RenewSubscriptionRequest, gym.manager.proto.Subscription> renewSubscriptionRequestBuilder(akka.grpc.internal.InternalChannel channel){
        return new JavaUnaryRequestBuilder<>(renewSubscriptionDescriptor, channel, options, settings, ec);
      }
    
  
    
      private final SingleResponseRequestBuilder<gym.manager.proto.GetSubscriptionRequest, gym.manager.proto.Subscription> getSubscriptionRequestBuilder(akka.grpc.internal.InternalChannel channel){
        return new JavaUnaryRequestBuilder<>(getSubscriptionDescriptor, channel, options, settings, ec);
      }
    
  
    
      private final SingleResponseRequestBuilder<gym.manager.proto.GetAnalyticsRequest, gym.manager.proto.GetAnalyticsResponse> getAnalyticsRequestBuilder(akka.grpc.internal.InternalChannel channel){
        return new JavaUnaryRequestBuilder<>(getAnalyticsDescriptor, channel, options, settings, ec);
      }
    
  

      

        /**
         * For access to method metadata use the parameterless version of newSubscription
         */
        public java.util.concurrent.CompletionStage<gym.manager.proto.Subscription> newSubscription(gym.manager.proto.NewSubscriptionRequest request) {
          return newSubscription().invoke(request);
        }

        /**
         * Lower level "lifted" version of the method, giving access to request metadata etc.
         * prefer newSubscription(gym.manager.proto.NewSubscriptionRequest) if possible.
         */
        
          public SingleResponseRequestBuilder<gym.manager.proto.NewSubscriptionRequest, gym.manager.proto.Subscription> newSubscription()
        
        {
          return newSubscriptionRequestBuilder(channel.internalChannel());
        }
      

        /**
         * For access to method metadata use the parameterless version of renewSubscription
         */
        public java.util.concurrent.CompletionStage<gym.manager.proto.Subscription> renewSubscription(gym.manager.proto.RenewSubscriptionRequest request) {
          return renewSubscription().invoke(request);
        }

        /**
         * Lower level "lifted" version of the method, giving access to request metadata etc.
         * prefer renewSubscription(gym.manager.proto.RenewSubscriptionRequest) if possible.
         */
        
          public SingleResponseRequestBuilder<gym.manager.proto.RenewSubscriptionRequest, gym.manager.proto.Subscription> renewSubscription()
        
        {
          return renewSubscriptionRequestBuilder(channel.internalChannel());
        }
      

        /**
         * For access to method metadata use the parameterless version of getSubscription
         */
        public java.util.concurrent.CompletionStage<gym.manager.proto.Subscription> getSubscription(gym.manager.proto.GetSubscriptionRequest request) {
          return getSubscription().invoke(request);
        }

        /**
         * Lower level "lifted" version of the method, giving access to request metadata etc.
         * prefer getSubscription(gym.manager.proto.GetSubscriptionRequest) if possible.
         */
        
          public SingleResponseRequestBuilder<gym.manager.proto.GetSubscriptionRequest, gym.manager.proto.Subscription> getSubscription()
        
        {
          return getSubscriptionRequestBuilder(channel.internalChannel());
        }
      

        /**
         * For access to method metadata use the parameterless version of getAnalytics
         */
        public java.util.concurrent.CompletionStage<gym.manager.proto.GetAnalyticsResponse> getAnalytics(gym.manager.proto.GetAnalyticsRequest request) {
          return getAnalytics().invoke(request);
        }

        /**
         * Lower level "lifted" version of the method, giving access to request metadata etc.
         * prefer getAnalytics(gym.manager.proto.GetAnalyticsRequest) if possible.
         */
        
          public SingleResponseRequestBuilder<gym.manager.proto.GetAnalyticsRequest, gym.manager.proto.GetAnalyticsResponse> getAnalytics()
        
        {
          return getAnalyticsRequestBuilder(channel.internalChannel());
        }
      

      
        private static MethodDescriptor<gym.manager.proto.NewSubscriptionRequest, gym.manager.proto.Subscription> newSubscriptionDescriptor =
          MethodDescriptor.<gym.manager.proto.NewSubscriptionRequest, gym.manager.proto.Subscription>newBuilder()
            .setType(
   MethodDescriptor.MethodType.UNARY 
  
  
  
)
            .setFullMethodName(MethodDescriptor.generateFullMethodName("gymmanager.GymManagerService", "NewSubscription"))
            .setRequestMarshaller(new ProtoMarshaller<gym.manager.proto.NewSubscriptionRequest>(NewSubscriptionRequestSerializer))
            .setResponseMarshaller(new ProtoMarshaller<gym.manager.proto.Subscription>(SubscriptionSerializer))
            .setSampledToLocalTracing(true)
            .build();
        
        private static MethodDescriptor<gym.manager.proto.RenewSubscriptionRequest, gym.manager.proto.Subscription> renewSubscriptionDescriptor =
          MethodDescriptor.<gym.manager.proto.RenewSubscriptionRequest, gym.manager.proto.Subscription>newBuilder()
            .setType(
   MethodDescriptor.MethodType.UNARY 
  
  
  
)
            .setFullMethodName(MethodDescriptor.generateFullMethodName("gymmanager.GymManagerService", "RenewSubscription"))
            .setRequestMarshaller(new ProtoMarshaller<gym.manager.proto.RenewSubscriptionRequest>(RenewSubscriptionRequestSerializer))
            .setResponseMarshaller(new ProtoMarshaller<gym.manager.proto.Subscription>(SubscriptionSerializer))
            .setSampledToLocalTracing(true)
            .build();
        
        private static MethodDescriptor<gym.manager.proto.GetSubscriptionRequest, gym.manager.proto.Subscription> getSubscriptionDescriptor =
          MethodDescriptor.<gym.manager.proto.GetSubscriptionRequest, gym.manager.proto.Subscription>newBuilder()
            .setType(
   MethodDescriptor.MethodType.UNARY 
  
  
  
)
            .setFullMethodName(MethodDescriptor.generateFullMethodName("gymmanager.GymManagerService", "GetSubscription"))
            .setRequestMarshaller(new ProtoMarshaller<gym.manager.proto.GetSubscriptionRequest>(GetSubscriptionRequestSerializer))
            .setResponseMarshaller(new ProtoMarshaller<gym.manager.proto.Subscription>(SubscriptionSerializer))
            .setSampledToLocalTracing(true)
            .build();
        
        private static MethodDescriptor<gym.manager.proto.GetAnalyticsRequest, gym.manager.proto.GetAnalyticsResponse> getAnalyticsDescriptor =
          MethodDescriptor.<gym.manager.proto.GetAnalyticsRequest, gym.manager.proto.GetAnalyticsResponse>newBuilder()
            .setType(
   MethodDescriptor.MethodType.UNARY 
  
  
  
)
            .setFullMethodName(MethodDescriptor.generateFullMethodName("gymmanager.GymManagerService", "GetAnalytics"))
            .setRequestMarshaller(new ProtoMarshaller<gym.manager.proto.GetAnalyticsRequest>(GetAnalyticsRequestSerializer))
            .setResponseMarshaller(new ProtoMarshaller<gym.manager.proto.GetAnalyticsResponse>(GetAnalyticsResponseSerializer))
            .setSampledToLocalTracing(true)
            .build();
        

      /**
       * Initiates a shutdown in which preexisting and new calls are cancelled.
       */
      public java.util.concurrent.CompletionStage<akka.Done> close() {
        if (isChannelOwned) {
          return channel.closeCS();
        } else {
          throw new GrpcClientCloseException();
        }
      }

     /**
      * Returns a CompletionState that completes successfully when shutdown via close()
      * or exceptionally if a connection can not be established after maxConnectionAttempts.
      */
      public java.util.concurrent.CompletionStage<akka.Done> closed() {
        return channel.closedCS();
      }
  }

}



