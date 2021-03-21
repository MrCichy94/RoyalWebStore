package pl.cichy.RoyalWebStore.logic.implementation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.cichy.RoyalWebStore.exception.CustomerNotFoundException;
import pl.cichy.RoyalWebStore.exception.OrderNotFoundException;
import pl.cichy.RoyalWebStore.model.Customer;
import pl.cichy.RoyalWebStore.model.SalesInvoice;
import pl.cichy.RoyalWebStore.model.repository.CustomerRepository;
import pl.cichy.RoyalWebStore.model.repository.OrderRepository;
import pl.cichy.RoyalWebStore.model.repository.SalesInvoicePositionsRepository;
import pl.cichy.RoyalWebStore.model.repository.SalesInvoiceRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SalesInvoiceServiceImplTest {

    @Test
    @DisplayName("Should throw CustomerNotFoundException when we dont got customer with given customer's ID")
    void createNewSalesInvoice_ThrowCustomerNotFoundException() {
        //given
        var mockCustomer = mock(Customer.class);
        //and
        var mockSalesInvoice = mock(SalesInvoice.class);
        //and
        var mockCustomerRepository = mock(CustomerRepository.class);
        when(mockCustomerRepository.existsById(mockCustomer.getCustomerId())).thenReturn(false);
        //and
        var mockSalesInvoicePositionsRepository = mock(SalesInvoicePositionsRepository.class);
        //and
        var mockSalesInvoiceRepository = mock(SalesInvoiceRepository.class);
        //and
        var mockOrderRepository = mock(OrderRepository.class);
        //system under test
        var toTest = new SalesInvoiceServiceImpl(mockSalesInvoicePositionsRepository,
                mockSalesInvoiceRepository, mockCustomerRepository, mockOrderRepository);
        //when
        var exception = catchThrowable(() -> toTest.createNewSalesInvoice(
                mockCustomer.getCustomerId(), mockSalesInvoice));
        //then
        assertThat(exception).isInstanceOf(CustomerNotFoundException.class)
                .hasMessageContaining("No customer found");
    }

    @Test
    @DisplayName("Should throw OrderNotFoundException when we got customer with no order")
    void createNewSalesInvoice_ThrowOrderNotFoundException() {
        //given
        var mockCustomer = mock(Customer.class);
        //and
        var mockSalesInvoice = mock(SalesInvoice.class);
        //and
        var mockCustomerRepository = mock(CustomerRepository.class);
        when(mockCustomerRepository.existsById(mockCustomer.getCustomerId())).thenReturn(true);
        //and
        var mockSalesInvoicePositionsRepository = mock(SalesInvoicePositionsRepository.class);
        //and
        var mockSalesInvoiceRepository = mock(SalesInvoiceRepository.class);
        //and
        var mockOrderRepository = mock(OrderRepository.class);
        //system under test
        var toTest = new SalesInvoiceServiceImpl(mockSalesInvoicePositionsRepository,
                mockSalesInvoiceRepository, mockCustomerRepository, mockOrderRepository);
        //when
        var exception = catchThrowable(() -> toTest.createNewSalesInvoice(
                mockCustomer.getCustomerId(), mockSalesInvoice));
        //then
        assertThat(exception).isInstanceOf(OrderNotFoundException.class)
                .hasMessageContaining("There is no customer");
    }
}
