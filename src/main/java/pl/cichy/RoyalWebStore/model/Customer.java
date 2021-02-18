package pl.cichy.RoyalWebStore.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    int customerId;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Address address;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Contact contact;

    @Size(min = 3, max = 25)
    String login;

    String password;

    @Size(max = 25)
    String companyName;

    @Pattern(regexp="(^$|[0-9]{9})")
    String REGON;

    @Pattern(regexp="(^$|[0-9]{10})")
    String NIP;

    @Size(min = 2, max = 25)
    String lastName;

    @Size(min = 2, max = 25)
    String firstName;

    @Size(min = 1, max = 25)
    String typeOfClient;

    public Customer() {
    }
}
