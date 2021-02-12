package pl.cichy.RoyalWebStore.model.repository.implementation;

import org.springframework.stereotype.Repository;
import pl.cichy.RoyalWebStore.logic.ProductService;
import pl.cichy.RoyalWebStore.model.Product;
import pl.cichy.RoyalWebStore.model.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryProductRepository implements ProductRepository {

    private final List<Product> listOfProducts = new ArrayList<Product>();

    private ProductRepository productRepository;

    public InMemoryProductRepository() {
        Product example1 = new Product(0,"Rewolution 5",
                new BigDecimal("199.99"), new BigDecimal("0.23"));
        example1.setProductDescription("Marka będzie ustawiana, decimale liczone metodami, vat tez.");

        listOfProducts.add(example1);
    }

    public List<Product> readAllProducts() {
        return listOfProducts;
    }

}
