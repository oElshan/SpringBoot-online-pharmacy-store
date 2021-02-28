package ru.isha.store.controllers.client;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.isha.store.dto.ClientOrderForm;
import ru.isha.store.entity.ClientOrder;
import ru.isha.store.model.ShoppingCart;
import ru.isha.store.services.OrderService;
import ru.isha.store.utils.Constants;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;

@Controller
@RequestMapping(value = "/ajax/orders")
public class AjaxOrderController {

    private final OrderService orderService;


    public AjaxOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST, produces = "application/json")
    public String newClientOrder(@Valid @RequestBody ClientOrderForm clientOrderForm, BindingResult bindingResult, Model model, HttpSession session) {

        if (bindingResult.hasErrors()) {
            return "fragment/checkout-page :: checkout";
        }
        System.out.println(clientOrderForm);
        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute(Constants.CURRENT_SHOPPING_CART);
        ClientOrder clientOrder = orderService.newClientOrder(shoppingCart, clientOrderForm);
        model.addAttribute("totalCost", shoppingCart.getTotalCost().doubleValue());

        shoppingCart.getItems().clear();
        shoppingCart.setTotalCost(BigDecimal.ZERO);
        shoppingCart.setTotalCount(0);
        session.setAttribute(Constants.CURRENT_SHOPPING_CART,shoppingCart);
        model.addAttribute("clientOrder", clientOrder);
        System.out.println(clientOrder);

        return "fragment/order-complete :: order-complete";
    }

}
