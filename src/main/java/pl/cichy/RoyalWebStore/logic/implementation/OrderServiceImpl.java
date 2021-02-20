package pl.cichy.RoyalWebStore.logic.implementation;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import pl.cichy.RoyalWebStore.logic.OrderService;
import pl.cichy.RoyalWebStore.model.Customer;
import pl.cichy.RoyalWebStore.model.Order;
import pl.cichy.RoyalWebStore.model.repository.CustomerRepository;
import pl.cichy.RoyalWebStore.model.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequestScope
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    public OrderServiceImpl(final OrderRepository orderRepository,
                            final CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }


    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Page<Order> findAll(Pageable page) {
        return orderRepository.findAll(page);
    }

    @Override
    public Optional<Order> findById(Integer id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> getOrdersByClientId(Integer id) {
        return orderRepository.getOrdersByClientId(id);
    }

    @Override
    public void deleteById(Integer id) {
        orderRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return orderRepository.existsById(id);
    }

    @Override
    public Order save(Order entity) {
        return orderRepository.save(entity);
    }

    @Override
    public void setOrderForCustomer(int customerId, Order customerOrderToAdd) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException("No customer found with id=" + customerId);
        } else {
            {
                Customer customerToActualizeOrder = customerRepository.getById(customerId);
                List<Order> listOfOrdersToRefresh = customerRepository.getById(customerId).getOrders();

                assignDataToOrderObject(customerId, customerOrderToAdd);

                listOfOrdersToRefresh.add(customerOrderToAdd);

                customerToActualizeOrder.setOrders(listOfOrdersToRefresh);
                customerRepository.save(customerToActualizeOrder);
            }
        }
    }

    private void assignDataToOrderObject(int customerId, Order customerOrderToAdd) {

        customerOrderToAdd.setOrderId(customerOrderToAdd.getOrderId());
        customerOrderToAdd.setCustomerId(customerId);
        customerOrderToAdd.setPaid(customerOrderToAdd.getPaid());

    }
}


