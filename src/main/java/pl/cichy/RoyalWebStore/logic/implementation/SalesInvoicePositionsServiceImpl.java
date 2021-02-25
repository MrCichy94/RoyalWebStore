package pl.cichy.RoyalWebStore.logic.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import pl.cichy.RoyalWebStore.logic.SalesInvoicePositionsService;
import pl.cichy.RoyalWebStore.model.SalesInvoicePositions;
import pl.cichy.RoyalWebStore.model.repository.SalesInvoicePositionsRepository;

import java.util.List;

@Service
@RequestScope
public class SalesInvoicePositionsServiceImpl implements SalesInvoicePositionsService {

    @Autowired
    private final SalesInvoicePositionsRepository salesInvoicePositionsRepository;

    public SalesInvoicePositionsServiceImpl(final SalesInvoicePositionsRepository salesInvoicePositionsRepository) {
        this.salesInvoicePositionsRepository = salesInvoicePositionsRepository;
    }

    @Override
    public List<SalesInvoicePositions> findAll() {
        return salesInvoicePositionsRepository.findAll();
    }

    @Override
    public Page<SalesInvoicePositions> findAll(Pageable page) {
        return salesInvoicePositionsRepository.findAll(page);
    }

    @Override
    public SalesInvoicePositions getById(Integer id) {
        return salesInvoicePositionsRepository.getById(id);
    }

}
