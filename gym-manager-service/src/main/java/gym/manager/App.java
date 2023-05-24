package gym.manager;

import akka.actor.typed.ActorSystem;
import akka.actor.typed.javadsl.Behaviors;
import akka.management.cluster.bootstrap.ClusterBootstrap;
import akka.management.javadsl.AkkaManagement;
import com.typesafe.config.Config;
import gym.manager.proto.GymManagerService;
import gym.manager.repository.AnalyticsRepository;
import gym.manager.repository.SpringIntegration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.jpa.JpaTransactionManager;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        ActorSystem<Void> system = ActorSystem.create(Behaviors.empty(), "GymManagerService");
        try {
            init(system);
        } catch (Exception e) {
            logger.error("Terminating due to initialization failure.", e);
            system.terminate();
        }
    }

    public static void init(ActorSystem<Void> system) {
        AkkaManagement.get(system).start();
        ClusterBootstrap.get(system).start();

        Config config = system.settings().config();
        String grpcInterface = config.getString("gym-manager-service.grpc.interface");
        int grpcPort = config.getInt("gym-manager-service.grpc.port");

        ApplicationContext springContext = SpringIntegration.applicationContext(system);

        JpaTransactionManager transactionManager = springContext.getBean(JpaTransactionManager.class);

        AnalyticsRepository analyticsRepository =
                springContext.getBean(AnalyticsRepository.class);

        AnalyticsProjection.init(system, transactionManager, analyticsRepository);

        GymManagerService grpcService = new GymManagerServiceImpl(system, analyticsRepository);
        GymManagerServer.start(grpcInterface, grpcPort, system, grpcService);
    }
}