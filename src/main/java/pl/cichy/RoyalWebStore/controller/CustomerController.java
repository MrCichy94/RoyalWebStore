package pl.cichy.RoyalWebStore.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.cichy.RoyalWebStore.logic.CustomerService;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    public CustomerController() {
    }

    /*
    @PostMapping
    ResponseEntity<Customer> createNewCustomer(@RequestBody @Valid Customer newCustomerToAdd) {

        String encodedPassword = bCryptPasswordEncoder.encode(newCustomerToAdd.getPassword());
        Customer result = new Customer(newCustomerToAdd.getCustomerId(),
                newCustomerToAdd.getLogin(),
                newCustomerToAdd.getPassword(),
                newCustomerToAdd.getVatPercentage());
        customerService.save(result);
        logger.info("New product was created!");


    }

     */
}
