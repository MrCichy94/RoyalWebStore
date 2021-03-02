package pl.cichy.RoyalWebStore.logic.implementation;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.cichy.RoyalWebStore.exception.ProductNotFoundException;
import pl.cichy.RoyalWebStore.model.Manufacturer;
import pl.cichy.RoyalWebStore.model.repository.ManufacturerRepository;
import pl.cichy.RoyalWebStore.model.repository.ProductRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ManufacturerServiceImplTest {

    @Test
    @DisplayName("Should throw ProductNotFoundException when we dont got product with given product ID")
    void setManufacturerForProductById_ThrowProductNotFoundException() {
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
        var exception = catchThrowable(() -> toTest.setManufacturerForProduct(1, mockManufacturer));
        //then
        assertThat(exception).isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("No product found");
    }

}
