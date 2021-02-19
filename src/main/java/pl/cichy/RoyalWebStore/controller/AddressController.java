package pl.cichy.RoyalWebStore.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.cichy.RoyalWebStore.logic.AddressService;
import pl.cichy.RoyalWebStore.model.Address;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    private static final Logger logger = LoggerFactory.getLogger(AddressController.class);

    public AddressController() {
    }

    @GetMapping
    ResponseEntity<List<Address>> readAllAddresses() {
        logger.info("Read all the contacts!");
        return ResponseEntity.ok(addressService.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<Address> readAddressById(@PathVariable int id) {
        logger.info("Read address with id: " + id + "!");
        return addressService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    ResponseEntity<Address> addNewAddress(@RequestBody @Valid Address newAddressToAdd) {
        addressService.createNewAddressIfPossible(newAddressToAdd);
        logger.info("New contact was created!");
        return ResponseEntity.created(URI.create("/" + newAddressToAdd.getAddressId())).body(newAddressToAdd);
    }
}
