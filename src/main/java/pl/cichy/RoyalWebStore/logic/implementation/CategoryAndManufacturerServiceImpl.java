package pl.cichy.RoyalWebStore.logic.implementation;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import pl.cichy.RoyalWebStore.logic.CategoryAndManufacturerService;
import pl.cichy.RoyalWebStore.logic.ProductService;
import pl.cichy.RoyalWebStore.model.CategoryAndManufacturer;
import pl.cichy.RoyalWebStore.model.repository.CategoryAndManufacturerRepository;

import java.util.List;

@Service
@RequestScope
public class CategoryAndManufacturerServiceImpl implements CategoryAndManufacturerService {

    private final CategoryAndManufacturerRepository categoryAndManufacturerRepository;

    public CategoryAndManufacturerServiceImpl(final CategoryAndManufacturerRepository categoryAndManufacturerRepository) {
        this.categoryAndManufacturerRepository = categoryAndManufacturerRepository;
    }

    @Override
    public List<CategoryAndManufacturer> findAll(){
        return categoryAndManufacturerRepository.findAll();
    };

    @Override
    public void deleteById(Integer id){
        categoryAndManufacturerRepository.deleteById(id);
    };

    @Override
    public CategoryAndManufacturer save(CategoryAndManufacturer entity){
        return categoryAndManufacturerRepository.save(entity);
    }
}
