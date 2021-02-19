package pl.cichy.RoyalWebStore.logic.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.cichy.RoyalWebStore.logic.AddressService;
import pl.cichy.RoyalWebStore.model.Address;
import pl.cichy.RoyalWebStore.model.repository.AddressRepository;

import java.util.List;
import java.util.Optional;

@Service
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
    public Address getById(Integer id) {
        return addressRepository.getById(id);
    }

    @Override
    public void deleteById(Integer id) {
        addressRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return addressRepository.existsById(id);
    }

    @Override
    public Address save(Address entity) {
        return addressRepository.save(entity);
    }
}


