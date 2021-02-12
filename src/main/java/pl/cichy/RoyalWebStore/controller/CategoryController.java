package pl.cichy.RoyalWebStore.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.cichy.RoyalWebStore.logic.CategoryAndManufacturerService;
import pl.cichy.RoyalWebStore.model.Category;
import pl.cichy.RoyalWebStore.model.CategoryAndManufacturer;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    public CategoryController() {
    }

    @GetMapping
    ResponseEntity<List<Category>> readAllCategoriesAndManufacturers(){
        logger.info("Read all the products!");
        return ResponseEntity.ok(categoryService.findAll());
    }
}
