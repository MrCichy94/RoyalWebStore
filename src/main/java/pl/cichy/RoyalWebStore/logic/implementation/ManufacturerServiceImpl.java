package pl.cichy.RoyalWebStore.logic.implementation;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import pl.cichy.RoyalWebStore.logic.ManufacturerService;
import pl.cichy.RoyalWebStore.model.Manufacturer;
import pl.cichy.RoyalWebStore.model.repository.ManufacturerRepository;

import java.util.List;

@Service
@RequestScope
public class ManufacturerServiceImpl implements ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
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

}
