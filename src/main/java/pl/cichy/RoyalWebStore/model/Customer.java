package pl.cichy.RoyalWebStore.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer implements Serializable {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    int customerId;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Set<Order> orders;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Address address;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Contact contact;

    @Size(min = 3, max = 25)
    String login;

    @Size(min = 3, max = 85)
    String password;

    @Size(min = 2, max = 25)
    String lastName;

    @Size(min = 2, max = 25)
    String firstName;

    @Size(min = 1, max = 25)
    String typeOfClient;

    @Size(min = 1, max = 25)
    private String role;

    @Size(max = 25)
    String companyName;

    @Pattern(regexp = "(^$|[0-9]{9})")
    String REGON;

    @Pattern(regexp = "(^$|[0-9]{10})")
    String NIP;

    private boolean accountActive;

    public Customer() {
    }

    public Customer(int customerId, String login, String password, String firstName,
                    String lastName, String typeOfClient, String role) {
        this.customerId = customerId;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.typeOfClient = typeOfClient;
        this.role = "ROLE_" + role;

        address = new Address();
        contact = new Contact();

        accountActive = true;
    }

}
