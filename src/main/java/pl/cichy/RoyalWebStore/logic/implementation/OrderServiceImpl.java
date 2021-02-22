package pl.cichy.RoyalWebStore.logic.implementation;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import pl.cichy.RoyalWebStore.logic.OrderService;
import pl.cichy.RoyalWebStore.model.Copy;
import pl.cichy.RoyalWebStore.model.Customer;
import pl.cichy.RoyalWebStore.model.Order;
import pl.cichy.RoyalWebStore.model.repository.CopyRepository;
import pl.cichy.RoyalWebStore.model.repository.CustomerRepository;
import pl.cichy.RoyalWebStore.model.repository.OrderRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequestScope
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final CopyRepository copyRepository;

    public OrderServiceImpl(final OrderRepository orderRepository,
                            final CustomerRepository customerRepository,
                            final CopyRepository copyRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.copyRepository = copyRepository;
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

    @Override
    public void addToOrder(int orderId, int copyId) {

        if (!orderRepository.existsById(orderId)) {
            throw new ResourceNotFoundException("No order found with id=" + orderId);
        } else {
            {
                Order orderToAddThisCopy = orderRepository.getById(orderId);
                List<Copy> copies = orderToAddThisCopy.getCopies();

                copies.add(copyRepository.getById(copyId));

                orderToAddThisCopy.setCopies(copies);
                orderRepository.save(orderToAddThisCopy);
            }
        }
    }

    private void assignDataToOrderObject(int customerId, Order customerOrderToAdd) {

        customerOrderToAdd.setOrderId(customerOrderToAdd.getOrderId());
        customerOrderToAdd.setCustomerId(customerId);
        customerOrderToAdd.setPaid(customerOrderToAdd.getPaid());

    }
}


