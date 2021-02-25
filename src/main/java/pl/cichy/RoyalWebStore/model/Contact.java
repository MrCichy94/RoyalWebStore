package pl.cichy.RoyalWebStore.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "contacts")
public class Contact implements Serializable {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    int contactId;

    @Pattern(regexp = "(^$|[0-9]{9})")
    String phoneNumber1;

    @Size(max = 50)
    String emailAddress;

    @Pattern(regexp = "(^$|[0-9]{9})")
    String phoneNumber2;

    @Size(max = 50)
    String faxAddress;

    @Size(max = 50)
    String wwwDomain;

    public Contact() {
    }

    public Contact(int contactId, String phoneNumber1, String emailAddress) {
        this.contactId = contactId;
        this.phoneNumber1 = phoneNumber1;
        this.emailAddress = emailAddress;
    }
}
