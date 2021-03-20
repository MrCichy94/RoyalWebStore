package pl.cichy.RoyalWebStore.logic.implementation;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import pl.cichy.RoyalWebStore.exception.CustomerNotFoundException;
import pl.cichy.RoyalWebStore.logic.InvoiceGeneratorService;
import pl.cichy.RoyalWebStore.model.*;
import pl.cichy.RoyalWebStore.model.repository.CustomerRepository;
import pl.cichy.RoyalWebStore.model.repository.OrderRepository;
import pl.cichy.RoyalWebStore.model.repository.ProductRepository;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static pl.cichy.RoyalWebStore.model.InvoiceGenerator.*;

@Service
@RequestScope
public class InvoiceGeneratorServiceImpl implements InvoiceGeneratorService {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public InvoiceGeneratorServiceImpl(final CustomerRepository customerRepository,
                                       final ProductRepository productRepository,
                                       final OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public void createCustomersOrderPDFInvoice(int customerId, int orderId) throws FileNotFoundException {

        PdfDocument pdfDocument = new PdfDocument(new PdfWriter("MyInvoice.pdf"));
        PageSize ps = PageSize.A4;
        Document layoutDocument = new Document(pdfDocument, ps);

        try {
            Order result = customerRepository.getById(customerId).getOrders().get(orderId - 1);
            List<Copy> copies = result.getCopies();
            List<InvoiceGenerator.Article> positionsList = new ArrayList<>();
            for(Copy a : copies) {
                Product p = productRepository.getById(a.getProductId());
                Article art = new InvoiceGenerator.Article(1,
                        p.getProductName(),
                        1,
                        a.getSellCurrentGrossPrice(),
                        a.getBuyVatPercentage()
                );
                positionsList.add(art);
            }
            addTitle(layoutDocument);
            addInvoiceDetails(layoutDocument);
            addPositionsTable(layoutDocument, positionsList);
            addSummTable(layoutDocument);

            addSign(layoutDocument);
            layoutDocument.close();

        } catch (RuntimeException e) {
            throw new CustomerNotFoundException(HttpStatus.NOT_FOUND,
                    "No customer found with id: " + customerId,
                    new RuntimeException(),
                    customerId);
        }
/*
        addTitle(layoutDocument);

        addInvoiceDetails(layoutDocument);
        addPositionsTable(layoutDocument, Arrays.asList(
                new InvoiceGenerator.Article(1, "Revolotion 5", 8, new BigDecimal("169.99"), new BigDecimal("0.23")),
                new InvoiceGenerator.Article(2, "Galaxy 5", 5, new BigDecimal("179.99"), new BigDecimal("0.23"))));

        addSummTable(layoutDocument);

        addSign(layoutDocument);
        layoutDocument.close();

 */

    }
}
