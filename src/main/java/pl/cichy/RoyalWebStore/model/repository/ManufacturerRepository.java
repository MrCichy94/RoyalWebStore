package pl.cichy.RoyalWebStore.model.repository;

import pl.cichy.RoyalWebStore.model.Manufacturer;

import java.util.List;

public interface ManufacturerRepository {

    List<Manufacturer> findAll();

    Manufacturer save(Manufacturer entity);

}
