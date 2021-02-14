package pl.cichy.RoyalWebStore.model.repository;

import pl.cichy.RoyalWebStore.model.Manufacturer;

import java.util.List;

public interface ManufacturerRepository {

    List<Manufacturer> findAll();

    void deleteById(Integer id);

    Manufacturer save(Manufacturer entity);

}
