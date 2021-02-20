package ru.isha.store.services;

import ru.isha.store.model.ShoppingCartItem;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

public interface CookieService {

    String createShoppingCartCookie(Collection<ShoppingCartItem> items);

    public Cookie findCookie(HttpServletRequest req, String cookieName);

    List<ShoppingCartItem> parseShoppingCartCookie(String cookieValue);

}
