package pl.cichy.RoyalWebStore.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.cichy.RoyalWebStore.logic.CopyService;
import pl.cichy.RoyalWebStore.model.Copy;

import javax.validation.Valid;
import java.net.URI;
import java.util.Set;

@Controller
@RequestMapping("/products")
public class CopyController {

    @Autowired
    private CopyService copyService;

    private static final Logger logger = LoggerFactory.getLogger(CopyController.class);

    public CopyController() {
    }

    @GetMapping("/copies")
    ResponseEntity<Set<Copy>> readAllCopies() {
        logger.info("Read all copies!");
        return ResponseEntity.ok(copyService.findAllUnique());
    }

    @GetMapping("/{id}/copies")
    ResponseEntity<Set<Copy>> readAllCopiesForProductId(@PathVariable int id) {
        logger.info("Read all of the copies of this product!");
        return ResponseEntity.ok(copyService.getCopiesByProductId(id));
    }

    @GetMapping("/{productId}/copies/{copyId}")
    ResponseEntity<Copy> readCopyWithGivenIdForProductId(@PathVariable int productId, @PathVariable int copyId) {
        logger.info("Read all of the copies of this product!");
        return ResponseEntity.ok(copyService.getById(copyId));
    }

    @PostMapping("/{productId}")
    ResponseEntity<Copy> createNewCopyOfProduct(@PathVariable Integer productId,
                                                @RequestBody @Valid Copy copyOfProductToAdd) {
        copyService.setCopyForProduct(productId, copyOfProductToAdd);
        logger.info("New copy was created!");
        return ResponseEntity.created(URI.create("/" + copyOfProductToAdd.getCopyId())).body(copyOfProductToAdd);
    }

    @PostMapping("/{productId}/{copyId}")
    ResponseEntity<Copy> changeStatusOfCopy(@PathVariable Integer productId,
                                            @PathVariable Integer copyId) {
        copyService.changeStatus(productId, copyId);
        logger.info("Copy status changed!");
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/copies/{id}")
    ResponseEntity<Copy> deleteCopy(@PathVariable int id) {
        copyService.deleteById(id);
        logger.info("Copy was deleted!");
        return ResponseEntity.ok().build();
    }
}
