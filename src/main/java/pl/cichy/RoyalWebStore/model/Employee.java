package pl.cichy.RoyalWebStore.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private String emailLogin;

    @Size(min = 3, max = 85)
    private String password;

    @Size(min = 2, max = 25)
    private String lastName;

    @Size(min = 2, max = 25)
    private String firstName;

    @Size(min = 1, max = 25)
    private String role;

    private boolean accountActive;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate hireDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    public Employee() {
    }

    public Employee(int employeeId, String emailLogin, String password, String firstName, String lastName,
                    String role) {
        this.employeeId = employeeId;
        this.emailLogin = emailLogin;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = "ROLE_" + role;

        address = new Address();
        contact = new Contact();

        hireDate = LocalDate.now();
        releaseDate = null;
        accountActive = true;
    }
}
