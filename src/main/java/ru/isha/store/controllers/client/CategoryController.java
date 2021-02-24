package ru.isha.store.controllers.client;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.isha.store.controllers.AbstractProductController;
import ru.isha.store.dto.FilterProduct;
import ru.isha.store.entity.Category;
import ru.isha.store.entity.Product;
import ru.isha.store.entity.Subcategory;
import ru.isha.store.services.ProductService;
import ru.isha.store.utils.Constants;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/categories")
public class CategoryController extends AbstractProductController {

    private ProductService productService;

    private ServletContext servletContext;

    public CategoryController(ProductService productService, ServletContext servletContext) {
        this.productService = productService;
        this.servletContext = servletContext;
    }

    // TODO: 2020-11-30 вопрос по реквесту метода get каким образом запрашивать категории праметром или url
    @RequestMapping(value = "/*/*",method = RequestMethod.POST)
    public  String showProductByFilter(@ModelAttribute("searchProductForm") FilterProduct filterProduct, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("filterProduct", filterProduct);
        return "redirect:"+request.getRequestURI();
    }

    @RequestMapping(value = "/*/{subCategory}" ,method = RequestMethod.GET)
  public  String showProductBySubcategory(@PathVariable("subCategory") String subCategory , @RequestParam("page") Optional<Integer> page, Model model, HttpServletRequest request) {

        int currentPage =page.orElse(1);
        Page<Product> productsPage;

        if (model.containsAttribute("filterProduct")) {
            FilterProduct filterProduct = (FilterProduct) model.getAttribute("filterProduct");
            productsPage = productService.getProductByFilter(filterProduct, PageRequest.of(currentPage - 1, Constants.MAX_PRODUCTS_PER_HTML_PAGE));
            model.addAttribute("filterProduct",  filterProduct);
            model.addAttribute("urlPagination", request.getRequestURI());
            model.addAttribute("minMax", productService.getMinMaxPriceProductByCategoryURL(subCategory));
            model.addAttribute("filterPrice", filterProduct.getPrice());
            model.addAttribute("filterProducersIdSet", filterProduct.getProducers());
            model.addAttribute("isFilterPrice", true);
        } else {
            productsPage =productService.findAllProductBySubCategoryURL(subCategory, PageRequest.of(currentPage-1,Constants.MAX_PRODUCTS_PER_HTML_PAGE) );
            model.addAttribute("filterProduct", new FilterProduct());
            model.addAttribute("urlPagination", request.getRequestURI());
            model.addAttribute("minMax", productService.getMinMaxPriceProductByCategoryURL(subCategory));

        }

        Subcategory subcategory = productService.findSubcategoryByURL(subCategory);
        model.addAttribute("products", productsPage.getContent());
        model.addAttribute("producers", productService.getProducersByCategoryURL(subCategory));
        model.addAttribute("category",subcategory);
        model.addAttribute("breadcrumb", subcategory.getName());
        model.addAttribute("productsPage", productsPage);
        model.addAttribute("urlForm", request.getRequestURI());
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
