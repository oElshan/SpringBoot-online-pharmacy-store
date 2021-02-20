package ru.isha.store.controllers.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.isha.store.entity.Product;
import ru.isha.store.entity.SpecCategory;
import ru.isha.store.services.ProductService;
import ru.isha.store.utils.Constants;

import javax.servlet.ServletContext;
import java.util.List;

@Controller

@RequestMapping(value = "/ajax/products")
public class AjaxProductController {

    private ProductService productService;

    private ServletContext servletContext;

    public AjaxProductController(ProductService productService, ServletContext servletContext) {
        this.productService = productService;
        this.servletContext = servletContext;
    }

    @GetMapping("/search")
    public ModelAndView searchProductByNameLike(@RequestParam("name") String name, ModelMap modelMap) {
        List<Product> products = productService.findByNameContaining(name);
        modelMap.addAttribute("products", products);
        return new ModelAndView("fragment/data-table-items :: data-table-items", modelMap);
    }


    @GetMapping
    public String getProductsListForSpecCategory(@RequestParam("idCatalog") int idCatalog, Model model) {
        List<SpecCategory> specCategories = (List<SpecCategory>) servletContext.getAttribute(Constants.SPECCATEGORY_LIST);
        model.addAttribute("specCategory", specCategories.get(idCatalog-1));
        model.addAttribute("products", productService.listAllProductsForSpecCategory(idCatalog, 1, Constants.MAX_PRODUCTS_PER_HTML_PAGE));
        return "fragment/products-list-home :: products";
    }


}
