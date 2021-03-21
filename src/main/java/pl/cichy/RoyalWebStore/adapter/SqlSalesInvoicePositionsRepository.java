package pl.cichy.RoyalWebStore.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.cichy.RoyalWebStore.model.SalesInvoicePositions;
import pl.cichy.RoyalWebStore.model.repository.SalesInvoicePositionsRepository;

import java.util.List;

@Repository
public interface SqlSalesInvoicePositionsRepository extends SalesInvoicePositionsRepository,
        JpaRepository<SalesInvoicePositions, Integer> {

    @Override
    @Query(nativeQuery = true, value = "SELECT * from SALESINVOICEPOSITIONS where SALES_INVOICE_POSITIONS_ID=:id")
    SalesInvoicePositions getById(@Param("id") Integer id);

    @Override
    @Query(nativeQuery = true, value = "SELECT * from SALESINVOICEPOSITIONS where SALES_INVOICE_SALES_INVOICE_ID=:id")
    List<Integer> getCopiesIdOfSalesInvoiceWithGivenId(@Param("id") Integer id);


}
