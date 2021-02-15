package pl.cichy.RoyalWebStore.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import pl.cichy.RoyalWebStore.logic.ProductService;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "copies")
public class Copy {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    int copyId;

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

    boolean isAlreadySold;

    LocalDate buyDate;

    LocalDate sellDate;

    private int product_id;

    public Copy() {
    }

    public Copy(int copyId, Product product, String merchandisingCode,
                BigDecimal buyGrossPrice, BigDecimal buyVatPercentage) {

        this.merchandisingCode = merchandisingCode;
        this.buyGrossPrice = buyGrossPrice;
        this.buyVatPercentage =buyVatPercentage;

        BigDecimal point = BigDecimal.valueOf(-1.00);
        buyNetPrice = (buyGrossPrice.multiply((point.add(buyVatPercentage))
                .abs())).setScale(2, RoundingMode.DOWN);

        buyVatValue = buyGrossPrice.add(buyNetPrice.negate())
                .setScale(2, RoundingMode.DOWN);

        discoutValue = new BigDecimal(0);
        percentageDiscoutValue = new BigDecimal(0);
        sellCurrentGrossPrice = product.getSellBaseGrossPrice();
        sellCurrentNetPrice = product.getSellBaseNetPrice();

        buyDate = LocalDate.now();
        sellDate = null;
    }

}
