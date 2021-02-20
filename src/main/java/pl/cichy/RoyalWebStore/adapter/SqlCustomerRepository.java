package pl.cichy.RoyalWebStore.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.cichy.RoyalWebStore.model.Customer;
import pl.cichy.RoyalWebStore.model.repository.CustomerRepository;

@Repository
public interface SqlCustomerRepository extends CustomerRepository, JpaRepository<Customer, Integer> {

    @Override
    @Query(nativeQuery = true, value = "SELECT * from CUSTOMERS where CUSTOMER_ID=:id")
    Customer getById(@Param("id") Integer id);

}
