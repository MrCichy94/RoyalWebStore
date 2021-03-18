package pl.cichy.RoyalWebStore.logic.implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import pl.cichy.RoyalWebStore.exception.AccountAlreadyExistException;
import pl.cichy.RoyalWebStore.logic.ContactService;
import pl.cichy.RoyalWebStore.model.Contact;
import pl.cichy.RoyalWebStore.model.repository.ContactRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequestScope
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
    public void createNewContactIfEmailIsFree(Contact newContactToAdd) {

        try {
            Contact result = new Contact(newContactToAdd.getContactId(),
                    newContactToAdd.getPhoneNumber1(),
                    newContactToAdd.getEmailAddress());
            contactRepository.save(result);
        } catch (RuntimeException noAccount) {
            throw new AccountAlreadyExistException(HttpStatus.BAD_REQUEST,
                    "Account with this email already exist!",
                    new RuntimeException());
        }
    }
}
