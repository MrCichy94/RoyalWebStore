package pl.cichy.RoyalWebStore.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.cichy.RoyalWebStore.logic.SalesInvoiceService;
import pl.cichy.RoyalWebStore.model.SalesInvoice;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/salesinvoice")
public class SalesInvoiceController {

    @Autowired
    private SalesInvoiceService salesInvoiceService;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    public SalesInvoiceController() {
    }

    @GetMapping
    ResponseEntity<List<SalesInvoice>> readAllSalesInvoice() {
        logger.info("Read all the sales invoices!");
        return ResponseEntity.ok(salesInvoiceService.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<SalesInvoice> readSalesInvoiceById(@PathVariable int id) {
        logger.info("Read sales invoice with id: " + id + "!");
        return ResponseEntity.ok(salesInvoiceService.getById(id));
    }

    @PostMapping("/{customerId}/add")
    ResponseEntity<SalesInvoice> createNewSalesInvoice(@PathVariable int customerId,
                                                       @RequestBody @Valid SalesInvoice salesInvoiceToAdd) {
        salesInvoiceService.createNewSalesInvoice(customerId, salesInvoiceToAdd);
        logger.info("New sales invoice was created!");
        return ResponseEntity.created(URI.create("/" + salesInvoiceToAdd.getSalesInvoiceId())).body(salesInvoiceToAdd);
    }
}
