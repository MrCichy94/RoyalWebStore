package pl.cichy.RoyalWebStore.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.cichy.RoyalWebStore.model.Manufacturer;
import pl.cichy.RoyalWebStore.model.repository.ManufacturerRepository;

@Repository
public interface SqlManufacturerRepository extends ManufacturerRepository, JpaRepository<Manufacturer, Integer> {
}
