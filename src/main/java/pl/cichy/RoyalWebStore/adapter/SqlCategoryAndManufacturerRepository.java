package pl.cichy.RoyalWebStore.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.cichy.RoyalWebStore.model.CategoryAndManufacturer;
import pl.cichy.RoyalWebStore.model.repository.CategoryAndManufacturerRepository;

@Repository
public interface SqlCategoryAndManufacturerRepository extends CategoryAndManufacturerRepository,
        JpaRepository<CategoryAndManufacturer, Integer> {
}
