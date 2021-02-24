package ru.isha.store.controllers.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.isha.store.dto.EditProductForm;
import ru.isha.store.dto.NewProductForm;
import ru.isha.store.entity.ClientOrder;
import ru.isha.store.entity.Product;
import ru.isha.store.entity.Status;
import ru.isha.store.services.OrderService;
import ru.isha.store.services.ProductService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private ProductService productService;

    private OrderService orderService;

    public AdminController(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    @GetMapping(value = "/admin/single-product")

    public String editItem(Model model) {
        model.addAttribute("loginError", true);
        return "/single-product";
    }

    @GetMapping(value = "/admin/editItem")
    public String viewEditItem(@RequestParam("id") Long id, Model model) {

        Product product = productService.findProductById(id);
        model.addAttribute("editProduct", product);
        model.addAttribute("editProductForm", new EditProductForm());
        model.addAttribute("specCategoryList", productService.findAllSpecCategory());
        System.out.println("Product id" + id);
        return "edit-items";
    }

    @RequestMapping(value = "/admin/edit-product", method = RequestMethod.POST)
    public String editItem(@Valid @ModelAttribute("editProductForm") EditProductForm editProductForm, BindingResult bindingResult, Model model) throws IOException {
        Product product = productService.findProductById(editProductForm.getId());

        if (bindingResult.hasErrors()) {
            System.out.println(editProductForm);
            model.addAttribute("editProduct", product);
            model.addAttribute("specCategoryList", productService.findAllSpecCategory());
            return "edit-items";
        }
        Product editProduct = productService.editProduct(editProductForm);
        model.addAttribute("editProduct", editProduct);
        model.addAttribute("specCategoryList", productService.findAllSpecCategory());


        System.out.println(product);

        return "edit-items";
    }

    @GetMapping(value = "/admin/create/product")
    public String newProduct(Model model) {
        model.addAttribute("newProductForm", new NewProductForm());
        model.addAttribute("specCategoryList", productService.findAllSpecCategory());
        return "create-product";
    }


    @PostMapping(value = "/admin/create/product")
    public String createProduct(@Valid @ModelAttribute("newProductForm") NewProductForm newProductForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            logger.info("Ошибка валидации формы товара");
            return "create-product";
        }
        Product createdProduct = productService.createProduct(newProductForm);
        model.addAttribute("createdProduct", createdProduct);
        logger.info("----------Обьект создан! id =" + createdProduct.getId());

        return "create-product";
    }


    @RequestMapping(value = "/admin/search-orders", method = RequestMethod.GET)
    public String showOrders(String name, Model model, HttpSession session) {
        return "orders";
    }

    @RequestMapping(value = "/admin/items", method = RequestMethod.GET)
    public String showItems(Model model, HttpSession session) {

        return "items";
    }


    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(@RequestParam("page") Optional<Integer> page, @RequestParam("select") Optional<String> select, Model model, HttpSession session) {
        int currentPage = page.orElse(1);
        String curentSelect = select.orElse("all");
        Page<ClientOrder> clientOrdersPage = orderService.getOrdersLimit(currentPage, 10);
        List<Status> statuses = orderService.getAllStatusOrders();

        int totalPages = clientOrdersPage.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("statuses", statuses);
        model.addAttribute("clientOrdersPage", clientOrdersPage);
        model.addAttribute("orders", clientOrdersPage.getContent());
        return "admin";
    }

    @GetMapping("/admin/new-orders")
    public ModelAndView showNewOrder(ModelMap model) {
        List<ClientOrder> clientOrders = orderService.getAllNewOrders();
        model.addAttribute("orders", clientOrders);
        return new ModelAndView("new-orders", model);
    }


    @GetMapping("/admin/orders/delete")
    public String deleteOrder(@RequestParam("id") Long id, ModelMap model)  {
        logger.info("...........................................deleting client order N"+id);
        orderService.deleteOrder(id);

        return "redirect:/admin";
    }



}
