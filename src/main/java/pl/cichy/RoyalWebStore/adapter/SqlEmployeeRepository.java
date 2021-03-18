package pl.cichy.RoyalWebStore.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.cichy.RoyalWebStore.model.Customer;
import pl.cichy.RoyalWebStore.model.Employee;
import pl.cichy.RoyalWebStore.model.repository.EmployeeRepository;

@Repository
public interface SqlEmployeeRepository extends EmployeeRepository, JpaRepository<Employee, Integer> {

    @Override
    @Query(nativeQuery = true, value = "SELECT * from EMPLOYEES where EMPLOYEE_ID=:id")
    Employee getById(@Param("id") Integer id);

}
