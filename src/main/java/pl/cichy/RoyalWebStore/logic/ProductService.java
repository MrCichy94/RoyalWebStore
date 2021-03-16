package pl.cichy.RoyalWebStore.logic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.cichy.RoyalWebStore.model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAll();

    Page<Product> findAll(Pageable page);

    Optional<Product> findById(Integer id);

    void deleteById(Integer id);

    void deleteProductsCopy(int productId, int copyId);

    void addNewProduct(Product newProductToAdd);

    void changeProductPriceByValue(int productId, BigDecimal priceToSet);

    void changeDiscountValueOfGivenProduct(int productId, BigDecimal discountPercentageValue);

    void changeProductName(int productId, String newProductName);

    void setTypeOfGivenProduct(int productId, String newProductType);
}
