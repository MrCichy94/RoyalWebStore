package pl.cichy.RoyalWebStore.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.cichy.RoyalWebStore.model.Copy;

import java.util.List;

public interface CopyRepository {

    List<Copy> findAll();

    Page<Copy> findAll(Pageable page);

    List<Copy> getCopiesByProductId(Integer id);

    void deleteById(Integer id);

    Copy save(Copy entity);

    Copy getById(Integer id);
}
