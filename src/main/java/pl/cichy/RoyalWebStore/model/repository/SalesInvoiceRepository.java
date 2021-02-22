package pl.cichy.RoyalWebStore.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.cichy.RoyalWebStore.model.SalesInvoice;

import java.util.List;
import java.util.Optional;

public interface SalesInvoiceRepository {

    List<SalesInvoice> findAll();

    Page<SalesInvoice> findAll(Pageable page);

    Optional<SalesInvoice> findById(Integer id);

    SalesInvoice getById(Integer id);

    void deleteById(Integer id);

    boolean existsById(Integer id);

    SalesInvoice save(SalesInvoice entity);
}
