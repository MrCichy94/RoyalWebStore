package pl.cichy.RoyalWebStore.logic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.cichy.RoyalWebStore.model.SalesInvoice;

import java.util.List;
import java.util.Optional;

public interface SalesInvoiceService {

    List<SalesInvoice> findAll();

    Page<SalesInvoice> findAll(Pageable page);

    Optional<SalesInvoice> findById(Integer id);

    SalesInvoice getById(Integer id);

    void deleteById(Integer id);

    boolean existsById(Integer id);

    SalesInvoice save(SalesInvoice entity);

    void createNewSalesInvoice(int customerId, SalesInvoice salesInvoiceToAdd);
}
