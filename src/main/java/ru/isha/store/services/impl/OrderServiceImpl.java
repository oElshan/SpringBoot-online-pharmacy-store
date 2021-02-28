package ru.isha.store.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.isha.store.dto.CartItemDTO;
import ru.isha.store.dto.ClientOrderForm;
import ru.isha.store.dto.EditOrder;
import ru.isha.store.entity.Client;
import ru.isha.store.entity.ClientOrder;
import ru.isha.store.entity.OrderItem;
import ru.isha.store.entity.Status;
import ru.isha.store.model.ShoppingCart;
import ru.isha.store.model.ShoppingCartItem;
import ru.isha.store.repository.*;
import ru.isha.store.services.OrderService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepo orderRepo;
    private final StatusRepo statusRepo;
    private final OrderItemRepo orderItemRepo;
    private final ClientRepo clientRepo;
    private final ProductRepo productRepo;

    public OrderServiceImpl(OrderRepo orderRepo, StatusRepo statusRepo,
                            OrderItemRepo orderItemRepo, ClientRepo clientRepo,ProductRepo productRepo) {
        this.orderRepo = orderRepo;
        this.statusRepo = statusRepo;
        this.orderItemRepo = orderItemRepo;
        this.clientRepo = clientRepo;
        this.productRepo = productRepo;

    }

    @Override
    public List<ClientOrder>  getAllNewOrders() {
       return orderRepo.findAllByStatusName("new");
    }

    @Override
    public ClientOrder newClientOrder(CartItemDTO cartItemDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        cartItemDTO.getProducts().entrySet().stream().peek(
                longIntegerEntry -> shoppingCart.
                        addProduct(productRepo.findProductById(longIntegerEntry.getKey()),
                                longIntegerEntry.getValue())).collect(Collectors.toList());

        ClientOrderForm clientOrderForm = new ClientOrderForm();
        clientOrderForm.setEmail(cartItemDTO.getEmail());
        clientOrderForm.setFirstName(cartItemDTO.getFirstName());
        clientOrderForm.setLastName(cartItemDTO.getLastName());
        clientOrderForm.setPhone(cartItemDTO.getPhone());
        clientOrderForm.setStreetAddress(cartItemDTO.getStreetAddress());
        clientOrderForm.setTown(cartItemDTO.getTown());
        clientOrderForm.setZipCode(cartItemDTO.getZipCode());

        return newClientOrder(shoppingCart, clientOrderForm);
    }

    @Transactional
    public ClientOrder newClientOrder(ShoppingCart shoppingCart, ClientOrderForm clientOrderForm) {

        Collection<ShoppingCartItem> items = shoppingCart.getItems();
        ClientOrder clientOrder = new ClientOrder();
        List<OrderItem> orderItems = new ArrayList<>();
        clientOrder.setOrderItems(orderItems);
        clientOrder.setCreated(Timestamp.valueOf(LocalDateTime.now()));

        for (ShoppingCartItem item : items) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(item.getProduct());
            orderItem.setCount(item.getCount());
            orderItem.setClientOrder(clientOrder);
            clientOrder.getOrderItems().add(orderItem);
        }

        List<ClientOrder> clientOrders = new ArrayList<>();
        Client client = clientRepo.findByPhone(clientOrderForm.getPhone());

        if (client == null) {
            client = new Client();
            client.setFirstName(clientOrderForm.getFirstName());
            client.setLastName(clientOrderForm.getLastName());
            client.setStreetAddress(clientOrderForm.getStreetAddress());
            client.setTown(clientOrderForm.getTown());
            client.setEmail(clientOrderForm.getEmail());
            client.setPhone(clientOrderForm.getPhone());
        }
        clientOrder.setClient(client);
        Status status = statusRepo.getById(1);
        clientOrder.setStatus(status);
        clientOrders.add(orderRepo.save(clientOrder));
        client.setClientOrders(clientOrders);

        clientRepo.save(client);
        return clientOrder;
    }


    @Override
    public List<ClientOrder> getOrdersByStatus(String status) {
        return orderRepo.findAllByStatusName(status);
    }

    @Override
    public long getCountNewOrders(String status) {
        return orderRepo.countAllByStatusName("new");
    }

    @Override
    public List<ClientOrder> getTodayOrder() {
        return orderRepo.findAllClientOrderToday();
    }

    @Override
    public List<ClientOrder> findOrderByName(String name) {

        List<ClientOrder> clientOrders = orderRepo.searchOrderByNameLike(name);
        return clientOrders;
    }

    @Override
    public List<ClientOrder> getAll() {
        return orderRepo.findAll();
    }

    @Transactional
    @Override
    public ClientOrder updateClientOrder(EditOrder editOrder) {

        ClientOrder clientOrder = orderRepo.findById(editOrder.getId().longValue());
        Client client = clientOrder.getClient();
        client.setFirstName(editOrder.getClientFirstName());
        client.setLastName(editOrder.getClientLastName());
        client.setEmail(editOrder.getClientEmail());
        client.setTown(editOrder.getClientStreetTown());
        client.setStreetAddress(editOrder.getClientStreetAddress());
        client.setPhone(editOrder.getClientPhone());
        Status status = statusRepo.findByName(editOrder.getOrderStatus());
        clientOrder.setClient(client);
        clientOrder.setStatus(status);
        orderRepo.save(clientOrder);

        return orderRepo.save(clientOrder);
    }

    @Override
    public Page<ClientOrder> getOrdersLimit(int page, int limit) {
       return   orderRepo.findAll(PageRequest.of(page-1,limit, Sort.Direction.DESC,"created"));
    }

    @Override
    public Page<ClientOrder> getOrdersLimit(int page, int limit, String status) {
        return orderRepo.findAllByStatusName(status, PageRequest.of(page - 1, limit, Sort.Direction.DESC, "created"));
    }

    @Override
    public ClientOrder findOrderByPhone(String phone) {
        return orderRepo.findAllByClientPhone(phone);
    }

    @Override
    public List<Status>  getAllStatusOrders() {
        return statusRepo.findAll();
    }

    @Override
    public ClientOrder getClientOrderById(long id) {
        return orderRepo.findById(id);
    }

    @Transactional
    @Override
    public ClientOrder updateClientOrderItem(long orderId, long productId, int count) {
        ClientOrder clientOrder = orderRepo.findById(orderId);
        List<OrderItem> orderItems = clientOrder.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            int index = orderItems.indexOf(orderItem);
            if (orderItem.getId() == productId) {
                orderItem.setCount(count);
                orderItems.set(index, orderItem);
            }
        }
        clientOrder.setOrderItems(orderItems);
        return orderRepo.save(clientOrder);
    }

    @Transactional
    @Override
    public void deleteOrder(Long id) {
        logger.info("...........................................deleting client order N"+id);
        orderRepo.deleteById(id);
    }

    @Override
    public void deleteOrder(ClientOrder clientOrder) {
        orderRepo.delete(clientOrder);
    }

    @Transactional
    @Override
    public void deleteItemFromClientOrder( long productId, long orderId) {
        orderItemRepo.deleteByIdAndClientOrder_Id(productId, orderId);
    }

}
