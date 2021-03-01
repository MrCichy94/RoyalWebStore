package pl.cichy.RoyalWebStore.logic.implementation;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import pl.cichy.RoyalWebStore.exception.AccountAlreadyExistException;
import pl.cichy.RoyalWebStore.logic.AddressService;
import pl.cichy.RoyalWebStore.model.Address;
import pl.cichy.RoyalWebStore.model.repository.AddressRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequestScope
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    public AddressServiceImpl(final AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }


    @Override
    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    @Override
    public Page<Address> findAll(Pageable page) {
        return addressRepository.findAll(page);
    }

    @Override
    public Optional<Address> findById(Integer id) {
        return addressRepository.findById(id);
    }

    @Override
    public void createNewAddressIfPossible(Address newAddressToAdd) {

        if (addressRepository.findByCity(newAddressToAdd.getCity()).isPresent() &&
                addressRepository.findByStreetName(newAddressToAdd.getStreetName()).isPresent() &&
                addressRepository.findByDoorNumber(newAddressToAdd.getDoorNumber()).isPresent()) {
            throw new AccountAlreadyExistException(HttpStatus.NOT_FOUND,
                    "Account with this address already exist!");
        } else {
            Address result = new Address(newAddressToAdd.getAddressId(),
                    newAddressToAdd.getCity(),
                    newAddressToAdd.getVoivodeship(),
                    newAddressToAdd.getZipCode(),
                    newAddressToAdd.getStreetName(),
                    newAddressToAdd.getDoorNumber());

            addressRepository.save(result);
        }
    }
}


