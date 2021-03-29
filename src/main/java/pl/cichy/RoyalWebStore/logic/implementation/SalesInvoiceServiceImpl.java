package pl.cichy.RoyalWebStore.logic.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import pl.cichy.RoyalWebStore.exception.OrderNotFoundException;
import pl.cichy.RoyalWebStore.logic.SalesInvoiceService;
import pl.cichy.RoyalWebStore.model.Copy;
import pl.cichy.RoyalWebStore.model.Customer;
import pl.cichy.RoyalWebStore.model.SalesInvoice;
import pl.cichy.RoyalWebStore.model.SalesInvoicePositions;
import pl.cichy.RoyalWebStore.model.repository.CustomerRepository;
import pl.cichy.RoyalWebStore.model.repository.OrderRepository;
import pl.cichy.RoyalWebStore.model.repository.SalesInvoicePositionsRepository;
import pl.cichy.RoyalWebStore.model.repository.SalesInvoiceRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequestScope
public class SalesInvoiceServiceImpl implements SalesInvoiceService {

    @Autowired
    private final SalesInvoicePositionsRepository salesInvoicePositionsRepository;
    private final SalesInvoiceRepository salesInvoiceRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public SalesInvoiceServiceImpl(final SalesInvoicePositionsRepository salesInvoicePositionsRepository,
                                   final SalesInvoiceRepository salesInvoiceRepository,
                                   final CustomerRepository customerRepository,
                                   final OrderRepository orderRepository) {
        this.salesInvoicePositionsRepository = salesInvoicePositionsRepository;
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
    public void createNewSalesInvoice(int orderId, SalesInvoice salesInvoiceToAdd) {

        if (!orderRepository.existsById(orderId)) {
            throw new OrderNotFoundException(HttpStatus.NOT_FOUND,
                    "There is no order with given id to process!",
                    new RuntimeException(),
                    orderId);
        } else {
            SalesInvoice newSalesInvoice = new SalesInvoice(salesInvoiceToAdd.getSalesInvoiceId(),
                    salesInvoiceToAdd.getPaymentMethod(),
                    salesInvoiceToAdd.getTypeOfDocument());

            newSalesInvoice.setOrder(orderRepository.getById(orderId));

            salesInvoiceRepository.save(newSalesInvoice);

            List<Copy> copiesInOrder = orderRepository.getById(orderId).getCopies();
            for (Copy a : copiesInOrder) {
                SalesInvoicePositions newSalesInvoicePosition = new SalesInvoicePositions(
                        newSalesInvoice,
                        a,
                        a.getSellCurrentNetPrice(),
                        a.getSellCurrentGrossPrice(),
                        a.getBuyVatPercentage()
                );
                salesInvoicePositionsRepository.save(newSalesInvoicePosition);
            }
        }
    }
}
