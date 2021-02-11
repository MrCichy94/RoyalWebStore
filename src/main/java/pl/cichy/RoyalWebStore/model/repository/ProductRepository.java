package pl.cichy.RoyalWebStore.model.repository;

import org.springframework.stereotype.Repository;
import pl.cichy.RoyalWebStore.model.Product;

import java.util.List;

public interface ProductRepository {

    List<Product> readAllProducts();
}
