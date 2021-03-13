package pl.cichy.RoyalWebStore.logic.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import pl.cichy.RoyalWebStore.exception.AccountAlreadyExistException;
import pl.cichy.RoyalWebStore.logic.CustomerService;
import pl.cichy.RoyalWebStore.model.Customer;
import pl.cichy.RoyalWebStore.model.User;
import pl.cichy.RoyalWebStore.model.repository.ContactRepository;
import pl.cichy.RoyalWebStore.model.repository.CustomerRepository;
import pl.cichy.RoyalWebStore.model.repository.OrderRepository;
import pl.cichy.RoyalWebStore.model.repository.UserRepository;

import java.util.List;

@Service
@RequestScope
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final CustomerRepository customerRepository;
    private final ContactRepository contactRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public CustomerServiceImpl(final CustomerRepository customerRepository,
                               final ContactRepository contactRepository,
                               final OrderRepository orderRepository,
                               final UserRepository userRepository) {
        this.customerRepository = customerRepository;
        this.contactRepository = contactRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
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
    public Customer getById(Integer id) {
        return customerRepository.getById(id);
    }

    @Override
    public void deleteById(Integer id) {
        customerRepository.deleteById(id);
    }

    public void registerNewCustomerAccount(Customer newCustomer) {

        if (contactRepository.findByEmail(newCustomer.getContact().getEmailAddress()).isPresent()) {
            throw new AccountAlreadyExistException(HttpStatus.BAD_REQUEST,
                    "Account with this email already exist!",
                    new RuntimeException());
        } else {
            Customer result = new Customer(newCustomer.getCustomerId(),
                    newCustomer.getLogin(),
                    passwordEncoder.encode(newCustomer.getPassword()),
                    newCustomer.getFirstName(),
                    newCustomer.getLastName(),
                    newCustomer.getTypeOfClient(),
                    newCustomer.getRole());

            result.getContact().setContactId(newCustomer.getContact().getContactId());
            result.getContact().setPhoneNumber1(newCustomer.getContact().getPhoneNumber1());
            result.getContact().setEmailAddress(newCustomer.getContact().getEmailAddress());

            User u = new User(newCustomer.getLogin(),
                    passwordEncoder.encode(newCustomer.getPassword()),
                    newCustomer.getRole());

            userRepository.save(u);
            customerRepository.save(result);
        }
    }

    @Override
    public void deleteCustomersOrder(int customerId, int orderId) {
        customerRepository.getById(customerId).getOrders().remove(orderId - 1);
        customerRepository.save(customerRepository.getById(customerId));

        orderRepository.deleteById(orderId);
    }

}
