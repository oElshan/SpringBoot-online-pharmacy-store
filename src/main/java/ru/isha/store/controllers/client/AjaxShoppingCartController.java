package ru.isha.store.controllers.client;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ru.isha.store.entity.Product;
import ru.isha.store.model.ShoppingCart;
import ru.isha.store.services.ProductService;
import ru.isha.store.services.WebService;
import ru.isha.store.utils.Constants;
import ru.isha.store.utils.Views;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class AjaxShoppingCartController {

    private ProductService productService;

    private WebService webService;

    public AjaxShoppingCartController(ProductService productService, WebService webService) {
        this.productService = productService;
        this.webService = webService;
    }

    @JsonView(Views.Public.class)
    @GetMapping(value = "/ajax/json/shopping-cart",produces ="application/json" )
    @ResponseBody
    public ShoppingCart getShoppingCartJSON(HttpSession session) {
       return (ShoppingCart) session.getAttribute(Constants.CURRENT_SHOPPING_CART);
    }

    @GetMapping("/ajax/shopping-cart")
    public ModelAndView getShoppingCartView(HttpSession session, ModelMap model) {

         session.setAttribute("shoppingCart",Constants.CURRENT_SHOPPING_CART);
        return new ModelAndView("fragment/view-shopping-cart :: viewSoppingCart", model);
//        return "fragment/view-shopping-cart :: viewSoppingCart";

    }

    @DeleteMapping("/ajax/shopping-cart")
    public String deleteItemFromShoppingCart(@RequestParam("idProduct") long idProduct, HttpSession session, HttpServletResponse response) {
       ShoppingCart shoppingCart =(ShoppingCart) session.getAttribute(Constants.CURRENT_SHOPPING_CART);
       shoppingCart.removeProduct(idProduct,1);
        webService.updateCurrentShoppingCartCookie(webService.serializeShoppingCart(shoppingCart),response);
       session.setAttribute(Constants.CURRENT_SHOPPING_CART,shoppingCart);
        return "fragment/shopping-cart :: shopping-cart";
    }

    @GetMapping("/ajax/shopping-cart/add")
    public String addShoppingCart(@RequestParam("idProduct") long idProduct, HttpSession session, HttpServletResponse response) {

        Product product =  productService.findProductById(idProduct);
        ShoppingCart shoppingCart =(ShoppingCart) session.getAttribute(Constants.CURRENT_SHOPPING_CART);
        shoppingCart.addProduct(product,1);
        webService.updateCurrentShoppingCartCookie(webService.serializeShoppingCart(shoppingCart),response);
        session.setAttribute(Constants.CURRENT_SHOPPING_CART, shoppingCart);
        return "fragment/shopping-cart :: shopping-cart";
    }
}
