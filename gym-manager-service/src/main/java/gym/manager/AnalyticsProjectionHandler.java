package gym.manager;

import akka.projection.eventsourced.EventEnvelope;
import akka.projection.jdbc.javadsl.JdbcHandler;
import gym.manager.repository.AnalyticsRepository;
import gym.manager.repository.HibernateJdbcSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AnalyticsProjectionHandler
        extends JdbcHandler<EventEnvelope<Subscription.Event>, HibernateJdbcSession> {
  private final Logger logger = LoggerFactory.getLogger(getClass());
  private final String tag;
  private final AnalyticsRepository repo;

  public AnalyticsProjectionHandler(String tag, AnalyticsRepository repo) {
    this.tag = tag;
    this.repo = repo;
  }

  private Analytics findOrNew(String itemId) {
    return repo.findById(itemId).orElseGet(() -> new Analytics(itemId, 0, 0, false));
  }

  @Override
  public void process(
          HibernateJdbcSession session, EventEnvelope<Subscription.Event> envelope) {
    Subscription.Event event = envelope.event();

    if (event instanceof Subscription.EnterEvent en) {
      String cardId = en.cartId;

      Analytics existingItemPop = findOrNew(cardId);
      Analytics updatedItemPop = existingItemPop.changeCount(1, true);
      repo.save(updatedItemPop);

      logger.info(
              "AnalyticsProjectionHandler({}) user statistics was updated",
              this.tag,
              cardId,
              updatedItemPop.getCount());

    } else if (event instanceof Subscription.ExitEvent) {
      if (event instanceof Subscription.EnterEvent en) {
        String cardId = en.cartId;

        Analytics existingItemPop = findOrNew(cardId);
        Analytics updatedItemPop = existingItemPop.changeCount(0, false);
        repo.save(updatedItemPop);

        logger.info(
                "AnalyticsProjectionHandler({}) user statistics was updated",
                this.tag,
                cardId,
                updatedItemPop.getCount());

      } else {
        // skip all other events, such as `CheckedOut`
      }
    }
  }
}

