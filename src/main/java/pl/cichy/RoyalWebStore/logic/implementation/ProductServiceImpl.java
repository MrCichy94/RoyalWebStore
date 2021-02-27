package pl.cichy.RoyalWebStore.logic.implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import pl.cichy.RoyalWebStore.logic.ProductService;
import pl.cichy.RoyalWebStore.model.Product;
import pl.cichy.RoyalWebStore.model.repository.CopyRepository;
import pl.cichy.RoyalWebStore.model.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequestScope
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CopyRepository copyRepository;

    public ProductServiceImpl(final ProductRepository productRepository,
                              final CopyRepository copyRepository) {
        this.productRepository = productRepository;
        this.copyRepository = copyRepository;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> findAll(Pageable page) {
        return productRepository.findAll(page);
    }

    @Override
    public Optional<Product> findById(Integer id) {
        return productRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        productRepository.deleteById(id);
    }

    @Override
    public void addNewProduct(Product newProductToAdd) {
        Product result = new Product(newProductToAdd.getProductId(),
                newProductToAdd.getProductName(),
                newProductToAdd.getSellBaseGrossPrice(),
                newProductToAdd.getVatPercentage());
        productRepository.save(result);
    }

    @Override
    public void deleteProductsCopy(int productId, int copyId) {
        productRepository.getById(productId).getCopies().remove(copyId - 1);
        productRepository.save(productRepository.getById(productId));

        copyRepository.deleteById(copyId);
    }

}
