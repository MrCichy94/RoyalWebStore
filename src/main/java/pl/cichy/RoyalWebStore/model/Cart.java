package pl.cichy.RoyalWebStore.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "carts")
public class Cart implements Serializable {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    int cartId;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<CartItem> cartItems;

    @Digits(integer = 8, fraction = 2)
    private BigDecimal grandTotal;

    public Cart() {
    }

    public Cart(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    /* TO SERVIS, WITH ADD TO CART METHOD
    public void updateGrandTotal() {
        grandTotal = new BigDecimal(0);
        for(Copy item : copies) {
            grandTotal = grandTotal.add(item.sellCurrentGrossPrice);
        }
    }
    */

}
