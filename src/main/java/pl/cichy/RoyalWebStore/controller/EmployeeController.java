package pl.cichy.RoyalWebStore.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.cichy.RoyalWebStore.exception.CustomerNotFoundException;
import pl.cichy.RoyalWebStore.logic.EmployeeService;
import pl.cichy.RoyalWebStore.model.Customer;
import pl.cichy.RoyalWebStore.model.Employee;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    public EmployeeController() {
    }

    @GetMapping
    ResponseEntity<List<Employee>> readAllEmployees() {
        logger.info("Read all the employees!");
        return ResponseEntity.ok(employeeService.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<Employee> readEmployeeById(@PathVariable int id) {
        logger.info("Read employee with id: " + id + "!");
        return employeeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    ResponseEntity<Employee> createNewEmployee(@RequestBody @Valid Employee newEmployeeToAdd) {
        employeeService.registerNewEmployeeAccount(newEmployeeToAdd);
        logger.info("New employee was created!");
        return ResponseEntity.created(URI.create("/" + newEmployeeToAdd.getEmployeeId())).body(newEmployeeToAdd);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Customer> deleteEmployee(@PathVariable int id) {
        employeeService.deleteById(id);
        logger.info("Employee was deleted!");
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{employeeId}")
    ResponseEntity<Customer> updateEmployee(@PathVariable int employeeId,
                                            @RequestBody JsonPatch employeeToUpdate) {
        try {
            employeeService.updateEmployeesData(employeeId, employeeToUpdate);
            logger.info("Customer was successfully updated!");
            return ResponseEntity.ok().build();
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
