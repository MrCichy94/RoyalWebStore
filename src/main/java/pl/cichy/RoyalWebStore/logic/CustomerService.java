package pl.cichy.RoyalWebStore.logic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.cichy.RoyalWebStore.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    List<Customer> findAll();

    Page<Customer> findAll(Pageable page);

    Optional<Customer> findById(Integer id);

    Customer getById(Integer id);

    void deleteById(Integer id);

    boolean existsById(Integer id);

    Customer save(Customer entity);

    void registerNewCustomerAccount(Customer newCustomer);
}
