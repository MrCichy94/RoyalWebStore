package pl.cichy.RoyalWebStore.logic.implementation;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import pl.cichy.RoyalWebStore.logic.InvoiceGeneratorService;
import pl.cichy.RoyalWebStore.model.Copy;
import pl.cichy.RoyalWebStore.model.Product;
import pl.cichy.RoyalWebStore.model.SalesInvoice;
import pl.cichy.RoyalWebStore.model.repository.CopyRepository;
import pl.cichy.RoyalWebStore.model.repository.ProductRepository;
import pl.cichy.RoyalWebStore.model.repository.SalesInvoicePositionsRepository;
import pl.cichy.RoyalWebStore.model.repository.SalesInvoiceRepository;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static pl.cichy.RoyalWebStore.model.InvoiceGenerator.*;

@Service
@RequestScope
public class InvoiceGeneratorServiceImpl implements InvoiceGeneratorService {

    private final CopyRepository copyRepository;
    private final ProductRepository productRepository;
    private final SalesInvoiceRepository salesInvoiceRepository;
    private final SalesInvoicePositionsRepository salesInvoicePositionsRepository;


    public InvoiceGeneratorServiceImpl(final CopyRepository copyRepository,
                                       final ProductRepository productRepository,
                                       final SalesInvoiceRepository salesInvoiceRepository,
                                       final SalesInvoicePositionsRepository salesInvoicePositionsRepository) {
        this.copyRepository = copyRepository;
        this.productRepository = productRepository;
        this.salesInvoiceRepository = salesInvoiceRepository;
        this.salesInvoicePositionsRepository = salesInvoicePositionsRepository;
    }

    @Override
    public void createCustomersOrderPDFInvoice(long invoiceNumber) throws FileNotFoundException {

        PdfDocument pdfDocument = new PdfDocument(new PdfWriter("MyInvoice.pdf"));
        PageSize ps = PageSize.A4;
        Document layoutDocument = new Document(pdfDocument, ps);

        int salesInvoiceNumber = getSalesInvoiceNumber(invoiceNumber);

        List<Integer> invoiceCopiesNumbers = getCopiesIdIntegersFromSalesInvoice(salesInvoiceNumber);

        List<Copy> positionsOnInvoice = findAndAddCopiesByCopiesId(invoiceCopiesNumbers);

        List<Article> positionsList = createArticleRowsForTable(positionsOnInvoice);

        addTitle(layoutDocument);
        addInvoiceDetails(layoutDocument);
        addPositionsTable(layoutDocument, positionsList);
        addSummTable(layoutDocument);

        addSign(layoutDocument);
        layoutDocument.close();

    }

    private int getSalesInvoiceNumber(long invoiceNumber) {
        SalesInvoice result = salesInvoiceRepository.getSalesInvoiceByInvoiceNumber(invoiceNumber);
        return result.getSalesInvoiceId();
    }

    private List<Integer> getCopiesIdIntegersFromSalesInvoice(int salesInvoiceNumber) {
        return salesInvoicePositionsRepository
                .getCopiesIdOfSalesInvoiceWithGivenId(salesInvoiceNumber);
    }

    private List<Copy> findAndAddCopiesByCopiesId(List<Integer> invoiceCopiesNumbers) {
        List<Copy> positionsOnInvoice = new ArrayList<>();
        for (int x : invoiceCopiesNumbers) {
            positionsOnInvoice.add(copyRepository.getById(x));
        }
        return positionsOnInvoice;
    }

    private List<Article> createArticleRowsForTable(List<Copy> positionsOnInvoice) {
        List<Article> positionsList = new ArrayList<>();
        int lineInTableNumber = 1;
        for (Copy a : positionsOnInvoice) {
            Product p = productRepository.getById(a.getProductId());
            Article art = new Article(lineInTableNumber,
                    p.getProductName(),
                    1,
                    a.getSellCurrentGrossPrice(),
                    a.getBuyVatPercentage()
            );
            positionsList.add(art);
            lineInTableNumber++;
        }
        return positionsList;
    }
}
