package ru.isha.store.services;

import ru.isha.store.dto.ClientOrderForm;
import ru.isha.store.entity.ClientOrder;
import ru.isha.store.model.ShoppingCart;

public interface ClientService {

    ClientOrder newClientOrder(ShoppingCart shoppingCart, ClientOrderForm clientOrderForm);
}
