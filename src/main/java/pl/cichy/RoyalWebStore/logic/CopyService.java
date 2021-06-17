package pl.cichy.RoyalWebStore.logic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.cichy.RoyalWebStore.model.Copy;

import java.util.List;
import java.util.Set;

public interface CopyService {

    List<Copy> findAll();

    Set<Copy> findAllUnique();

    Page<Copy> findAll(Pageable page);

    Set<Copy> getCopiesByProductId(Integer id);

    void setCopyForProduct(Integer productId, Copy copyToSet);

    void deleteById(Integer id);

    Copy save(Copy entity);

    Copy getById(Integer id);

    void changeStatus(Integer productId, Integer copyId);
}
