package pl.cichy.RoyalWebStore.logic.implementation;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import pl.cichy.RoyalWebStore.logic.InvoiceGeneratorService;
import pl.cichy.RoyalWebStore.model.InvoiceGenerator;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Arrays;

import static pl.cichy.RoyalWebStore.model.InvoiceGenerator.*;

@Service
@RequestScope
public class InvoiceGeneratorServiceImpl implements InvoiceGeneratorService {

    @Override
    public void createCustomersOrderPDFInvoice(int customerId, int orderId) throws FileNotFoundException {

        PdfDocument pdfDocument = new PdfDocument(new PdfWriter("MyInvoice.pdf"));
        PageSize ps = PageSize.A4;
        Document layoutDocument = new Document(pdfDocument, ps);

        addTitle(layoutDocument);

        addInvoiceDetails(layoutDocument);
        addPositionsTable(layoutDocument, Arrays.asList(
                new InvoiceGenerator.Article(1, "Revolotion 5", 8, new BigDecimal("169.99"), new BigDecimal("0.23")),
                new InvoiceGenerator.Article(2, "Galaxy 5", 5, new BigDecimal("179.99"), new BigDecimal("0.23"))));

        addSummTable(layoutDocument);

        addSign(layoutDocument);
        layoutDocument.close();

    }
}
