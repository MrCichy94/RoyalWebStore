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
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product implements Serializable {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    int productId;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Set<Copy> copies;

    /*
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Set<Photo> photos;
    */

    @Size(min = 1, max = 25)
    String productName;

    @Size(min = 1, max = 25)
    String productCategory;

    @Size(min = 1, max = 25)
    String productManufacturer;

    @Size(max = 25)
    String type;
    @Size(max = 25)
    String version;
    @Size(max = 50)
    String productDescription;

    @Digits(integer = 8, fraction = 2)
    BigDecimal sellBaseNetPrice;

    @Digits(integer = 8, fraction = 2)
    BigDecimal sellBaseGrossPrice;

    @Digits(integer = 8, fraction = 2)
    BigDecimal vatPercentage;

    @Digits(integer = 8, fraction = 2)
    BigDecimal vatValue;

    public Product() {
    }

    public Product(int productId, String productName, String productManufacturer, String productCategory,
                   BigDecimal sellBaseGrossPrice, BigDecimal vatPercentage) {
        this.productId = productId;
        this.productName = productName;
        this.productManufacturer = productManufacturer;
        this.productCategory = productCategory;
        this.sellBaseGrossPrice = sellBaseGrossPrice;
        this.vatPercentage = vatPercentage;

        BigDecimal point = (BigDecimal.ONE).negate();
        sellBaseNetPrice = countSellBaseNetPrice(sellBaseGrossPrice, vatPercentage, point);
        vatValue = countVatValue(sellBaseGrossPrice);

        type = "";
        version = "";
        productDescription = "";
    }

    private BigDecimal countVatValue(BigDecimal sellBaseGrossPrice) {
        return sellBaseGrossPrice.add(sellBaseNetPrice.negate())
                .setScale(2, RoundingMode.DOWN);
    }

    private BigDecimal countSellBaseNetPrice(BigDecimal sellBaseGrossPrice,
                                             BigDecimal vatPercentage, BigDecimal point) {
        return (sellBaseGrossPrice.multiply((point.add(vatPercentage))
                .abs())).setScale(2, RoundingMode.DOWN);
    }

    /*
    @Transient
    public String getPhotosImagePath() {
        if (photos.isEmpty()) return null;
        return "/productPhotos/" + productId + "/" + photos;
    }
    */
}
