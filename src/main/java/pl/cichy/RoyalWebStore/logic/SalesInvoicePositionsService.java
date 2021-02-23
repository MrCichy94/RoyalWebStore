package pl.cichy.RoyalWebStore.logic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import pl.cichy.RoyalWebStore.model.SalesInvoice;
import pl.cichy.RoyalWebStore.model.SalesInvoicePositions;

import java.util.List;
import java.util.Optional;

public interface SalesInvoicePositionsService {

    List<SalesInvoicePositions> findAll();

    Page<SalesInvoicePositions> findAll(Pageable page);

    Optional<SalesInvoicePositions> findById(Integer id);

    SalesInvoicePositions getById(Integer id);

    void deleteById(Integer id);

    boolean existsById(Integer id);

    SalesInvoicePositions save(SalesInvoicePositions entity);

}
