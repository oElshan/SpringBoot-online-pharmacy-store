package ru.isha.store.rest;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.HttpStatus;
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
	 * Add new order
	 *
	 * @return created order
	 */
	@RequestMapping(
			method = RequestMethod.POST,
			consumes = "application/json;charset=UTF-8",
			produces = "application/json;charset=UTF-8")
	@JsonView(Views.Public.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	public ClientOrder addClientOrder(@RequestBody @Valid CartItemDTO cartItemDTO) {
		return orderService.newClientOrder(cartItemDTO);
	}

	/**
	 * Delete order
	 */
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteClientOrder(@PathVariable("id") Long id) {
		orderService.deleteOrder(id);
	}

}


