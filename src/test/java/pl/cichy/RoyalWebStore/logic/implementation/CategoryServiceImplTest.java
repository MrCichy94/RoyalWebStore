package pl.cichy.RoyalWebStore.logic.implementation;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.cichy.RoyalWebStore.model.Category;
import pl.cichy.RoyalWebStore.model.repository.CategoryRepository;
import pl.cichy.RoyalWebStore.model.repository.ProductRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class CategoryServiceImplTest {

    @Test
    @DisplayName("Should throw ResourceNotFoundException when we dont got product with given product ID")
    void setCategoryForProductById_ThrowResourceNotFoundException() {
        //given
        var mockProductRepository = mock(ProductRepository.class);
        when(mockProductRepository.existsById(1)).thenReturn(false);
        //and
        var mockCategory = mock(Category.class);
        //and
        var mockCategoryRepository = mock(CategoryRepository.class);
        //system under test
        var toTest = new CategoryServiceImpl(mockCategoryRepository, mockProductRepository);
        //when
        var exception = catchThrowable(() -> toTest.setCategoryForProduct(1, mockCategory));
        //then
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("No product found");
    }

}