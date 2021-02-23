package pl.cichy.RoyalWebStore.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.cichy.RoyalWebStore.logic.SalesInvoicePositionsService;
import pl.cichy.RoyalWebStore.model.SalesInvoicePositions;

import java.util.List;

@Controller
@RequestMapping("/salesinvoicepositions")
public class SalesInvoicePositionsController {

    @Autowired
    private SalesInvoicePositionsService salesInvoicePositionsService;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    public SalesInvoicePositionsController() {
    }

    @GetMapping
    ResponseEntity<List<SalesInvoicePositions>> readAllSalesInvoicePositions() {
        logger.info("Read all the sales invoice positions!");
        return ResponseEntity.ok(salesInvoicePositionsService.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<SalesInvoicePositions> readSalesInvoicePositionsById(@PathVariable int id) {
        logger.info("Read sales invoice positions with id: " + id + "!");
        return ResponseEntity.ok(salesInvoicePositionsService.getById(id));
    }
}
