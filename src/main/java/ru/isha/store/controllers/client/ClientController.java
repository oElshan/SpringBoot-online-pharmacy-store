package ru.isha.store.controllers.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.isha.store.dto.ClientOrderForm;

@Controller
public class ClientController {

    @RequestMapping(value = "/checkout" ,method = RequestMethod.GET)
    public  String createOrder(  Model model) {
        model.addAttribute("orderForm", new ClientOrderForm());
        model.addAttribute("breadcrumb", "Checkout Process");
        return "checkout";
    }

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public String showCart(Model model) {
        model.addAttribute("breadcrumb", "Checkout Process");
//        теперь в cart можно типлифом найти  перменную x1 и распечатать в нудный тег
        return "cart";
    }


}
