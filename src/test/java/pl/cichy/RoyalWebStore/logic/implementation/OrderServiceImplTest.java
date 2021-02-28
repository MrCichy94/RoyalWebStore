package pl.cichy.RoyalWebStore.logic.implementation;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.cichy.RoyalWebStore.model.Customer;
import pl.cichy.RoyalWebStore.model.Order;
import pl.cichy.RoyalWebStore.model.repository.CustomerRepository;
import pl.cichy.RoyalWebStore.model.repository.OrderRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderServiceImplTest {

    @Test
    @DisplayName("Should throw ResourceNotFoundException when we dont got customer with given customer's ID")
    void setOrderForCustomer_ThrowResourceNotFoundException() {
        //given
        var mockCustomerRepository = mock(CustomerRepository.class);
        when(mockCustomerRepository.existsById(1)).thenReturn(false);
        //and
        var mockCustomer = mock(Customer.class);
        var mockOrder = mock(Order.class);
        //and
        var mockOrderRepository = mock(OrderRepository.class);
        //system under test
        var toTest = new OrderServiceImpl(mockOrderRepository, mockCustomerRepository, null);
        //when
        var exception = catchThrowable(() -> toTest.setOrderForCustomer(mockCustomer.getCustomerId(), mockOrder));
        //then
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("No customer found");
    }

}
