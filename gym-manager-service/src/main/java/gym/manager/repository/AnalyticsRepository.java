package gym.manager.repository;

import gym.manager.Analytics;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface AnalyticsRepository extends Repository<Analytics, String> {

  Analytics save(Analytics itemPopularity);

  Optional<Analytics> findById(String id);
}
