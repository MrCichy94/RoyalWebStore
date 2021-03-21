package pl.cichy.RoyalWebStore.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.cichy.RoyalWebStore.model.SalesInvoice;

import java.util.List;

public interface SalesInvoiceRepository {

    List<SalesInvoice> findAll();

    Page<SalesInvoice> findAll(Pageable page);

    SalesInvoice getById(Integer id);

    SalesInvoice save(SalesInvoice entity);

    SalesInvoice getSalesInvoiceByInvoiceNumber(long invoiceNumber);
}
