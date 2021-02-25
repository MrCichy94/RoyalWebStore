package pl.cichy.RoyalWebStore.logic.implementation;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import pl.cichy.RoyalWebStore.logic.EmployeeService;
import pl.cichy.RoyalWebStore.model.Employee;
import pl.cichy.RoyalWebStore.model.repository.ContactRepository;
import pl.cichy.RoyalWebStore.model.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequestScope
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final EmployeeRepository employeeRepository;
    private final ContactRepository contactRepository;

    public EmployeeServiceImpl(final EmployeeRepository employeeRepository,
                               final ContactRepository contactRepository) {
        this.employeeRepository = employeeRepository;
        this.contactRepository = contactRepository;
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
    public Employee getById(Integer id) {
        return employeeRepository.getById(id);
    }

    @Override
    public void deleteById(Integer id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return employeeRepository.existsById(id);
    }

    @Override
    public Employee save(Employee entity) {
        return employeeRepository.save(entity);
    }

    @Override
    public void registerNewEmployeeAccount(Employee newEmployeeToAdd) {

        if (contactRepository.findByEmail(newEmployeeToAdd.getContact().getEmailAddress()).isPresent()) {
            throw new ResourceNotFoundException("Account with this email already exist!");
        } else {
            Employee result = new Employee(newEmployeeToAdd.getEmployeeId(),
                    newEmployeeToAdd.getLogin(),
                    passwordEncoder.encode(newEmployeeToAdd.getPassword()),
                    newEmployeeToAdd.getFirstName(),
                    newEmployeeToAdd.getLastName(),
                    newEmployeeToAdd.getTypeOfPermissions());

            result.getContact().setContactId(newEmployeeToAdd.getContact().getContactId());
            result.getContact().setPhoneNumber1(newEmployeeToAdd.getContact().getPhoneNumber1());
            result.getContact().setEmailAddress(newEmployeeToAdd.getContact().getEmailAddress());

            employeeRepository.save(result);
        }
    }
}