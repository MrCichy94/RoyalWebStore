package pl.cichy.RoyalWebStore.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.cichy.RoyalWebStore.logic.ProductService;
import pl.cichy.RoyalWebStore.model.Order;
import pl.cichy.RoyalWebStore.model.Product;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    public ProductController() {
    }

    @GetMapping
    ResponseEntity<List<Product>> readAllProduct() {
        logger.info("Read all the products!");
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/product/{id}")
    ResponseEntity<Product> readProductById(@PathVariable int id) {
        logger.info("Read product with id: " + id + "!");
        return productService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/newproduct/add")
    ResponseEntity<Product> createNewProduct(@RequestBody @Valid Product newProductToAdd) {
        productService.addNewProduct(newProductToAdd);
        logger.info("New product was created!");
        return ResponseEntity.created(URI.create("/" + newProductToAdd.getProductId())).body(newProductToAdd);
    }

    @DeleteMapping("/product/{id}")
    ResponseEntity<Order> deleteOrder(@PathVariable int id) {
        productService.deleteById(id);
        logger.info("Product was deleted!");
        return ResponseEntity.ok().build();
    }

}
