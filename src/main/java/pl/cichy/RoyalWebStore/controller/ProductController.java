package pl.cichy.RoyalWebStore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.cichy.RoyalWebStore.logic.ProductService;
import pl.cichy.RoyalWebStore.model.Product;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    public ProductController() {
    }

    @GetMapping
    ResponseEntity<List<Product>> readAllProduct(){
        return ResponseEntity.ok(productService.readAllProducts());
    }

}
