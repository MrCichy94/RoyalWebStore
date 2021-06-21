package pl.cichy.RoyalWebStore.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.cichy.RoyalWebStore.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {

    List<Customer> findAll();

    Page<Customer> findAll(Pageable page);

    Optional<Customer> findById(Integer id);

    Customer getById(Integer id);

    Customer getCustomerByUsername(String username);

    void deleteById(Integer id);

    boolean existsById(Integer id);

    Customer save(Customer entity);

    Customer getByEmailLogin(String name);

}
