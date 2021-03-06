package pl.cichy.RoyalWebStore.logic.implementation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.cichy.RoyalWebStore.exception.AccountAlreadyExistException;
import pl.cichy.RoyalWebStore.model.Contact;
import pl.cichy.RoyalWebStore.model.repository.ContactRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ContactServiceImplTest {

    @Test
    @DisplayName("Should throw AccountAlreadyExistException when we got already existing object with email")
    void createNewContactIfEmailIsFree_ThrowAccountAlreadyExistException() {
        //given
        var mockContact = mock(Contact.class);
        //and
        var mockContactRepository = mock(ContactRepository.class);
        when(mockContactRepository.findByEmailLogin(mockContact.getEmailAddress()))
                .thenReturn(Optional.of(mockContact));
        //system under test
        var toTest = new ContactServiceImpl(mockContactRepository);
        //when
        var exception = catchThrowable(() -> toTest.createNewContactIfEmailIsFree(mockContact));
        //then
        assertThat(exception).isInstanceOf(AccountAlreadyExistException.class)
                .hasMessageContaining("Account with this email");
    }
}
