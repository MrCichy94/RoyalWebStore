package pl.cichy.RoyalWebStore.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "carts")
public class Cart implements Serializable {

    @Id
    String cartId;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Map<Integer, CartItem> cartItems;

    @Digits(integer = 8, fraction = 2)
    private BigDecimal grandTotal;

    private static final long serialVersionUID = 6350930334141111514L;

    int customerId;

    public Cart() {
        cartItems = new HashMap<Integer, CartItem>();
        grandTotal = new BigDecimal(0);
    }

    public Cart(int customerId, String cartId) {
        this();
        this.cartId = cartId;
        this.customerId = customerId;
    }

    public void addCartItem(CartItem item) {
        int copyId = item.getCopy().getCopyId();

        if (cartItems.containsKey(copyId)) {
            CartItem existingCartItem = cartItems.get(copyId);
            existingCartItem.setQuantity(existingCartItem.getQuantity() + item.getQuantity());
            cartItems.put(copyId,existingCartItem);
        } else {
            cartItems.put(copyId,item);
        }

        updateGrandTotal();
    }

    public void updateGrandTotal() {
        grandTotal = new BigDecimal(0);
        for(CartItem item : cartItems.values()) {
            grandTotal = grandTotal.add(item.getTotalPrice());
        }
    }

    public void removeCartItem(CartItem item) {
        int productId = item.getCopy().getCopyId();
        cartItems.remove(productId);
        updateGrandTotal();
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
