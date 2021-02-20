package pl.cichy.RoyalWebStore.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.cichy.RoyalWebStore.logic.CustomerService;
import pl.cichy.RoyalWebStore.model.Customer;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    public CustomerController() {
    }

    @GetMapping
    ResponseEntity<List<Customer>> readAllCustomers() {
        logger.info("Read all the customers!");
        return ResponseEntity.ok(customerService.findAll());
    }

    @GetMapping("/customer/{id}")
    ResponseEntity<Customer> readProductById(@PathVariable int id) {
        logger.info("Read customer with id: " + id + "!");
        return customerService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping("/add")
    ResponseEntity<Customer> createNewCustomer(@RequestBody @Valid Customer newCustomerToAdd) {
        customerService.registerNewCustomerAccount(newCustomerToAdd);
        logger.info("New customer was created!");
        return ResponseEntity.created(URI.create("/" + newCustomerToAdd.getCustomerId())).body(newCustomerToAdd);
    }


}
