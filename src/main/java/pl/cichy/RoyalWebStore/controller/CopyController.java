package pl.cichy.RoyalWebStore.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.cichy.RoyalWebStore.logic.CopyService;
import pl.cichy.RoyalWebStore.model.Copy;

import java.util.List;

@Controller
@RequestMapping("/products/product")
public class CopyController {

    @Autowired
    private CopyService copyService;

    private static final Logger logger = LoggerFactory.getLogger(CopyController.class);

    public CopyController() {
    }

    @GetMapping("/copies")
    ResponseEntity<List<Copy>> readAllCopies() {
        logger.info("Read all of the copies of this product!");
        return ResponseEntity.ok(copyService.findAll());
    }
}
