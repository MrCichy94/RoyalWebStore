package pl.cichy.RoyalWebStore.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.cichy.RoyalWebStore.logic.EmployeeService;
import pl.cichy.RoyalWebStore.model.Customer;
import pl.cichy.RoyalWebStore.model.Employee;
import pl.cichy.RoyalWebStore.model.Product;

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
    ResponseEntity<List<Employee>> readAllProduct() {
        logger.info("Read all the products!");
        return ResponseEntity.ok(employeeService.findAll());
    }

    @GetMapping("/employee/{id}")
    ResponseEntity<Employee> readEmployeeById(@PathVariable int id) {
        logger.info("Read product with id: " + id + "!");
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

    @DeleteMapping("/employee/{id}")
    ResponseEntity<Customer> deleteCustomer(@PathVariable int id) {
        employeeService.deleteById(id);
        logger.info("Employee was deleted!");
        return ResponseEntity.ok().build();
    }
}
