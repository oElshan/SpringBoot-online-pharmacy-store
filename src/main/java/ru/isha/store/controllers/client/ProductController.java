package ru.isha.store.controllers.client;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.isha.store.controllers.AbstractProductController;
import ru.isha.store.dto.FilterProduct;
import ru.isha.store.entity.Product;
import ru.isha.store.services.ProductService;
import ru.isha.store.utils.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/products")
public class ProductController extends AbstractProductController {

    private final ProductService productService;

    public  ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/search",method = RequestMethod.POST,produces ="application/json" )
    public  String showProductByFilter(@RequestBody FilterProduct filterProduct, Model model,
                                       HttpServletRequest request,
                                       HttpSession session) {

        session.setAttribute("filterProduct", filterProduct);
        Page<Product> productsPage = productService.getProductByFilter(filterProduct, PageRequest.of(0, Constants.MAX_PRODUCTS_PER_HTML_PAGE));
        model.addAttribute("urlPagination", request.getRequestURI() + '?');
        model.addAttribute("filterPrice", filterProduct.getPrice());
        model.addAttribute("filterProducersIdSet", filterProduct.getProducers());
        model.addAttribute("isFilterPrice", true);
        if (filterProduct.getSearch() != null) {
            model.addAttribute("search", filterProduct.getSearch());
            model.addAttribute("breadcrumb", filterProduct.getSearch());
            model.addAttribute("minMax", productService.getMinMaxPriceProductBySearchName(filterProduct.getSearch()));
        }
        model.addAttribute("products", productsPage.getContent());
        model.addAttribute("productsPage", productsPage);
        model.addAttribute("pageNumbers", pagination(productsPage));
        return "fragment/product-content :: content";
    }


    @GetMapping(value = "/search")
    public String searchItemGrid(@RequestParam("search") String search, @RequestParam("page") Optional<Integer> page,
                                 Model model, HttpServletRequest request,HttpSession session) {

        if (!page.isPresent()) {
            session.removeAttribute("filterProduct");
        }
        int currentPage = page.orElse(1);
        FilterProduct filterProduct = (FilterProduct) session.getAttribute("filterProduct");
        Page<Product> productsPage;

        if (filterProduct != null) {
            productsPage = productService.getProductByFilter(filterProduct,
                    PageRequest.of(currentPage - 1, Constants.MAX_PRODUCTS_PER_HTML_PAGE));
            model.addAttribute("filterPrice", filterProduct.getPrice());
            model.addAttribute("filterProducersIdSet", filterProduct.getProducers());
            model.addAttribute("isFilterPrice", true);
        } else {
            productsPage = productService.findProductBySearch(search, PageRequest.of(currentPage - 1,
                    Constants.MAX_PRODUCTS_PER_HTML_PAGE));
        }

        List<Product> products = productsPage.getContent();
        model.addAttribute("pageNumbers", pagination(productsPage));
        model.addAttribute("search", search);
        model.addAttribute("products", products);
        model.addAttribute("productsPage", productsPage);
        model.addAttribute("breadcrumb", search);
        model.addAttribute("producers", productService.getProducersBySearchProductName(search));
        model.addAttribute("minMax", productService.getMinMaxPriceProductBySearchName(search));
        model.addAttribute("urlPagination", request.getRequestURI() + '?');
        return "product-grid";
    }

}
