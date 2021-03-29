package pl.cichy.RoyalWebStore.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.cichy.RoyalWebStore.model.Copy;

import java.util.List;
import java.util.Set;

public interface CopyRepository {

    List<Copy> findAll();

    Set<Copy> findAllUnique();

    Page<Copy> findAll(Pageable page);

    Set<Copy> getCopiesByProductId(Integer id);

    void deleteById(Integer id);

    Copy save(Copy entity);

    Copy getById(Integer id);

    Copy getByMerchandisingCode(String merchandising);
}
