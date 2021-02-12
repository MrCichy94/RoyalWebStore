package pl.cichy.RoyalWebStore.logic;

import pl.cichy.RoyalWebStore.model.CategoryAndManufacturer;

import java.util.List;

public interface CategoryAndManufacturerService {

    List<CategoryAndManufacturer> findAll();

    void deleteById(Integer id);

    CategoryAndManufacturer save(CategoryAndManufacturer entity);

}
