package pl.cichy.RoyalWebStore.model.repository;

import pl.cichy.RoyalWebStore.model.Category;

import java.util.List;

public interface CategoryRepository {

    List<Category> findAll();

    void deleteById(Integer id);

    Category save(Category entity);

}
