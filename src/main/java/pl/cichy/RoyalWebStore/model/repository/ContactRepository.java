package pl.cichy.RoyalWebStore.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.cichy.RoyalWebStore.model.Contact;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository {

    List<Contact> findAll();

    Page<Contact> findAll(Pageable page);

    Optional<Contact> findById(Integer id);

    Contact getById(Integer id);

    void deleteById(Integer id);

    boolean existsById(Integer id);

    Contact save(Contact entity);
}
