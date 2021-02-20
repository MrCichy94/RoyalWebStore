package pl.cichy.RoyalWebStore.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    int orderId;

    LocalDate orderAcceptanceDate;

    boolean isAccepted;

    LocalDate shippingDate;

    LocalDate orderFulfillmentDate;

    @Size(min = 1, max = 25)
    String paid;

    boolean isFulfill;

    int customerId;

    public Order() {
    }

    public Order(int orderId, String paid) {
        this.orderId = orderId;
        this.paid = paid;

        orderAcceptanceDate = LocalDate.now();
        isAccepted = true;
        isFulfill = false;
    }
}
