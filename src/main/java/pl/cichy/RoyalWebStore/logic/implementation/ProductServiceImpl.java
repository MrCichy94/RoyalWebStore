package pl.cichy.RoyalWebStore.logic.implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import pl.cichy.RoyalWebStore.exception.ProductNotFoundException;
import pl.cichy.RoyalWebStore.logic.ProductService;
import pl.cichy.RoyalWebStore.model.Copy;
import pl.cichy.RoyalWebStore.model.Product;
import pl.cichy.RoyalWebStore.model.repository.CopyRepository;
import pl.cichy.RoyalWebStore.model.repository.ProductRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
                newProductToAdd.getProductManufacturer(),
                newProductToAdd.getProductCategory(),
                newProductToAdd.getSellBaseGrossPrice(),
                newProductToAdd.getVatPercentage());
        productRepository.save(result);
    }

    @Override
    public void changeProductPriceByValue(int productId, BigDecimal priceToSet) {

        try {
            Product result = productRepository.getById(productId);

            changeProductDataIfThePriceChanges(priceToSet, result);

            productRepository.save(result);
        } catch (RuntimeException noProduct) {
            throw new ProductNotFoundException(HttpStatus.NOT_FOUND,
                    "No product found with id: " + productId,
                    new RuntimeException(),
                    productId);
        }

    }

    @Override
    public void changeProductName(int productId, String newProductName) {

        try {
            Product result = productRepository.getById(productId);
            result.setProductName(newProductName);
            productRepository.save(result);
        } catch (RuntimeException noProduct) {
            throw new ProductNotFoundException(HttpStatus.NOT_FOUND,
                    "No product found with id: " + productId,
                    new RuntimeException(),
                    productId);
        }

    }

    @Override
    public void setTypeOfGivenProduct(int productId, String newProductType) {

        try {
            Product result = productRepository.getById(productId);
            result.setType(newProductType);
            productRepository.save(result);
        } catch (RuntimeException noProduct) {
            throw new ProductNotFoundException(HttpStatus.NOT_FOUND,
                    "No product found with id: " + productId,
                    new RuntimeException(),
                    productId);
        }

    }

    @Override
    public void setVersionOfGivenProduct(int productId, String newProductVersion) {

        try {
            Product result = productRepository.getById(productId);
            result.setVersion(newProductVersion);
            productRepository.save(result);
        } catch (RuntimeException noProduct) {
            throw new ProductNotFoundException(HttpStatus.NOT_FOUND,
                    "No product found with id: " + productId,
                    new RuntimeException(),
                    productId);
        }

    }

    @Override
    public void setDescriptionOfGivenProduct(int productId, String newProductDescription) {

        try {
            Product result = productRepository.getById(productId);
            result.setProductDescription(newProductDescription);
            productRepository.save(result);
        } catch (RuntimeException noProduct) {
            throw new ProductNotFoundException(HttpStatus.NOT_FOUND,
                    "No product found with id: " + productId,
                    new RuntimeException(),
                    productId);
        }

    }

    @Override
    public void changeDiscountValueOfGivenProduct(int productId, BigDecimal discountPercentageValue) {

        try {
            Product result = productRepository.getById(productId);

            discountTheProductAndSetNewValueAfterThat(discountPercentageValue, result);

            productRepository.save(result);
        } catch (RuntimeException noProduct) {
            throw new ProductNotFoundException(HttpStatus.NOT_FOUND,
                    "No product found with id: " + productId,
                    new RuntimeException(),
                    productId);
        }

    }

    @Override
    public void deleteProductsCopy(int productId, int copyId) {
        productRepository.getById(productId).getCopies().remove(copyId - 1);
        productRepository.save(productRepository.getById(productId));

        copyRepository.deleteById(copyId);
    }

    private void changeProductDataIfThePriceChanges(BigDecimal priceToSet, Product result) {
        result.setSellBaseGrossPrice(priceToSet);
        BigDecimal point = (BigDecimal.ONE).negate();
        BigDecimal newNetPrice = (priceToSet.multiply((point.add(result.getVatPercentage()))
                .abs())).setScale(2, RoundingMode.DOWN);
        result.setSellBaseNetPrice(newNetPrice);
        result.setVatValue(priceToSet.add(newNetPrice.negate())
                .setScale(2, RoundingMode.DOWN));

        Set<Copy> items = result.getCopies();
        for (Copy it : items) {
            it.setSellCurrentGrossPrice(priceToSet);
            it.setSellCurrentNetPrice(result.getSellBaseNetPrice());
        }
        result.setCopies(items);
    }

    private void discountTheProductAndSetNewValueAfterThat(BigDecimal discountPercentageValue, Product result) {
        BigDecimal point = (BigDecimal.ONE).negate();
        BigDecimal oldGrossPrice = result.getSellBaseGrossPrice();
        BigDecimal newGrossPrice = (oldGrossPrice.multiply((point.add(discountPercentageValue))
                .abs())).setScale(2, RoundingMode.DOWN);
        BigDecimal newNetPrice = ((newGrossPrice.multiply((point.add(result.getVatPercentage()))
                .abs())).setScale(2, RoundingMode.DOWN));
        BigDecimal newVatValue = newGrossPrice.add(newNetPrice.negate())
                .setScale(2, RoundingMode.DOWN);

        Set<Copy> items = result.getCopies();

        for (Copy it : items) {
            it.setDiscountValue(discountPercentageValue);
            it.setSellCurrentGrossPrice(newGrossPrice);
            it.setSellCurrentNetPrice(newNetPrice);
        }

        result.setCopies(items);
        result.setSellBaseGrossPrice(newGrossPrice);
        result.setSellBaseNetPrice(newNetPrice);
        result.setVatValue(newVatValue);
    }

}
