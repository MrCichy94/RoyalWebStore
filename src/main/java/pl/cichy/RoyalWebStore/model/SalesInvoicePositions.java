package pl.cichy.RoyalWebStore.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "salesinvoicepositions")
public class SalesInvoicePositions implements Serializable {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    int salesInvoicePositionsId;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    SalesInvoice salesInvoice;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Copy copy;

    @Digits(integer = 8, fraction = 2)
    BigDecimal sellNettPrice;

    @Digits(integer = 8, fraction = 2)
    BigDecimal sellGrossPrice;

    @Digits(integer = 8, fraction = 2)
    BigDecimal sellVatPercent;

    public SalesInvoicePositions() {
    }

    public SalesInvoicePositions(SalesInvoice salesInvoice, Copy copy,
                                 BigDecimal sellNettPrice, BigDecimal sellGrossPrice, BigDecimal sellVatPercent) {
        this.salesInvoice = salesInvoice;
        this.copy = copy;
        this.sellNettPrice = sellNettPrice;
        this.sellGrossPrice = sellGrossPrice;
        this.sellVatPercent = sellVatPercent;
    }
}
