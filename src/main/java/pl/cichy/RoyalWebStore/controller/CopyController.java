package pl.cichy.RoyalWebStore.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.cichy.RoyalWebStore.logic.CopyService;
import pl.cichy.RoyalWebStore.logic.ProductService;
import pl.cichy.RoyalWebStore.model.Copy;
import pl.cichy.RoyalWebStore.model.Product;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/products/product")
public class CopyController {

    @Autowired
    private CopyService copyService;

    @Autowired
    private ProductService productService;

    private static final Logger logger = LoggerFactory.getLogger(CopyController.class);

    public CopyController() {
    }

    @GetMapping("/copies")
    ResponseEntity<List<Copy>> readAllCopies(){
        logger.info("Read all copies!");
        return ResponseEntity.ok(copyService.findAll());
    }

    @GetMapping("/{id}/copies")
    ResponseEntity<List<Copy>> readAllCopiesForProductId(@PathVariable int id) {
        logger.info("Read all of the copies of this product!");
        return ResponseEntity.ok(copyService.getCopiesByProductId(id));
    }

    @PostMapping("/{productId}")
    ResponseEntity<Copy> createNewCopyOfProduct(@PathVariable int productId,
                                                @RequestBody @Valid Copy copyOfProductToAdd) {
        copyService.setCopyForProduct(productId, copyOfProductToAdd);
        logger.info("New copy was created!");
        return ResponseEntity.created(URI.create("/" + copyOfProductToAdd.getCopyId())).body(copyOfProductToAdd);
    }

}
