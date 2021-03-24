package pl.cichy.RoyalWebStore.logic.implementation;

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

        try {
            Product productToActualizeCopy = productRepository.getById(productId);
            List<Copy> listOfCopiesToRefresh = productRepository.getById(productId).getCopies();

            Copy newCopyToAdd = assignDataForCopy(productId, copyToSet);

            listOfCopiesToRefresh.add(newCopyToAdd);

            productToActualizeCopy.setCopies(listOfCopiesToRefresh);
            productRepository.save(productToActualizeCopy);
        } catch (RuntimeException noProduct) {
            throw new ProductNotFoundException(HttpStatus.NOT_FOUND,
                    "No product found with id: " + productId,
                    new RuntimeException(),
                    productId);
        }

    }

    private Copy assignDataForCopy(Integer productId, Copy copyToSet) {
        Copy newCopyToAdd = new Copy(copyToSet.getCopyId(),
                copyToSet.getMerchandisingCode(),
                copyToSet.getBuyGrossPrice(),
                copyToSet.getBuyVatPercentage());

        newCopyToAdd.setProductId(productId);
        newCopyToAdd.setSellCurrentGrossPrice(productRepository.getById(productId).getSellBaseGrossPrice());
        newCopyToAdd.setSellCurrentNetPrice(productRepository.getById(productId).getSellBaseNetPrice());
        return newCopyToAdd;
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
