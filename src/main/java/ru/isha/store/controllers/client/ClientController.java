package ru.isha.store.controllers.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.isha.store.dto.ClientOrderForm;
import ru.isha.store.entity.Product;
import ru.isha.store.services.ProductService;
import ru.isha.store.utils.Constants;

import javax.servlet.http.HttpSession;

@Controller
public class ClientController {

    private final ProductService productService;

    public ClientController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/checkout" ,method = RequestMethod.GET)
    public  String createOrder(  Model model) {
        model.addAttribute("orderForm", new ClientOrderForm());
        model.addAttribute("breadcrumb", "Checkout Process");
        return "checkout";
    }

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public String showCart(Model model, HttpSession session) {

        model.addAttribute("shoppingCart", session.getAttribute(Constants.CURRENT_SHOPPING_CART));
        model.addAttribute("breadcrumb", "Checkout Process");
        return "cart";
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public String showCart(@PathVariable("id") Long id , Model model, HttpSession session) {

        Product product = productService.findProductById(id);

        model.addAttribute("shoppingCart", session.getAttribute(Constants.CURRENT_SHOPPING_CART));
        model.addAttribute("breadcrumb", "Checkout Process");
        model.addAttribute("product",product);
        return "single-product";
    }

}
