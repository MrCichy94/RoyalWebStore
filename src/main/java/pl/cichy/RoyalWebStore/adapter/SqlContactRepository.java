package pl.cichy.RoyalWebStore.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.cichy.RoyalWebStore.model.Contact;
import pl.cichy.RoyalWebStore.model.repository.ContactRepository;

import java.util.Optional;

@Repository
public interface SqlContactRepository extends ContactRepository, JpaRepository<Contact, Integer> {

    @Override
    @Query(nativeQuery = true, value = "SELECT * from CONTACTS where EMAIL_ADDRESS=:email")
    Optional<Contact> findByEmail(@Param("email") String email);

}
