package ru.isha.store.controllers.client;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.isha.store.controllers.AbstractProductController;
import ru.isha.store.dto.FilterProduct;
import ru.isha.store.entity.Category;
import ru.isha.store.entity.Product;
import ru.isha.store.entity.Subcategory;
import ru.isha.store.exception.UnknownEntityException;
import ru.isha.store.services.ProductService;
import ru.isha.store.utils.Constants;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/categories")
public class CategoryController extends AbstractProductController {

    private final ProductService productService;

    private final ServletContext servletContext;

    public CategoryController(ProductService productService, ServletContext servletContext) {
        this.productService = productService;
        this.servletContext = servletContext;
    }

    @RequestMapping(value = "/*/*",method = RequestMethod.POST,produces ="application/json" )
    public  String showProductByFilter(@RequestBody FilterProduct filterProduct, Model model,
                                       HttpServletRequest request,
                                       HttpSession session) {

        Subcategory subcategory;
        model.addAttribute("filterProduct", filterProduct);
        session.setAttribute("filterProduct", filterProduct);
        Page<Product> productsPage = productService.getProductByFilter(filterProduct, PageRequest.of(0, Constants.MAX_PRODUCTS_PER_HTML_PAGE));
        model.addAttribute("urlPagination", request.getRequestURI() + '?');
        model.addAttribute("filterPrice", filterProduct.getPrice());
        model.addAttribute("filterProducersIdSet", filterProduct.getProducers());
        model.addAttribute("isFilterPrice", true);
        if (filterProduct.getId() != null) {
            subcategory = productService.getSubcategoryById(filterProduct.getId());
            model.addAttribute("minMax", productService.getMinMaxPriceProductBySubcategory(subcategory));
            model.addAttribute("producers", productService.getProducersByCategoryURL(subcategory.getUrl()));
            model.addAttribute("category",subcategory);
            model.addAttribute("breadcrumb", subcategory.getName());
        }
        model.addAttribute("products", productsPage.getContent());
        model.addAttribute("productsPage", productsPage);
        model.addAttribute("pageNumbers", pagination(productsPage));
        return "fragment/product-content :: content";
    }

    // TODO: 2021-03-02 forward for filter product 
    @RequestMapping(value = "/*/{subCategory}" ,method = RequestMethod.GET)
  public  String showProductBySubcategory(@PathVariable("subCategory") String subCategoryPath ,
                                          @RequestParam("page") Optional<Integer> page, Model model,
                                          HttpServletRequest request, HttpSession session) {
        if (!page.isPresent()) {
            session.removeAttribute("filterProduct");
        }
        int currentPage =page.orElse(1);
        Page<Product> productsPage;
        FilterProduct filterProduct = (FilterProduct) session.getAttribute("filterProduct");
        if (filterProduct != null) {
            productsPage = productService.getProductByFilter(filterProduct, PageRequest.of(currentPage - 1, Constants.MAX_PRODUCTS_PER_HTML_PAGE));
            model.addAttribute("filterPrice", filterProduct.getPrice());
            model.addAttribute("filterProducersIdSet", filterProduct.getProducers());
            model.addAttribute("isFilterPrice", true);
        } else {
            productsPage = productService.findAllProductBySubCategoryURL(subCategoryPath, PageRequest.of(currentPage - 1, Constants.MAX_PRODUCTS_PER_HTML_PAGE));
            if (productsPage.isEmpty()) {
                throw new UnknownEntityException("NotExist", Subcategory.class.getName());
            }
        }

        model.addAttribute("urlPagination", request.getRequestURI() + '?');
        Subcategory subcategory = productService.findSubcategoryByURL(subCategoryPath);
        model.addAttribute("minMax", productService.getMinMaxPriceProductBySubcategory(subcategory));
        model.addAttribute("products", productsPage.getContent());
        model.addAttribute("producers", productService.getProducersByCategoryURL(subCategoryPath));
        model.addAttribute("category",subcategory);
        model.addAttribute("breadcrumb", subcategory.getName());
        model.addAttribute("productsPage", productsPage);
        model.addAttribute("pageNumbers", pagination(productsPage));

        return "product-grid";
    }


    @GetMapping("/*")
    public String showCategories(Model model, HttpServletRequest request) {

        String categoryUrl = request.getRequestURI();
        Category category = productService.findCategoryByUrl(categoryUrl);
        List<Subcategory> subcategories = (List<Subcategory>) servletContext.getAttribute(Constants.SUBCATEGORY_LIST);
        model.addAttribute("subcategories", subcategories.stream().filter(s -> s.getCategory().getId() == category.getId()).collect(Collectors.toList()));
        model.addAttribute("category", category);
        model.addAttribute("breadcrumb", category.getName());

        return "category-grid";
    }

}
