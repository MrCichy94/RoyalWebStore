package pl.cichy.RoyalWebStore.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.cichy.RoyalWebStore.logic.CategoryService;
import pl.cichy.RoyalWebStore.model.Category;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/products")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    public CategoryController() {
    }

    @GetMapping("/categories")
    ResponseEntity<List<Category>> readAllCategoriesAndManufacturers(){
        logger.info("Read all the products!");
        return ResponseEntity.ok(categoryService.findAll());
    }

    @PostMapping("/{productId}/category/add")
    ResponseEntity<Category> setNewManufacturerForProduct(@PathVariable("productId") Integer productId,
                                                              @RequestBody @Valid Category categoryToSet){
        categoryService.setCategoryForProduct(productId, categoryToSet);
        return ResponseEntity.created(URI.create("/" + categoryToSet.getCategoryId())).body(categoryToSet);
    }
}
