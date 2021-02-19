package pl.cichy.RoyalWebStore.logic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.cichy.RoyalWebStore.model.Address;

import java.util.List;
import java.util.Optional;

public interface AddressService {

    List<Address> findAll();

    Page<Address> findAll(Pageable page);

    Optional<Address> findById(Integer id);

    void deleteById(Integer id);

    boolean existsById(Integer id);

    Address save(Address entity);

    Optional<Address> findByCity(String city);

    Optional<Address> findByStreetName(String streetName);

    Optional<Address> findByDoorNumber(String doorNumber);

    void createNewAddressIfPossible(Address newAddressToAdd);
}
