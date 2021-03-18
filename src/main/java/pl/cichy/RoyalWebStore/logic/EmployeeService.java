package pl.cichy.RoyalWebStore.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.cichy.RoyalWebStore.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<Employee> findAll();

    Page<Employee> findAll(Pageable page);

    Optional<Employee> findById(Integer id);

    Employee getById(int employeeId);

    void deleteById(Integer id);

    void registerNewEmployeeAccount(Employee newEmployeeToAdd);

    void updateEmployeesData(int employeeId, JsonPatch employeeToUpdate) throws JsonPatchException, JsonProcessingException;
}
