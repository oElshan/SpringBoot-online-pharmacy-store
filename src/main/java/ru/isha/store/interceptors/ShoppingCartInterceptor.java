package ru.isha.store.interceptors;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import ru.isha.store.model.ShoppingCart;
import ru.isha.store.services.WebService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ShoppingCartInterceptor extends HandlerInterceptorAdapter {
    private static final Logger log = LoggerFactory.getLogger(ShoppingCartInterceptor.class);


    private final WebService webService;


    public ShoppingCartInterceptor(WebService webService) {
        this.webService = webService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        ShoppingCart shoppingCart;
        if (!webService.isCurrentShoppingCartCreated(request)) {

            Cookie cookieShoppingCart = webService.findShoppingCartCookie(request);
            if (cookieShoppingCart != null) {
              shoppingCart = webService.deserializeShoppingCart(cookieShoppingCart.getValue());
            } else {
              shoppingCart = new ShoppingCart();
            }
            webService.setCurrentShoppingCart(request, shoppingCart);
        }
        return super.preHandle(request, response, handler);
    }

}
