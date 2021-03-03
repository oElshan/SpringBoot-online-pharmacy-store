package ru.isha.store.services.impl;

import org.springframework.stereotype.Service;
import ru.isha.store.model.ShoppingCartItem;
import ru.isha.store.services.CookieService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

@Service
public class CookieServiceImpl implements CookieService {


    // TODO: 2021-02-19 Переделать на stream
    @Override
    public String createShoppingCartCookie(Collection<ShoppingCartItem> items) {

        StringBuilder res = new StringBuilder();
        for (ShoppingCartItem item : items) {
            res.append(item.getProduct().getId()).append("-").append(item.getCount()).append("|");
        }
        if (res.length() > 0) {
            res.deleteCharAt(res.length() - 1);
        }

        return res.toString();
    }


    @Override
    public Cookie findCookie(HttpServletRequest req, String cookieName) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(cookieName)) {
                    if (c.getValue() != null && !"".equals(c.getValue())) {
                        return c;
                    }
                }
            }
        }
        return null;
    }


    @Override
    public List<ShoppingCartItem> parseShoppingCartCookie(String cookieValue) {
        return null;
    }
}
