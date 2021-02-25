package pl.cichy.RoyalWebStore.logic;

import pl.cichy.RoyalWebStore.model.Manufacturer;

import java.util.List;

public interface ManufacturerService {

    List<Manufacturer> findAll();

    void setManufacturerForProduct(Integer productId, Manufacturer manufacturerToSet);
}
