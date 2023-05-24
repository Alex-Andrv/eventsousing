package gym.manager;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "analytics")
public class Analytics {

  // primary key
  @Id private final String cartId;

  // optimistic locking
  @Version private final Long version;

  private final long count;
  private final boolean isIn;

  public Analytics() {
    // null version means the entity is not on the DB
    this.version=null;
    this.cartId="";
    this.count=0;
    this.isIn=false;
  }

  public Analytics(String itemId, long version, long count, boolean in) {
    this.cartId = itemId;
    this.version = version;
    this.count = count;
    this.isIn=in;
  }

  public String getCartId() {
    return cartId;
  }

  public long getCount() {
    return count;
  }

  public boolean getIn() {
    return isIn;
  }

  public long getVersion() {
    return version;
  }

  public Analytics changeCount(long delta, boolean in) {
    return new Analytics(cartId, version, count + delta, in);
  }
}
