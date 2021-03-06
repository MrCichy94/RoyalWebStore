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

    void createNewAddressIfPossible(Address newAddressToAdd);
}
