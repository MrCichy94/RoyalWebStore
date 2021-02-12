package pl.cichy.RoyalWebStore.model.repository;

import pl.cichy.RoyalWebStore.model.Manufacturer;
import pl.cichy.RoyalWebStore.model.Product;

import java.util.List;
import java.util.Optional;

public interface ManufacturerRepository {

    List<Manufacturer> findAll();

    void deleteById(Integer id);

    Manufacturer save(Manufacturer entity);

}
