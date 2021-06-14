package pl.cichy.RoyalWebStore.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import java.math.RoundingMode;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "copies")
public class Copy implements Serializable {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    int copyId;

    private int productId;

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
    BigDecimal discountValue;

    @Digits(integer = 8, fraction = 2)
    BigDecimal percentageDiscountValue;

    boolean isAlreadySold = false;

    boolean isOnTheStore = false;

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate buyDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate sellDate;

    public Copy() {
    }

    public Copy(int copyId, String merchandisingCode, BigDecimal buyGrossPrice, BigDecimal buyVatPercentage) {
        this.copyId = copyId;
        this.merchandisingCode = merchandisingCode;
        this.buyGrossPrice = buyGrossPrice;
        this.buyVatPercentage = buyVatPercentage;
        quantity = 0;

        BigDecimal point = (BigDecimal.ONE).negate();
        buyNetPrice = countBuyNetPrice(buyGrossPrice, buyVatPercentage, point);
        buyVatValue = countBuyVatValue(buyGrossPrice);

        discountValue = BigDecimal.ZERO;
        percentageDiscountValue = BigDecimal.ZERO;

        buyDate = LocalDate.now();
        sellDate = null;
    }

    private BigDecimal countBuyVatValue(BigDecimal buyGrossPrice) {
        return buyGrossPrice.add(buyNetPrice.negate())
                .setScale(2, RoundingMode.DOWN);
    }

    private BigDecimal countBuyNetPrice(BigDecimal buyGrossPrice, BigDecimal buyVatPercentage, BigDecimal point) {
        return (buyGrossPrice.multiply((point.add(buyVatPercentage))
                .abs())).setScale(2, RoundingMode.DOWN);
    }

    public void increaseQuantity() {
        quantity++;
    }
}
