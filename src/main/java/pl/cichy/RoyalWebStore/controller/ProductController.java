package pl.cichy.RoyalWebStore.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.cichy.RoyalWebStore.logic.ProductService;
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

    @PostMapping("/newproduct/add")
    ResponseEntity<Product> createNewProduct(@RequestBody @Valid Product newProductToAdd) {
        Product result = new Product(newProductToAdd.getProductId(),
                newProductToAdd.getProductName(),
                newProductToAdd.getBaseGrossPrice(),
                newProductToAdd.getVatPercentage());
        productService.save(result);
        logger.info("New product was created!");
        return ResponseEntity.created(URI.create("/" + result.getProductId())).body(result);
    }

}
