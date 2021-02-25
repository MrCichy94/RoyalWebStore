package pl.cichy.RoyalWebStore.logic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.cichy.RoyalWebStore.model.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> findAll();

    Page<Customer> findAll(Pageable page);

    Customer getById(Integer id);

    void deleteById(Integer id);

    void registerNewCustomerAccount(Customer newCustomer);

    void deleteCustomersOrder(int customerId, int orderId);
}
