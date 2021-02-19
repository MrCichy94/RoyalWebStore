package pl.cichy.RoyalWebStore.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.cichy.RoyalWebStore.model.Address;

import java.util.List;
import java.util.Optional;

public interface AddressRepository {

    List<Address> findAll();

    Page<Address> findAll(Pageable page);

    Optional<Address> findById(Integer id);

    void deleteById(Integer id);

    boolean existsById(Integer id);

    Address save(Address entity);
}
