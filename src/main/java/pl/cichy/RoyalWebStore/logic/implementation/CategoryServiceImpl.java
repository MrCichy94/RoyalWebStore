package pl.cichy.RoyalWebStore.logic.implementation;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import pl.cichy.RoyalWebStore.logic.CategoryService;
import pl.cichy.RoyalWebStore.model.Category;
import pl.cichy.RoyalWebStore.model.Product;
import pl.cichy.RoyalWebStore.model.repository.CategoryRepository;
import pl.cichy.RoyalWebStore.model.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

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
    public List<Category> findAll(){
        return categoryRepository.findAll();
    };

    @Override
    public void deleteById(Integer id){
        categoryRepository.deleteById(id);
    };

    @Override
    public Category save(Category entity){
        return categoryRepository.save(entity);
    }

    @Override
    public void setCategoryForProduct(Integer productId, Category categoryToSet) {

        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("No product found with id=" + productId);
        } else {
            Product productToActualizeCategory = productRepository.getById(productId);
            productToActualizeCategory.getCategoryAndManufacturer().setCategory(categoryToSet);
            productRepository.save(productToActualizeCategory);
        }
    }

}
