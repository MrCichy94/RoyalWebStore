package pl.cichy.RoyalWebStore.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    int orderId;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<Copy> copies;

    int customerId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate orderAcceptanceDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate shippingDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate orderFulfillmentDate;

    @Size(min = 1, max = 25)
    String paid;

    boolean isAccepted;

    boolean isFulfill;

    public Order() {
    }

    public Order(int orderId, String paid) {
        this.orderId = orderId;
        this.paid = paid;

        isAccepted = true;
        isFulfill = false;
    }
}
