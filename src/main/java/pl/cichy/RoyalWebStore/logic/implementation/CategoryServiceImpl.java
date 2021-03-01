package pl.cichy.RoyalWebStore.logic.implementation;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import pl.cichy.RoyalWebStore.exception.ProductNotFoundException;
import pl.cichy.RoyalWebStore.logic.CategoryService;
import pl.cichy.RoyalWebStore.model.Category;
import pl.cichy.RoyalWebStore.model.Product;
import pl.cichy.RoyalWebStore.model.repository.CategoryRepository;
import pl.cichy.RoyalWebStore.model.repository.ProductRepository;

import java.util.List;

@Service
@RequestScope
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void setCategoryForProduct(Integer productId, Category categoryToSet) {

        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException(HttpStatus.NOT_FOUND,
                    "No product found with id: " + productId, productId);
        } else {
            Product productToActualizeCategory = productRepository.getById(productId);
            productToActualizeCategory.getCategoryAndManufacturer().setCategory(categoryToSet);
            productRepository.save(productToActualizeCategory);
        }
    }

}
