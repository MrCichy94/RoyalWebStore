package pl.cichy.RoyalWebStore.logic;

import pl.cichy.RoyalWebStore.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    void deleteById(Integer id);

    Category save(Category entity);
}
