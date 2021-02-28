package ru.isha.store.services;

import org.springframework.data.domain.Page;
import ru.isha.store.dto.CartItemDTO;
import ru.isha.store.dto.ClientOrderForm;
import ru.isha.store.dto.EditOrderDTO;
import ru.isha.store.entity.ClientOrder;
import ru.isha.store.entity.Status;
import ru.isha.store.model.ShoppingCart;

import java.util.List;

public interface OrderService {
    List<ClientOrder> getAllNewOrders();

    ClientOrder newClientOrder(ShoppingCart shoppingCart, ClientOrderForm clientOrderForm);

    ClientOrder newClientOrder(CartItemDTO cartItemDTO);

    List<ClientOrder> getOrdersByStatus(String status);

    long getCountNewOrders(String status);

    List<ClientOrder> getAll();

    List<ClientOrder> getTodayOrder();

    Page<ClientOrder> getOrdersLimit(int page, int limit, String status);

    Page<ClientOrder> getOrdersLimit(int page, int limit);

    List<ClientOrder> findOrderByName(String name);

    ClientOrder findOrderByPhone(String phone);

    List<Status> getAllStatusOrders();

    ClientOrder getClientOrderById(long id);

    ClientOrder updateClientOrderItem(long orderId, long productId, int count);

    ClientOrder updateClientOrder(EditOrderDTO editOrderDTO);

    void deleteOrder(Long id);

    void deleteOrder(ClientOrder clientOrder);

    void deleteItemFromClientOrder(long productId, long orderId);




}
