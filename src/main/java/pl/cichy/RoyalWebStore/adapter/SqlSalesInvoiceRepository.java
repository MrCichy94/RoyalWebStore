package pl.cichy.RoyalWebStore.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.cichy.RoyalWebStore.model.SalesInvoice;
import pl.cichy.RoyalWebStore.model.repository.SalesInvoiceRepository;

@Repository
public interface SqlSalesInvoiceRepository extends SalesInvoiceRepository, JpaRepository<SalesInvoice, Integer> {

    @Override
    @Query(nativeQuery = true, value = "SELECT * from SALESINVOICE where SALES_INVOICE_ID=:id")
    SalesInvoice getById(@Param("id") Integer id);
}
