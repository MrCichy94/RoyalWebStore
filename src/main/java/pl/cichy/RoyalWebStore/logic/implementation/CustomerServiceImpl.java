package pl.cichy.RoyalWebStore.logic.implementation;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import pl.cichy.RoyalWebStore.logic.CustomerService;
import pl.cichy.RoyalWebStore.model.Customer;
import pl.cichy.RoyalWebStore.model.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequestScope
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(final CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Page<Customer> findAll(Pageable page) {
        return customerRepository.findAll(page);
    }

    @Override
    public Optional<Customer> findById(Integer id) {
        return customerRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        customerRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return customerRepository.existsById(id);
    }

    @Override
    public Customer save(Customer entity) {
        return customerRepository.save(entity);
    }


    public Customer registerNewCustomerAccount(Customer newCustomer) {
        if (!customerRepository.existsById(newCustomer.getCustomerId())) {
            throw new ResourceNotFoundException("There is an account with that email adress:"
                    + newCustomer.getContact().getEmailAddress());
            //set things here
        }
        return customerRepository.save(newCustomer);
    }

}
