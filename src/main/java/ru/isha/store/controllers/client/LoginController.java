package ru.isha.store.controllers.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }


    // Login form with error
    @RequestMapping("/sign-in-failed")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

}
