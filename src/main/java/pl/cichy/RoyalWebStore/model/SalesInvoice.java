package pl.cichy.RoyalWebStore.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

@Getter
@Setter
@Entity
@Table(name = "salesinvoice")
public class SalesInvoice implements Serializable {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    int salesInvoiceId;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Order order;

    String salesInvoiceNumber;

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate soldDate;

    BigDecimal netValue;
    BigDecimal grossValue;
    BigDecimal vatValue;

    @Size(max = 25)
    String bankName;

    @Size(max = 25)
    String paymentMethod;

    @Size(max = 25)
    String typeOfDocument;

    public SalesInvoice() {
    }

    public SalesInvoice(int salesInvoiceId, String paymentMethod, String typeOfDocument) {
        this.salesInvoiceId = salesInvoiceId;
        this.paymentMethod = paymentMethod;
        this.typeOfDocument = typeOfDocument;

        salesInvoiceNumber = numberGenerator(16);
    }

    private String numberGenerator(int length) {
        Random random = new Random();
        StringBuilder number = new StringBuilder("1");
        for (int i = 0; i < length - 1; i++) {
            number.append(random.nextInt(10));
        }
        return number.toString();
    }
}


