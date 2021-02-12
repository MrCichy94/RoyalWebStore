package pl.cichy.RoyalWebStore.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.cichy.RoyalWebStore.logic.CategoryAndManufacturerService;
import pl.cichy.RoyalWebStore.logic.ManufacturerService;
import pl.cichy.RoyalWebStore.model.CategoryAndManufacturer;
import pl.cichy.RoyalWebStore.model.Manufacturer;

import java.util.List;

@Controller
@RequestMapping("/manufacturers")
public class ManufacturerController {

    @Autowired
    private ManufacturerService manufacturerService;

    private static final Logger logger = LoggerFactory.getLogger(ManufacturerController.class);

    public ManufacturerController() {
    }

    @GetMapping
    ResponseEntity<List<Manufacturer>> readAllCategoriesAndManufacturers(){
        logger.info("Read all the products!");
        return ResponseEntity.ok(manufacturerService.findAll());
    }
}
