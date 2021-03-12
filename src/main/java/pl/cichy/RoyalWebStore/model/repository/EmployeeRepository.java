package pl.cichy.RoyalWebStore.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import pl.cichy.RoyalWebStore.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {

    List<Employee> findAll();

    Page<Employee> findAll(Pageable page);

    Optional<Employee> findById(Integer id);

    void deleteById(Integer id);

    Employee save(Employee entity);

    Optional<Employee> findByUsername(String username);
}
