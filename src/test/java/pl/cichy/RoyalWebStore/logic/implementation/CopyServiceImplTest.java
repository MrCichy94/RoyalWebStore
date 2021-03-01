package pl.cichy.RoyalWebStore.logic.implementation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.cichy.RoyalWebStore.exception.ProductNotFoundException;
import pl.cichy.RoyalWebStore.model.Copy;
import pl.cichy.RoyalWebStore.model.repository.CopyRepository;
import pl.cichy.RoyalWebStore.model.repository.ProductRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CopyServiceImplTest {

    @Test
    @DisplayName("Should throw ResourceNotFoundException when we dont got product with given product ID")
    void setCopyForProduct_ThrowResourceNotFoundException() {
        //given
        var mockProductRepository = mock(ProductRepository.class);
        when(mockProductRepository.existsById(1)).thenReturn(false);
        //and
        var mockCopy = mock(Copy.class);
        //and
        var mockCopyRepository = mock(CopyRepository.class);
        //system under test
        var toTest = new CopyServiceImpl(mockCopyRepository, mockProductRepository);
        //when
        var exception = catchThrowable(() -> toTest.setCopyForProduct(1, mockCopy));
        //then
        assertThat(exception).isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("No product found");
    }

}
