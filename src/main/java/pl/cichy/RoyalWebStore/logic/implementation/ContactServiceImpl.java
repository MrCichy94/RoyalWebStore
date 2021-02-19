package pl.cichy.RoyalWebStore.logic.implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.cichy.RoyalWebStore.logic.ContactService;
import pl.cichy.RoyalWebStore.model.Contact;
import pl.cichy.RoyalWebStore.model.repository.ContactRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    public ContactServiceImpl(final ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }


    @Override
    public List<Contact> findAll() {
        return contactRepository.findAll();
    }

    @Override
    public Page<Contact> findAll(Pageable page) {
        return contactRepository.findAll(page);
    }

    @Override
    public Optional<Contact> findById(Integer id) {
        return contactRepository.findById(id);
    }

    @Override
    public Contact getById(Integer id) {
        return contactRepository.getById(id);
    }

    @Override
    public void deleteById(Integer id) {
        contactRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return contactRepository.existsById(id);
    }

    @Override
    public Contact save(Contact entity) {
        return contactRepository.save(entity);
    }
}
