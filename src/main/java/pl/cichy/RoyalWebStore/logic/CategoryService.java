package pl.cichy.RoyalWebStore.logic;

import pl.cichy.RoyalWebStore.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    void setCategoryForProduct(Integer productId, Category categoryToSet);
}
