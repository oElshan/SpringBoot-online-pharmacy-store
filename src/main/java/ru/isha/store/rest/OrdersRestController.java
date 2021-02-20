package ru.isha.store.rest;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.isha.store.entity.ClientOrder;

import java.security.Principal;
import java.util.List;

/**
 * Customer orders history.
 */
@Controller
@RequestMapping(value = "/rest/customer/orders")
@Secured({"ROLE_USER"})
public class OrdersRestController {

	/**
	 * View orders.
	 *
	 * @return orders list of the specified customer
	 */
	@RequestMapping(
		method = RequestMethod.GET,
		produces = "application/json;charset=UTF-8")
	@ResponseBody
	public List<ClientOrder> getOrders(Principal principal) {
		return null;
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
