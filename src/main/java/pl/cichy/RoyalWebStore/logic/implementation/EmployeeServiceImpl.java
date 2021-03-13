package pl.cichy.RoyalWebStore.logic.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import pl.cichy.RoyalWebStore.exception.AccountAlreadyExistException;
import pl.cichy.RoyalWebStore.logic.EmployeeService;
import pl.cichy.RoyalWebStore.model.Employee;
import pl.cichy.RoyalWebStore.model.User;
import pl.cichy.RoyalWebStore.model.repository.ContactRepository;
import pl.cichy.RoyalWebStore.model.repository.EmployeeRepository;
import pl.cichy.RoyalWebStore.model.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequestScope
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final EmployeeRepository employeeRepository;
    private final ContactRepository contactRepository;
    private final UserRepository userRepository;

    public EmployeeServiceImpl(final EmployeeRepository employeeRepository,
                               final ContactRepository contactRepository,
                               final UserRepository userRepository) {
        this.employeeRepository = employeeRepository;
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Page<Employee> findAll(Pageable page) {
        return employeeRepository.findAll(page);
    }

    @Override
    public Optional<Employee> findById(Integer id) {
        return employeeRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public void registerNewEmployeeAccount(Employee newEmployeeToAdd) {

        if (contactRepository.findByEmail(newEmployeeToAdd.getContact().getEmailAddress()).isPresent()) {
            throw new AccountAlreadyExistException(HttpStatus.BAD_REQUEST,
                    "Account with this email already exist!",
                    new RuntimeException());
        } else {
            Employee result = new Employee(newEmployeeToAdd.getEmployeeId(),
                    newEmployeeToAdd.getLogin(),
                    passwordEncoder.encode(newEmployeeToAdd.getPassword()),
                    newEmployeeToAdd.getFirstName(),
                    newEmployeeToAdd.getLastName(),
                    newEmployeeToAdd.getRole());

            result.getContact().setContactId(newEmployeeToAdd.getContact().getContactId());
            result.getContact().setPhoneNumber1(newEmployeeToAdd.getContact().getPhoneNumber1());
            result.getContact().setEmailAddress(newEmployeeToAdd.getContact().getEmailAddress());

            User u = new User(newEmployeeToAdd.getLogin(),
                    passwordEncoder.encode(newEmployeeToAdd.getPassword()),
                    newEmployeeToAdd.getRole());

            userRepository.save(u);

            employeeRepository.save(result);
        }
    }
}
