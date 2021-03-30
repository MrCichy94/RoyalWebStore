package pl.cichy.RoyalWebStore.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "cartitems")
public class CartItem implements Serializable {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    int cartItemId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Copy copy;

    @Digits(integer = 8, fraction = 2)
    private BigDecimal totalPrice;

    private int quantity;

    private static final long serialVersionUID = 6355555334140807514L;

    public CartItem() {
    }

    public CartItem(Copy copy) {
        super();
        this.copy = copy;
        this.quantity = 1;
        this.totalPrice = copy.getSellCurrentGrossPrice();
    }

    public void setCopy(Copy copy) {
        this.copy = copy;
        this.updateTotalPrice();
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.updateTotalPrice();
    }

    public void updateTotalPrice() {
        totalPrice = this.copy.getSellCurrentGrossPrice().multiply(new BigDecimal(this.quantity));
    }
}
