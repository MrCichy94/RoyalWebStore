package pl.cichy.RoyalWebStore.logic;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.cichy.RoyalWebStore.logic.implementation.ManufacturerServiceImpl;
import pl.cichy.RoyalWebStore.model.Manufacturer;
import pl.cichy.RoyalWebStore.model.repository.ManufacturerRepository;
import pl.cichy.RoyalWebStore.model.repository.ProductRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ManufacturerServiceTest {

    @Test
    @DisplayName("Should throw ResourceNotFoundException when we dont got product with given product ID")
    void createManufacturerForProductById_ThrowResourceNotFoundException(){
        //given
        var mockProductRepository = mock(ProductRepository.class);
        when(mockProductRepository.existsById(1)).thenReturn(false);
        //and
        var mockManufacturer = mock(Manufacturer.class);
        //and
        var mockManufacturerRepository = mock(ManufacturerRepository.class);
        //system under test
        var toTest = new ManufacturerServiceImpl(mockManufacturerRepository, mockProductRepository);
        //when
        var exception = catchThrowable(()->toTest.setManufacturerForProduct(1,mockManufacturer));
        //then
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("No product found");
    }

}
