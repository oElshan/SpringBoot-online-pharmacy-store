package ru.isha.store.rest;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.isha.store.entity.ClientOrder;
import ru.isha.store.services.OrderService;

import java.security.Principal;
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
	 * @return orders list of the specified customer
	 */
	@RequestMapping(
			method = RequestMethod.GET,
			produces = "application/json;charset=UTF-8")
	@ResponseBody
	public List<ClientOrder> getOrders() {
		return orderService.getAll();
	}


	/**
	 * View orders.
	 *
	 * @return orders list of the specified customer
	 */
	@RequestMapping(value = "/{id}",
			method = RequestMethod.GET,
			produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ClientOrder getOrder(@PathVariable("id") long id) {
		return orderService.getClientOrderById(id);
	}

	/**
	 * View a single order.
	 *
	 * @return order of the specified customer
	 */
	@RequestMapping(value = "/{id}",
		method = RequestMethod.GET,
		produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ClientOrder getOrder(Principal principal, @PathVariable long id)  {
		return null;
	}
}
