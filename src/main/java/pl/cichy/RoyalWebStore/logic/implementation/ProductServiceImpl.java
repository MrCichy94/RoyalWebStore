package pl.cichy.RoyalWebStore.logic.implementation;

import org.springframework.stereotype.Service;
import pl.cichy.RoyalWebStore.logic.ProductService;
import pl.cichy.RoyalWebStore.model.Product;
import pl.cichy.RoyalWebStore.model.repository.ProductRepository;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> readAllProducts() {
        return productRepository.readAllProducts();
    }
}
