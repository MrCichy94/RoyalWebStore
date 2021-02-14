package pl.cichy.RoyalWebStore.logic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.cichy.RoyalWebStore.model.Copy;

import java.util.List;
import java.util.Optional;

public interface CopyService {

    List<Copy> findAll();

    Page<Copy> findAll(Pageable page);

    Optional<Copy> findById(Integer id);

    void deleteById(Integer id);

    boolean existsById(Integer id);

    Copy save(Copy entity);

}
