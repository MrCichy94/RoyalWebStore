package pl.cichy.RoyalWebStore.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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

    @Size(min=3, max=25)
    String productName;

    //CategoryAndManufacturer categoryAndManufacturer;

    @Size(min=3, max=25)
    String type;
    @Size(min=3, max=25)
    String version;
    @Size(min=3, max=50)
    String productDescription;

    //Photo photo;

    @Min(value=0)
    @Digits(integer=8, fraction=2)
    @NotNull
    BigDecimal currentNetPrice;

    BigDecimal currentGrossPrice;
    @Min(value=0)
    @Digits(integer=8, fraction=2)
    @NotNull
    BigDecimal baseNetPrice;

    @Min(value=0)
    @Digits(integer=8, fraction=2)
    @NotNull
    BigDecimal baseGrossPrice;

    @Min(value=0)
    @Digits(integer=8, fraction=2)
    @NotNull
    BigDecimal percentageDiscoutValue;

    @Min(value=0)
    @Digits(integer=8, fraction=2)
    @NotNull
    BigDecimal discoutValue;

    @Min(value=0)
    @Digits(integer=8, fraction=2)
    @NotNull
    BigDecimal vatPercentage;

    @Min(value=0)
    @Digits(integer=8, fraction=2)
    @NotNull
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
    }
}
