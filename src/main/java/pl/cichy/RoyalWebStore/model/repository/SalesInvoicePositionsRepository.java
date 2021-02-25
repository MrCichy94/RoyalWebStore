package pl.cichy.RoyalWebStore.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.cichy.RoyalWebStore.model.SalesInvoicePositions;

import java.util.List;
import java.util.Optional;

public interface SalesInvoicePositionsRepository {

    List<SalesInvoicePositions> findAll();

    Page<SalesInvoicePositions> findAll(Pageable page);

    Optional<SalesInvoicePositions> findById(Integer id);

    SalesInvoicePositions getById(Integer id);

}
