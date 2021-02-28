package ru.isha.store.rest;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.isha.store.dto.CartItemDTO;
import ru.isha.store.entity.ClientOrder;
import ru.isha.store.services.OrderService;
import ru.isha.store.utils.Views;

import javax.validation.Valid;
import java.util.List;

/**
 * Customer orders history.
 */
@Controller
@RequestMapping(value = "/rest/admin/orders")
@Secured({"ROLE_ADMIN"})
public class AdminOrdersRestController {


	private final OrderService orderService;

	public AdminOrdersRestController(OrderService orderService) {
		this.orderService = orderService;
	}

	/**
	 * View orders.
	 *
	 * @return all orders or all orders with status
	 */
	@JsonView(Views.Public.class)
	@RequestMapping(
			method = RequestMethod.GET,
			produces = "application/json;charset=UTF-8")
	@ResponseBody
	public List<ClientOrder> getOrders(@RequestParam(value = "status",required = false) String status) {
		if (status == null) {
			return orderService.getAll();
		}
		return orderService.getOrdersByStatus(status);
	}

	/**
	 * View a single order.
	 *
	 * @return order of the specified customer
	 */
	@JsonView(Views.Public.class)
	@RequestMapping(value = "/{id}",
			method = RequestMethod.GET,
			produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ClientOrder getOrder( @PathVariable long id) {
		return orderService.getClientOrderById(id);
	}

	/**
	 * View all orders with status
	 *
	 * @return orders
	 */
	@JsonView(Views.Public.class)
	@RequestMapping(value = "/status/{status}",
			method = RequestMethod.GET,
			produces = "application/json;charset=UTF-8")
	@ResponseBody
	public List<ClientOrder> getOrderByStatus(@PathVariable("status")String status) {
		return orderService.getOrdersByStatus(status);
	}

	/**
	 * Add new order
	 *
	 * @return created order
	 */
	@RequestMapping(
			value = "/add",
			method = RequestMethod.PUT,
			consumes = "application/json;charset=UTF-8",
			produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ClientOrder addClientOrder(@RequestBody @Valid CartItemDTO cartItemDTO) {
		return orderService.newClientOrder(cartItemDTO);
	}

}


