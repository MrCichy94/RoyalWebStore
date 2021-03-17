package pl.cichy.RoyalWebStore.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
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

    void updateCustomersData(int customerId, JsonPatch customerToUpdate) throws JsonPatchException, JsonProcessingException;
}
