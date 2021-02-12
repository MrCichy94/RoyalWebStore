package pl.cichy.RoyalWebStore.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.cichy.RoyalWebStore.model.Category;
import pl.cichy.RoyalWebStore.model.repository.CategoryRepository;

@Repository
public interface SqlCategoryRepository extends CategoryRepository, JpaRepository<Category, Integer> {
}
