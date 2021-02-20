package pl.cichy.RoyalWebStore.logic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.cichy.RoyalWebStore.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<Employee> findAll();

    Page<Employee> findAll(Pageable page);

    Optional<Employee> findById(Integer id);

    Employee getById(Integer id);

    void deleteById(Integer id);

    boolean existsById(Integer id);

    Employee save(Employee entity);

    void registerNewEmployeeAccount(Employee newEmployeeToAdd);
}
