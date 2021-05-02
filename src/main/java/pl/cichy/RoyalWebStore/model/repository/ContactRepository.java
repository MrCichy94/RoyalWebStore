package pl.cichy.RoyalWebStore.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.cichy.RoyalWebStore.model.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactRepository {

    List<Contact> findAll();

    Page<Contact> findAll(Pageable page);

    Optional<Contact> findById(Integer id);

    Optional<Contact> findByEmailLogin(String email);

    Contact save(Contact entity);

}
