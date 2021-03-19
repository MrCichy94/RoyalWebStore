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
import pl.cichy.RoyalWebStore.logic.CustomerService;
import pl.cichy.RoyalWebStore.logic.InvoiceGeneratorService;
import pl.cichy.RoyalWebStore.model.Customer;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private InvoiceGeneratorService invoiceGeneratorService;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    public CustomerController() {
    }

    @GetMapping
    ResponseEntity<List<Customer>> readAllCustomers() {
        logger.info("Read all the customers!");
        return ResponseEntity.ok(customerService.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<Customer> readCustomerById(@PathVariable int id) {
        logger.info("Read customer with id: " + id + "!");
        return ResponseEntity.ok(customerService.getById(id));
    }

    @PostMapping("/add")
    ResponseEntity<Customer> createNewCustomer(@RequestBody @Valid Customer newCustomerToAdd) {
        customerService.registerNewCustomerAccount(newCustomerToAdd);
        logger.info("New customer was created!");
        return ResponseEntity.created(URI.create("/" + newCustomerToAdd.getCustomerId())).body(newCustomerToAdd);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Customer> deleteCustomer(@PathVariable int id) {
        customerService.deleteById(id);
        logger.info("Customer was deleted!");
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{customerId}/orders/{orderId}")
    ResponseEntity<Customer> deleteCustomersOrder(@PathVariable int customerId, @PathVariable int orderId) {
        customerService.deleteCustomersOrder(customerId, orderId);
        logger.info("Customer's order with id: " + orderId + " was deleted!");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{customerId}/orders/{orderId}")
    ResponseEntity<Customer> createPDFInvoiceForCustomersOrder(@PathVariable int customerId,
                                                               @PathVariable int orderId) throws FileNotFoundException {
        invoiceGeneratorService.createCustomersOrderPDFInvoice(customerId, orderId);
        logger.info("Customer's order with id: " + " invoice in PDF created!");
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{customerId}")
    ResponseEntity<Customer> updateCustomer(@PathVariable int customerId,
                                            @RequestBody JsonPatch customerToUpdate) {
        try {
            customerService.updateCustomersData(customerId, customerToUpdate);
            logger.info("Customer was successfully updated!");
            return ResponseEntity.ok().build();
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
