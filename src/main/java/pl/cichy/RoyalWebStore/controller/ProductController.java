package pl.cichy.RoyalWebStore.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.cichy.RoyalWebStore.logic.ProductService;
import pl.cichy.RoyalWebStore.model.Customer;
import pl.cichy.RoyalWebStore.model.Order;
import pl.cichy.RoyalWebStore.model.Product;

import javax.validation.Valid;
import java.math.BigDecimal;
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

    @GetMapping("/{id}")
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

    /* TODO: MERGE WITH POST METHOD, MAYBE IN SERVICE_IMPL METHOD, THINK ABOUT IT WHEN CREATE FRONTEND FORM
    @PostMapping("/newproduct/add")
    public RedirectView saveUser(Product newProductToAdd,
                                 @RequestParam("image") MultipartFile multipartFile) throws IOException {

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        newProductToAdd.setPhotos(fileName);

        Product productToSave = productService.save(newProductToAdd);

        String uploadDir = "productsPhotos/" + productToSave.getProductId();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return new RedirectView("/users", true);
    }
    */

    @PatchMapping("/{productId}/set/price")
    ResponseEntity<Product> manualChangeProductPriceByValue(@PathVariable int productId,
                                                            @RequestBody BigDecimal priceToSet) {
        productService.changeProductPriceByValue(productId, priceToSet);
        logger.info("Product price was changed!");
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{productId}/set/discount")
    ResponseEntity<Product> changeDiscountValueByPercentage(@PathVariable int productId,
                                                            @RequestBody BigDecimal discountPercentageValue) {
        productService.changeDiscountValueOfGivenProduct(productId, discountPercentageValue);
        logger.info("Product discount was changed!");
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{productId}/set/name")
    ResponseEntity<Product> changeProductName(@PathVariable int productId,
                                              @RequestBody String newProductName) {
        productService.changeProductName(productId, newProductName);
        logger.info("Product name was changed!");
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Order> deleteOrder(@PathVariable int id) {
        productService.deleteById(id);
        logger.info("Product was deleted!");
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{productId}/copies/{copyId}")
    ResponseEntity<Customer> deleteProductsCopy(@PathVariable int productId, @PathVariable int copyId) {
        productService.deleteProductsCopy(productId, copyId);
        logger.info("Product's copy with id: " + copyId + " was deleted!");
        return ResponseEntity.ok().build();
    }

}
