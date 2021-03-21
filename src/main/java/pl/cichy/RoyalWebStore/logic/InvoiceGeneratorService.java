package pl.cichy.RoyalWebStore.logic;

import java.io.FileNotFoundException;

public interface InvoiceGeneratorService {

    void createCustomersOrderPDFInvoice(long invoiceNumber) throws FileNotFoundException;
}
