
// Generated by Akka gRPC. DO NOT EDIT.
package gym.manager.proto;

import akka.grpc.ProtobufSerializer;
import akka.grpc.javadsl.GoogleProtobufSerializer;

import akka.grpc.AkkaGrpcGenerated;


public interface GymManagerService {
  
  
  java.util.concurrent.CompletionStage<gym.manager.proto.Subscription> newSubscription(gym.manager.proto.NewSubscriptionRequest in);
  
  
  java.util.concurrent.CompletionStage<gym.manager.proto.Subscription> renewSubscription(gym.manager.proto.RenewSubscriptionRequest in);
  
  
  java.util.concurrent.CompletionStage<gym.manager.proto.Subscription> getSubscription(gym.manager.proto.GetSubscriptionRequest in);
  
  
  java.util.concurrent.CompletionStage<gym.manager.proto.GetAnalyticsResponse> getAnalytics(gym.manager.proto.GetAnalyticsRequest in);
  

  static String name = "gymmanager.GymManagerService";
  static akka.grpc.ServiceDescription description = new akka.grpc.internal.ServiceDescriptionImpl(name, ManagerService.getDescriptor());

  @AkkaGrpcGenerated
  public static class Serializers {
    
      public static ProtobufSerializer<gym.manager.proto.NewSubscriptionRequest> NewSubscriptionRequestSerializer = new GoogleProtobufSerializer<>(gym.manager.proto.NewSubscriptionRequest.parser());
    
      public static ProtobufSerializer<gym.manager.proto.RenewSubscriptionRequest> RenewSubscriptionRequestSerializer = new GoogleProtobufSerializer<>(gym.manager.proto.RenewSubscriptionRequest.parser());
    
      public static ProtobufSerializer<gym.manager.proto.GetSubscriptionRequest> GetSubscriptionRequestSerializer = new GoogleProtobufSerializer<>(gym.manager.proto.GetSubscriptionRequest.parser());
    
      public static ProtobufSerializer<gym.manager.proto.GetAnalyticsRequest> GetAnalyticsRequestSerializer = new GoogleProtobufSerializer<>(gym.manager.proto.GetAnalyticsRequest.parser());
    
      public static ProtobufSerializer<gym.manager.proto.Subscription> SubscriptionSerializer = new GoogleProtobufSerializer<>(gym.manager.proto.Subscription.parser());
    
      public static ProtobufSerializer<gym.manager.proto.GetAnalyticsResponse> GetAnalyticsResponseSerializer = new GoogleProtobufSerializer<>(gym.manager.proto.GetAnalyticsResponse.parser());
    
  }
}