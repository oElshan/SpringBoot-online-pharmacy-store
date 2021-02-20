package ru.isha.store.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.isha.store.entity.Product;
import ru.isha.store.model.ShoppingCart;
import ru.isha.store.services.CookieService;
import ru.isha.store.services.ProductService;
import ru.isha.store.services.WebService;
import ru.isha.store.utils.Constants;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class WebServiceImpl  implements WebService {
    private static final Logger log = LoggerFactory.getLogger(WebServiceImpl.class);

    // TODO: 2021-02-19 реализовтаь JSONCookieService
    @Autowired
    private CookieService cookieService;

    @Autowired
    private ProductService productService;

    @Override
    public String serializeShoppingCart(ShoppingCart shoppingCart) {
        return cookieService.createShoppingCartCookie(shoppingCart.getItems());
    }

    public ShoppingCart deserializeShoppingCart(String string) {
        ShoppingCart shoppingCart = new ShoppingCart();
        String[] items = string.split("\\|");
        for (String item : items) {
            try {
                String data[] = item.split("-");
                long idProduct = Long.parseLong(data[0]);
                int count = Integer.parseInt(data[1]);
                Product product = productService.findProductById(idProduct);
                shoppingCart.addProduct(product, count);
            } catch (RuntimeException e) {
                log.error("Can't add product to ShoppingCart during deserialization: item=" + item, e);
            }
        }
//        return shoppingCart.getItems().isEmpty() ? null : shoppingCart;
        return  shoppingCart;
    }

    @Override
    public ShoppingCart getCurrentShoppingCart(HttpServletRequest req) {
        return (ShoppingCart) req.getSession().getAttribute(Constants.CURRENT_SHOPPING_CART);
    }
    @Override
    public boolean isCurrentShoppingCartCreated(HttpServletRequest req) {
        return req.getSession().getAttribute(Constants.CURRENT_SHOPPING_CART) != null;
    }
    @Override
    public void setCurrentShoppingCart(HttpServletRequest req, ShoppingCart shoppingCart) {
        req.getSession().setAttribute(Constants.CURRENT_SHOPPING_CART, shoppingCart);
    }
    @Override
    public void clearCurrentShoppingCart(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().removeAttribute(Constants.CURRENT_SHOPPING_CART);
        setCookie(Constants.Cookie.SHOPPING_CART.getName(), null, 0, resp);
    }
    @Override
    public Cookie findShoppingCartCookie(HttpServletRequest req) {
        return cookieService.findCookie(req, Constants.Cookie.SHOPPING_CART.getName());
    }


    @Override
    public  void updateCurrentShoppingCartCookie(String cookieValue, HttpServletResponse resp) {
        setCookie(Constants.Cookie.SHOPPING_CART.getName(), cookieValue,
                Constants.Cookie.SHOPPING_CART.getTtl(), resp);
    }

    @Override
    public  void setCookie(String name, String value, int age, HttpServletResponse resp) {
        Cookie c = new Cookie(name, value);
        c.setMaxAge(age);
        c.setPath("/");
        c.setHttpOnly(true);
        resp.addCookie(c);
    }

    @Override
    public String getCurrentRequestUrl(HttpServletRequest req) {
        String query = req.getQueryString();
        if (query == null) {
            return req.getRequestURI();
        } else {
            return req.getRequestURI() + "?" + query;
        }
    }






//    public static CurrentAccount getCurrentAccount(HttpServletRequest req) {
//        return (CurrentAccount) req.getSession().getAttribute(Constants.CURRENT_ACCOUNT);
//    }

//    public static void setCurrentAccount(HttpServletRequest req, CurrentAccount currentAccount) {
//        req.getSession().setAttribute(Constants.CURRENT_ACCOUNT, currentAccount);
//    }
//
//    public static boolean isCurrentAccountCreated(HttpServletRequest req) {
//        return getCurrentAccount(req) != null;
//    }
//

}
