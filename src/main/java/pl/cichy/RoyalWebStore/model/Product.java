package pl.cichy.RoyalWebStore.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
@Entity
@Table( name = "products")
public class Product implements Serializable {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    int productId;

    @Size(min=1, max=25)
    String productName;

    //CategoryAndManufacturer categoryAndManufacturer;

    @Size(max=25)
    String type;
    @Size(max=25)
    String version;
    @Size(max=50)
    String productDescription;

    //Photo photo;

    @Digits(integer=8, fraction=2)
    BigDecimal currentNetPrice;

    @Digits(integer=8, fraction=2)
    BigDecimal currentGrossPrice;

    @Digits(integer=8, fraction=2)
    BigDecimal baseNetPrice;


    @Digits(integer=8, fraction=2)
    BigDecimal baseGrossPrice;

    @Digits(integer=8, fraction=2)
    BigDecimal percentageDiscoutValue;

    @Digits(integer=8, fraction=2)
    BigDecimal discoutValue;

    @Digits(integer=8, fraction=2)
    BigDecimal vatPercentage;

    @Digits(integer=8, fraction=2)
    BigDecimal vatValue;

    public Product() {
    }

    public Product(int productId, String productName, BigDecimal baseGrossPrice, BigDecimal vatPercentage) {
        this.productId = productId;
        this.productName = productName;
        this.baseGrossPrice = baseGrossPrice;
        this.vatPercentage = vatPercentage;

        BigDecimal point = BigDecimal.valueOf(-1.00);
        baseNetPrice = (baseGrossPrice.multiply((point.add(vatPercentage))
                .abs())).setScale(2, RoundingMode.DOWN);

        vatValue = baseGrossPrice.add(baseNetPrice.negate())
                .setScale(2, RoundingMode.DOWN);

        currentGrossPrice = baseGrossPrice;
        currentNetPrice = baseNetPrice;

        percentageDiscoutValue = new BigDecimal(0);
        discoutValue = new BigDecimal(0);

        type = "";
        version ="";
        productDescription ="";
    }

    //todo
    //setter VAT, DISCOUNT etc must recalculate prices and save (PUT) changes to DB.
}
