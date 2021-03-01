package pl.cichy.RoyalWebStore.logic.implementation;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import pl.cichy.RoyalWebStore.exception.ProductNotFoundException;
import pl.cichy.RoyalWebStore.logic.CopyService;
import pl.cichy.RoyalWebStore.model.Copy;
import pl.cichy.RoyalWebStore.model.Product;
import pl.cichy.RoyalWebStore.model.repository.CopyRepository;
import pl.cichy.RoyalWebStore.model.repository.ProductRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
@RequestScope
public class CopyServiceImpl implements CopyService {

    private final CopyRepository copyRepository;
    private final ProductRepository productRepository;

    public CopyServiceImpl(final CopyRepository copyRepository, final ProductRepository productRepository) {
        this.copyRepository = copyRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Copy> findAll() {
        return copyRepository.findAll();
    }

    @Override
    public Page<Copy> findAll(Pageable page) {
        return copyRepository.findAll(page);
    }

    @Override
    public List<Copy> getCopiesByProductId(Integer id) {
        return copyRepository.getCopiesByProductId(id);
    }

    @Override
    public void setCopyForProduct(Integer productId, Copy copyToSet) {

        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException(HttpStatus.NOT_FOUND,
                    "No product found with id: " + productId, productId);
        } else {
            Product productToActualizeCopy = productRepository.getById(productId);
            List<Copy> listOfCopiesToRefresh = productRepository.getById(productId).getCopies();

            assignDataToCopyObject(productId, copyToSet);

            listOfCopiesToRefresh.add(copyToSet);

            productToActualizeCopy.setCopies(listOfCopiesToRefresh);
            productRepository.save(productToActualizeCopy);
        }
    }

    private void assignDataToCopyObject(Integer productId, Copy copyToSet) {
        copyToSet.setCopyId(copyToSet.getCopyId());
        copyToSet.setProductId(productId);

        BigDecimal point = BigDecimal.valueOf(-1.00);
        copyToSet.setBuyNetPrice((copyToSet.getBuyGrossPrice().multiply((point.add(copyToSet.getBuyVatPercentage()))
                .abs())).setScale(2, RoundingMode.DOWN));
        copyToSet.setBuyVatValue(copyToSet.getBuyGrossPrice().add(copyToSet.getBuyNetPrice().negate())
                .setScale(2, RoundingMode.DOWN));

        copyToSet.setDiscoutValue(new BigDecimal(0));
        copyToSet.setPercentageDiscoutValue(new BigDecimal(0));
        copyToSet.setSellCurrentGrossPrice(productRepository.getById(productId).getSellBaseGrossPrice());
        copyToSet.setSellCurrentNetPrice(productRepository.getById(productId).getSellBaseNetPrice());

        copyToSet.setBuyDate(LocalDate.now());
        copyToSet.setSellDate(null);
    }

    @Override
    public void deleteById(Integer id) {
        copyRepository.deleteById(id);
    }

    @Override
    public Copy save(Copy entity) {
        return copyRepository.save(entity);
    }
}
