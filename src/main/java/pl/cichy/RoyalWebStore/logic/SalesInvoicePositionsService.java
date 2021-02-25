package pl.cichy.RoyalWebStore.logic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.cichy.RoyalWebStore.model.SalesInvoicePositions;

import java.util.List;

public interface SalesInvoicePositionsService {

    List<SalesInvoicePositions> findAll();

    Page<SalesInvoicePositions> findAll(Pageable page);

    SalesInvoicePositions getById(Integer id);

}
