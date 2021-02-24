package pl.cichy.RoyalWebStore.model.repository;

import pl.cichy.RoyalWebStore.model.CategoryAndManufacturer;

import java.util.List;

public interface CategoryAndManufacturerRepository {

    List<CategoryAndManufacturer> findAll();

}
