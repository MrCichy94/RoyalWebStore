package pl.cichy.RoyalWebStore.logic.implementation;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import pl.cichy.RoyalWebStore.logic.SalesInvoiceService;
import pl.cichy.RoyalWebStore.model.SalesInvoice;
import pl.cichy.RoyalWebStore.model.repository.CustomerRepository;
import pl.cichy.RoyalWebStore.model.repository.SalesInvoiceRepository;

import java.util.List;

@Service
@RequestScope
public class SalesInvoiceServiceImpl implements SalesInvoiceService {

    @Autowired
    private final SalesInvoiceRepository salesInvoiceRepository;
    private final CustomerRepository customerRepository;

    public SalesInvoiceServiceImpl(final SalesInvoiceRepository salesInvoiceRepository,
                                   final CustomerRepository customerRepository) {
        this.salesInvoiceRepository = salesInvoiceRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<SalesInvoice> findAll() {
        return salesInvoiceRepository.findAll();
    }

    @Override
    public Page<SalesInvoice> findAll(Pageable page) {
        return salesInvoiceRepository.findAll(page);
    }

    @Override
    public SalesInvoice getById(Integer id) {
        return salesInvoiceRepository.getById(id);
    }

    @Override
    public void createNewSalesInvoice(int customerId, SalesInvoice salesInvoiceToAdd) {
        if (customerRepository.getById(customerId).getOrders().isEmpty()) {
            throw new ResourceNotFoundException("Not found customer with this id!" + customerId);
        } else {
            SalesInvoice newSalesInvoice = new SalesInvoice(salesInvoiceToAdd.getSalesInvoiceId(),
                    salesInvoiceToAdd.getPaymentMethod(),
                    salesInvoiceToAdd.getTypeOfDocument());

            newSalesInvoice.setCustomer(customerRepository.getById(customerId));
            salesInvoiceRepository.save(newSalesInvoice);
        }
    }
}
