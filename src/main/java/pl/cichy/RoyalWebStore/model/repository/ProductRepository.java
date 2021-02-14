package pl.cichy.RoyalWebStore.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.cichy.RoyalWebStore.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> findAll();

    Page<Product> findAll(Pageable page);

    Optional<Product> findById(Integer id);

    Product getById(Integer id);

    void deleteById(Integer id);

    boolean existsById(Integer id);

    Product save(Product entity);

}
