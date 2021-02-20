package pl.cichy.RoyalWebStore.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    int employeeId;

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
    String typeOfPermissions;

    boolean accountActive;

    LocalDate hireDate;

    LocalDate releaseDate;

    public Employee() {
    }

    public Employee(int employeeId, String login, String password, String firstName, String lastName,
                    String typeOfPermissions) {
        this.employeeId = employeeId;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountActive = accountActive;
        this.typeOfPermissions = typeOfPermissions;

        hireDate = LocalDate.now();
        releaseDate = null;
        accountActive = true;
    }
}
