package pl.cichy.RoyalWebStore.logic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.cichy.RoyalWebStore.model.SalesInvoice;

import java.util.List;

public interface SalesInvoiceService {

    List<SalesInvoice> findAll();

    Page<SalesInvoice> findAll(Pageable page);

    SalesInvoice getById(Integer id);

    void createNewSalesInvoice(int orderId, SalesInvoice salesInvoiceToAdd);
}
