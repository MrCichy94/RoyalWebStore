package pl.cichy.RoyalWebStore.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employee implements Serializable, UserDetails {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    private int employeeId;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Address address;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Contact contact;

    @Size(min = 3, max = 25)
    private String username;

    @Size(min = 3, max = 85)
    private String password;

    @Size(min = 2, max = 25)
    private String lastName;

    @Size(min = 2, max = 25)
    private String firstName;

    @Size(min = 1, max = 25)
    private String role;

    private boolean accountActive;

    private LocalDate hireDate;

    private LocalDate releaseDate;

    public Employee() {
    }

    public Employee(int employeeId, String username, String password, String firstName, String lastName,
                    String role) {
        this.employeeId = employeeId;
        this.username = username;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
