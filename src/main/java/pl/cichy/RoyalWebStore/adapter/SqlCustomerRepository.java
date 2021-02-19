package pl.cichy.RoyalWebStore.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.cichy.RoyalWebStore.model.Customer;
import pl.cichy.RoyalWebStore.model.repository.CustomerRepository;

@Repository
public interface SqlCustomerRepository extends CustomerRepository, JpaRepository<Customer, Integer> {

}