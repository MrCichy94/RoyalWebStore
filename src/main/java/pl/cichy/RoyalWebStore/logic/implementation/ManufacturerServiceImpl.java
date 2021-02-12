package pl.cichy.RoyalWebStore.logic.implementation;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import pl.cichy.RoyalWebStore.logic.ManufacturerService;
import pl.cichy.RoyalWebStore.model.Manufacturer;
import pl.cichy.RoyalWebStore.model.Product;
import pl.cichy.RoyalWebStore.model.repository.ManufacturerRepository;
import pl.cichy.RoyalWebStore.model.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequestScope
public class ManufacturerServiceImpl implements ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;
    private final ProductRepository productRepository;

    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository, ProductRepository productRepository) {
        this.manufacturerRepository = manufacturerRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Manufacturer> findAll(){
        return manufacturerRepository.findAll();
    };

    @Override
    public void deleteById(Integer id){
        manufacturerRepository.deleteById(id);
    };

    @Override
    public Manufacturer save(Manufacturer entity){
        return manufacturerRepository.save(entity);
    }

    @Override
    public void setManufacturerForProduct(Integer productId, Manufacturer manufacturerToSet) {
        Product productToActualizeManufacturer = productRepository.getById(productId);
        productToActualizeManufacturer.getCategoryAndManufacturer().setManufacturer(manufacturerToSet);
        productRepository.save(productToActualizeManufacturer);
    }

}
