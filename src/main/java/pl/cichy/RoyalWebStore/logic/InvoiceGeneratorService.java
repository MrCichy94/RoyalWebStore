package pl.cichy.RoyalWebStore.logic;

import java.io.FileNotFoundException;

public interface InvoiceGeneratorService {

    void createCustomersOrderPDFInvoice(int customerId, int orderId) throws FileNotFoundException;
}
