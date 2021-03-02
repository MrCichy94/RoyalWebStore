package pl.cichy.RoyalWebStore.logic.implementation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.cichy.RoyalWebStore.exception.AccountAlreadyExistException;
import pl.cichy.RoyalWebStore.model.Address;
import pl.cichy.RoyalWebStore.model.repository.AddressRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AddressServiceImplTest {

    @Test
    @DisplayName("Should throw AccountAlreadyExistException when we got address with already existing the same city" +
            "and street name and door number")
    void createNewAddressIfPossible_ThrowAccountAlreadyExistException() {
        //given
        var mockAddress = mock(Address.class);
        //and
        var mockAddressRepository = mock(AddressRepository.class);
        when(mockAddressRepository
                .findByDoorNumber(mockAddress.getCity()))
                .thenReturn(Optional.of(mockAddress));
        when(mockAddressRepository
                .findByStreetName(mockAddress.getStreetName()))
                .thenReturn(Optional.of(mockAddress));
        when(mockAddressRepository
                .findByCity(mockAddress.getDoorNumber()))
                .thenReturn(Optional.of(mockAddress));
        //system under test
        var toTest = new AddressServiceImpl(mockAddressRepository);
        //when
        var exception = catchThrowable(() -> toTest.createNewAddressIfPossible(mockAddress));
        //then
        assertThat(exception).isInstanceOf(AccountAlreadyExistException.class)
                .hasMessageContaining("Account with this");
    }

    @Test
    @DisplayName("Should create a new address if door number is diffrent without throwing exceptions")
    void createNewAddressIfPossible_work_properly_cif_door_number_is_diffrent() {
        //given
        var mockAddress = mock(Address.class);
        //and
        var mockAddressRepository = mock(AddressRepository.class);
        when(mockAddressRepository
                .findByDoorNumber(mockAddress.getCity()))
                .thenReturn(Optional.empty());
        when(mockAddressRepository
                .findByStreetName(mockAddress.getStreetName()))
                .thenReturn(Optional.of(mockAddress));
        when(mockAddressRepository
                .findByCity(mockAddress.getDoorNumber()))
                .thenReturn(Optional.of(mockAddress));
        //system under test
        var toTest = new AddressServiceImpl(mockAddressRepository);
        //when
        //then
        Assertions.assertThatCode(() -> toTest.createNewAddressIfPossible(mockAddress))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Should create a new address if street name is diffrent without throwing exceptions")
    void createNewAddressIfPossible_work_properly_if_street_name_is_diffrent() {
        //given
        var mockAddress = mock(Address.class);
        //and
        var mockAddressRepository = mock(AddressRepository.class);
        when(mockAddressRepository
                .findByDoorNumber(mockAddress.getCity()))
                .thenReturn(Optional.of(mockAddress));
        when(mockAddressRepository
                .findByStreetName(mockAddress.getStreetName()))
                .thenReturn(Optional.empty());
        when(mockAddressRepository
                .findByCity(mockAddress.getDoorNumber()))
                .thenReturn(Optional.of(mockAddress));
        //system under test
        var toTest = new AddressServiceImpl(mockAddressRepository);
        //when
        //then
        Assertions.assertThatCode(() -> toTest.createNewAddressIfPossible(mockAddress))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Should create a new address if city is diffrent without throwing exceptions")
    void createNewAddressIfPossible_work_properly_if_city_is_diffrent() {
        //given
        var mockAddress = mock(Address.class);
        //and
        var mockAddressRepository = mock(AddressRepository.class);
        when(mockAddressRepository
                .findByDoorNumber(mockAddress.getCity()))
                .thenReturn(Optional.of(mockAddress));
        when(mockAddressRepository
                .findByStreetName(mockAddress.getStreetName()))
                .thenReturn(Optional.of(mockAddress));
        when(mockAddressRepository
                .findByCity(mockAddress.getDoorNumber()))
                .thenReturn(Optional.empty());
        //system under test
        var toTest = new AddressServiceImpl(mockAddressRepository);
        //when
        //then
        Assertions.assertThatCode(() -> toTest.createNewAddressIfPossible(mockAddress))
                .doesNotThrowAnyException();
    }

}
