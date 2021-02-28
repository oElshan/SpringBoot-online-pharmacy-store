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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.isha.store.dto.EditOrderDTO;
import ru.isha.store.dto.EditOrderItem;
import ru.isha.store.dto.SearchForm;
import ru.isha.store.entity.ClientOrder;
import ru.isha.store.entity.OrderItem;
import ru.isha.store.entity.Status;
import ru.isha.store.model.OrderStatus;
import ru.isha.store.services.OrderService;
import ru.isha.store.services.ProductService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class AjaxAdminController {
    private static final Logger logger = LoggerFactory.getLogger(AjaxAdminController.class);


    private OrderService orderService;

    private ProductService productService;

    public AjaxAdminController(OrderService orderService, ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
    }

    @RequestMapping(path = "/ajax/admin/orderStatus", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public OrderStatus deleteItemFromShoppingCartView(HttpSession session, Model model) {

//        List<ClientOrder> clientOrderList = orderService.getAllNewOrders();
        OrderStatus orderStatus = new OrderStatus();
        long count = orderService.getCountNewOrders("new");
        System.out.println("---------"+count);
        if (count > 0) {
            orderStatus.setMessage("You have new order!");
            orderStatus.setCount(count);

        }
        return orderStatus;
    }


    @RequestMapping(path = "/ajax/admin/edit-order-item", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public String editOrderItem (@Valid @RequestBody EditOrderItem editOrderItem, BindingResult bindingResult, RedirectAttributes redirectAttributes ) {



        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.editOrderItem",  bindingResult);
            redirectAttributes.addFlashAttribute("editOrderItem",  editOrderItem);

            return "redirect:/ajax/admin/edit-order?id="+editOrderItem.getOrderId() ;
        }


        ClientOrder clientOrder =  orderService.updateClientOrderItem(editOrderItem.getOrderId(), editOrderItem.getProductId(), Integer.parseInt(editOrderItem.getProductCount()));

        return "redirect:/ajax/admin/edit-order?id="+editOrderItem.getOrderId() ;
    }

    @RequestMapping(path = "/ajax/admin/edit-order", method = RequestMethod.GET)
    public ModelAndView editOrder(@RequestParam long id, ModelMap model, HttpServletRequest request) {


        if (!model.containsAttribute("editOrderItem")) {
            model.addAttribute("editOrderItem", new EditOrderItem());
        }

        if (!model.containsAttribute("editOrder")) {
            model.addAttribute("editOrder", new EditOrderDTO());
        }

        ClientOrder clientOrder = orderService.getClientOrderById(id);
        List<OrderItem> orderItems = clientOrder.getOrderItems();
        List<Status> statuses = orderService.getAllStatusOrders();

        BigDecimal totalCost = BigDecimal.ZERO;
        for (OrderItem orderItem : orderItems) {
            totalCost = totalCost.add(orderItem.getProduct().getPrice().multiply(BigDecimal.valueOf(orderItem.getCount())));
        }

        model.addAttribute("statuses", statuses);
        model.addAttribute("clientOrder", clientOrder);
        model.addAttribute("orderItems", orderItems);
        model.addAttribute("totalCost", totalCost);



        return new ModelAndView("fragment/edit-order-modal :: edit-order-modal", model);
    }




    @RequestMapping(path = "/ajax/admin/edit-order", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public String changeOrder(@Valid @RequestBody EditOrderDTO editOrderDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes , Model model) {

        logger.info(editOrderDTO.toString());

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.editOrder", bindingResult);
            redirectAttributes.addFlashAttribute("editOrder", editOrderDTO);

            return "redirect:/ajax/admin/edit-order?id=" + editOrderDTO.getId();
        }

        ClientOrder clientOrder = orderService.updateClientOrder(editOrderDTO);

        List<OrderItem> orderItems = clientOrder.getOrderItems();

        BigDecimal totalCost = BigDecimal.ZERO;
        for (OrderItem orderItem : orderItems) {
            totalCost = totalCost.add(orderItem.getProduct().getPrice().multiply(BigDecimal.valueOf(orderItem.getCount())));
        }

        List<Status> statuses = orderService.getAllStatusOrders();

        model.addAttribute("editOrderItem", new EditOrderItem());
        model.addAttribute("statuses", statuses);
        model.addAttribute("clientOrder", clientOrder);
        model.addAttribute("orderItems", orderItems);
        model.addAttribute("totalCost", totalCost);

        return "fragment/edit-order-modal :: edit-order-modal";
    }

    @RequestMapping(path = "/ajax/admin", method = RequestMethod.GET)
    public String showSelectOrders(@RequestParam("page") Optional<Integer> page, @RequestParam("select") Optional<String> select, Model model) {
        int currentPage = page.orElse(1);
        String currentSelect = select.orElse("all");
        Page<ClientOrder> clientOrdersPage;
        clientOrdersPage = currentSelect.equalsIgnoreCase("all") ? orderService.getOrdersLimit(currentPage, 10) :  orderService.getOrdersLimit(currentPage, 10, currentSelect);
        List<Status> statuses = orderService.getAllStatusOrders();
        int totalPages = clientOrdersPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("statuses", statuses);
        model.addAttribute("clientOrdersPage",clientOrdersPage) ;
        model.addAttribute("orders", clientOrdersPage.getContent());

        return  "fragment/data-latest-order :: AjaxDataLatestOrder";
    }


    @RequestMapping( value = "/ajax/json/search-orders",method = RequestMethod.POST,produces = "application/json; charset=UTF-8" )
    public ModelAndView searchOrders(@RequestBody SearchForm search, ModelMap modelMap) {

        System.out.println(search);
        List<ClientOrder> orders = orderService.findOrderByName(search.getSearchName());
        modelMap.addAttribute("orders", orders);

        return new ModelAndView("fragment/data-table-orders :: data-table-orders", modelMap);
    }

    @GetMapping("/ajax/admin/orders/delete-item")
    public String deleteOrderItem(@RequestParam("orderItemId") long orderItemId, @RequestParam("orderId") long orderId, ModelMap model)  {
        orderService.deleteItemFromClientOrder(orderItemId,orderId);
        return "redirect:/ajax/admin/edit-order?id=" + orderId;
    }
}
