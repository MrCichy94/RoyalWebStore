package pl.cichy.RoyalWebStore.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employee implements Serializable {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    private int employeeId;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Address address;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Contact contact;

    @Size(min = 3, max = 25)
    private String login;

    @Size(min = 3, max = 85)
    private String password;

    @Size(min = 2, max = 25)
    private String lastName;

    @Size(min = 2, max = 25)
    private String firstName;

    @Size(min = 1, max = 25)
    private String typeOfPermissions;

    private String roles;

    private boolean accountActive;

    private LocalDate hireDate;

    private LocalDate releaseDate;

    public Employee() {
    }

    public Employee(int employeeId, String login, String password, String firstName, String lastName,
                    String typeOfPermissions) {
        this.employeeId = employeeId;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.typeOfPermissions = typeOfPermissions;

        address = new Address();
        contact = new Contact();

        hireDate = LocalDate.now();
        releaseDate = null;
        accountActive = true;
    }
}
