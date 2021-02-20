package ru.isha.store.services;

import ru.isha.store.dto.OrderForm;
import ru.isha.store.entity.ClientOrder;
import ru.isha.store.model.ShoppingCart;

public interface ClientService {

    ClientOrder newClientOrder(ShoppingCart shoppingCart, OrderForm orderForm);
}
