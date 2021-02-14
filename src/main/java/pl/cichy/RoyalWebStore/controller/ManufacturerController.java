package pl.cichy.RoyalWebStore.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.cichy.RoyalWebStore.logic.ManufacturerService;
import pl.cichy.RoyalWebStore.model.Manufacturer;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ManufacturerController {

    @Autowired
    private ManufacturerService manufacturerService;

    private static final Logger logger = LoggerFactory.getLogger(ManufacturerController.class);

    public ManufacturerController() {
    }

    @GetMapping("/manufacturers")
    ResponseEntity<List<Manufacturer>> readAllCategoriesAndManufacturers() {
        logger.info("Read all the products!");
        return ResponseEntity.ok(manufacturerService.findAll());
    }

    @PostMapping("/{productId}/manufacturer/add")
    ResponseEntity<Manufacturer> setNewManufacturerForProduct(@PathVariable("productId") Integer productId,
                                                              @RequestBody @Valid Manufacturer manufacturerToSet) {
        manufacturerService.setManufacturerForProduct(productId, manufacturerToSet);
        return ResponseEntity.created(URI.create("/" + manufacturerToSet.getManufacturerId())).body(manufacturerToSet);
    }
}
