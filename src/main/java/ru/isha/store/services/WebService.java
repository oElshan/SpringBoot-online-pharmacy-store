package ru.isha.store.services;

import ru.isha.store.model.ShoppingCart;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface WebService {

    String getCurrentRequestUrl(HttpServletRequest req) ;

    void setCookie(String name, String value, int age, HttpServletResponse resp);

    String serializeShoppingCart(ShoppingCart shoppingCart);

    ShoppingCart getCurrentShoppingCart(HttpServletRequest req);

    boolean isCurrentShoppingCartCreated(HttpServletRequest req);

    public void setCurrentShoppingCart(HttpServletRequest req, ShoppingCart shoppingCart);

    public void clearCurrentShoppingCart(HttpServletRequest req, HttpServletResponse resp);

    public Cookie findShoppingCartCookie(HttpServletRequest req);

    public void updateCurrentShoppingCartCookie(String cookieValue, HttpServletResponse resp);

    public ShoppingCart deserializeShoppingCart(String string);


//    public ShoppingCart deserializeShoppingCart(String string);


    }

