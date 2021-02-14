package pl.cichy.RoyalWebStore.logic.implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import pl.cichy.RoyalWebStore.logic.CopyService;
import pl.cichy.RoyalWebStore.model.Copy;
import pl.cichy.RoyalWebStore.model.repository.CopyRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequestScope
public class CopyServiceImpl implements CopyService {

    private final CopyRepository copyRepository;

    public CopyServiceImpl(final CopyRepository copyRepository) {
        this.copyRepository = copyRepository;
    }

    @Override
    public List<Copy> findAll() {
        return copyRepository.findAll();
    }

    @Override
    public Page<Copy> findAll(Pageable page) {
        return copyRepository.findAll(page);
    }

    @Override
    public Optional<Copy> findById(Integer id) {
        return copyRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        copyRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return copyRepository.existsById(id);
    }

    @Override
    public Copy save(Copy entity) {
        return copyRepository.save(entity);
    }
}
