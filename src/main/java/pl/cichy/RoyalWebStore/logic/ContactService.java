package pl.cichy.RoyalWebStore.logic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.cichy.RoyalWebStore.model.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactService {

    List<Contact> findAll();

    Page<Contact> findAll(Pageable page);

    Optional<Contact> findById(Integer id);

    void createNewContactIfEmailIsFree(Contact newContactToAdd);
}
