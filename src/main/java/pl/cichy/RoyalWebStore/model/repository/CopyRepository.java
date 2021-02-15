package pl.cichy.RoyalWebStore.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import pl.cichy.RoyalWebStore.model.Copy;

import java.util.List;
import java.util.Optional;

public interface CopyRepository {

    List<Copy> findAll();

    Page<Copy> findAll(Pageable page);

    Optional<Copy> findById(Integer id);

    List<Copy> getCopiesByProductId(Integer id);

    void deleteById(Integer id);

    boolean existsById(Integer id);

    Copy save(Copy entity);

}
