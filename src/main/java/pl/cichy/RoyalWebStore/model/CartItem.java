package pl.cichy.RoyalWebStore.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "cartitems")
public class CartItem implements Serializable {

    //TODO - REDUCE THIS VARIABLE WHEN BACK AGAIN!

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    int cartItemId;

    private int copyId;

    private int quantity;

    @Size(min = 1, max = 25)
    String merchandisingCode;

    @Digits(integer = 8, fraction = 2)
    BigDecimal buyNetPrice;

    @Digits(integer = 8, fraction = 2)
    BigDecimal buyGrossPrice;

    @Digits(integer = 8, fraction = 2)
    BigDecimal buyVatPercentage;

    @Digits(integer = 8, fraction = 2)
    BigDecimal buyVatValue;

    @Digits(integer = 8, fraction = 2)
    BigDecimal sellCurrentNetPrice;

    @Digits(integer = 8, fraction = 2)
    BigDecimal sellCurrentGrossPrice;

    @Digits(integer = 8, fraction = 2)
    BigDecimal discoutValue;

    @Digits(integer = 8, fraction = 2)
    BigDecimal percentageDiscoutValue;

    public CartItem() {
    }

    public void increaseQuantity() {
        quantity++;
    }
}
