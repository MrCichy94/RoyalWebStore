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
import java.util.List;

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
    List<Copy> copies;

    /*
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Set<Photo> photos;
    */

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    CategoryAndManufacturer categoryAndManufacturer;

    @Size(min = 1, max = 25)
    String productName;

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

    public Product(int productId, String productName, BigDecimal sellBaseGrossPrice, BigDecimal vatPercentage) {
        this.productId = productId;
        this.productName = productName;
        this.sellBaseGrossPrice = sellBaseGrossPrice;
        this.vatPercentage = vatPercentage;

        BigDecimal point = BigDecimal.valueOf(-1.00);
        sellBaseNetPrice = (sellBaseGrossPrice.multiply((point.add(vatPercentage))
                .abs())).setScale(2, RoundingMode.DOWN);

        vatValue = sellBaseGrossPrice.add(sellBaseNetPrice.negate())
                .setScale(2, RoundingMode.DOWN);

        type = "";
        version = "";
        productDescription = "";
        categoryAndManufacturer = new CategoryAndManufacturer();
    }

    /*
    @Transient
    public String getPhotosImagePath() {
        if (photos.isEmpty()) return null;
        return "/productPhotos/" + productId + "/" + photos;
    }
    */

    //todo
    //setter VAT, DISCOUNT etc must recalculate prices and save (PUT) changes to DB.
}
