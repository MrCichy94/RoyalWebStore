package pl.cichy.RoyalWebStore.logic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.cichy.RoyalWebStore.model.Copy;

import java.util.List;

public interface CopyService {

    List<Copy> findAll();

    Page<Copy> findAll(Pageable page);

    List<Copy> getCopiesByProductId(Integer id);

    void setCopyForProduct(Integer productId, Copy copyToSet);

    void deleteById(Integer id);

    Copy save(Copy entity);

}
