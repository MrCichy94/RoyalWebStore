package pl.cichy.RoyalWebStore.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.cichy.RoyalWebStore.logic.ContactService;
import pl.cichy.RoyalWebStore.model.Contact;
import pl.cichy.RoyalWebStore.model.Product;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    public ContactController() {
    }

    @GetMapping
    ResponseEntity<List<Contact>> readAllContacts() {
        logger.info("Read all the contacts!");
        return ResponseEntity.ok(contactService.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<Contact> readContactById(@PathVariable int id) {
        logger.info("Read contact with id: " + id + "!");
        return contactService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    ResponseEntity<Contact> addNewContact(@RequestBody  @Valid Contact newContactToAdd) {
        contactService.createNewContactIfEmailIsFree(newContactToAdd);
        logger.info("New contact was created!");
        return ResponseEntity.created(URI.create("/" + newContactToAdd.getContactId())).body(newContactToAdd);
    }
}
