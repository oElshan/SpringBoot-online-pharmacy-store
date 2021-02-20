package ru.isha.store.controllers.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.isha.store.entity.Product;
import ru.isha.store.entity.SpecCategory;
import ru.isha.store.services.ProductService;
import ru.isha.store.utils.Constants;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class WelcomeController {

    private ProductService productService;

    private ServletContext servletContext;

    public WelcomeController(ProductService productService, ServletContext servletContext) {
        this.productService = productService;
        this.servletContext = servletContext;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String welcome(Model model, HttpSession session){

        List<SpecCategory> specCategories= (List<SpecCategory>) servletContext.getAttribute(Constants.SPECCATEGORY_LIST);
        List<Product> products = productService.listAllProductsForSpecCategory(specCategories.get(0).getId(),1, Constants.MAX_PRODUCTS_PER_HTML_PAGE);
        model.addAttribute("products", products);
        model.addAttribute("specCategory", specCategories.get(0));
        model.addAttribute("specCategoryList", specCategories);
        return "index";
    }

}
