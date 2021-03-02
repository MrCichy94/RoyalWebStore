package pl.cichy.RoyalWebStore.logic.implementation;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import pl.cichy.RoyalWebStore.exception.CustomerNotFoundException;
import pl.cichy.RoyalWebStore.exception.OrderNotFoundException;
import pl.cichy.RoyalWebStore.logic.SalesInvoiceService;
import pl.cichy.RoyalWebStore.model.SalesInvoice;
import pl.cichy.RoyalWebStore.model.repository.CustomerRepository;
import pl.cichy.RoyalWebStore.model.repository.OrderRepository;
import pl.cichy.RoyalWebStore.model.repository.SalesInvoiceRepository;

import java.util.List;

@Service
@RequestScope
public class SalesInvoiceServiceImpl implements SalesInvoiceService {

    @Autowired
    private final SalesInvoiceRepository salesInvoiceRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public SalesInvoiceServiceImpl(final SalesInvoiceRepository salesInvoiceRepository,
                                   final CustomerRepository customerRepository,
                                   final OrderRepository orderRepository) {
        this.salesInvoiceRepository = salesInvoiceRepository;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
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
        if (!customerRepository.existsById(customerId)) {
            throw new CustomerNotFoundException(HttpStatus.NOT_FOUND,
                    "No customer found with id: " + customerId,
                    new RuntimeException(),
                    customerId);
        } else if (orderRepository.getOrdersByClientId(customerId).isEmpty()) {
                throw new OrderNotFoundException(HttpStatus.NOT_FOUND,
                        "There is no customer's order to process!",
                        new RuntimeException(),
                        customerId);
        } else {
            SalesInvoice newSalesInvoice = new SalesInvoice(salesInvoiceToAdd.getSalesInvoiceId(),
                    salesInvoiceToAdd.getPaymentMethod(),
                    salesInvoiceToAdd.getTypeOfDocument());

            newSalesInvoice.setCustomer(customerRepository.getById(customerId));
            salesInvoiceRepository.save(newSalesInvoice);
        }
    }
}
