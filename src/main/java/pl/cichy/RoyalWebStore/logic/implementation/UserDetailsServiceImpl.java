package pl.cichy.RoyalWebStore.logic.implementation;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.cichy.RoyalWebStore.model.repository.EmployeeRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    public UserDetailsServiceImpl(final EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //todo exceptions handling .orElseThrow
        return employeeRepository.findByUsername(username).get();
    }
}
