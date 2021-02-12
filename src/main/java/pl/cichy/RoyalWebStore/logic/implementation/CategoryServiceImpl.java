package pl.cichy.RoyalWebStore.logic.implementation;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import pl.cichy.RoyalWebStore.logic.CategoryService;
import pl.cichy.RoyalWebStore.model.Category;
import pl.cichy.RoyalWebStore.model.repository.CategoryRepository;

import java.util.List;

@Service
@RequestScope
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
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
}
