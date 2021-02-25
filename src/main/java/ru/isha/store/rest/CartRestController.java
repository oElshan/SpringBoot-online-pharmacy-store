package ru.isha.store.rest;


import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.isha.store.dto.OrderForm;
import ru.isha.store.entity.ClientOrder;
import ru.isha.store.model.ShoppingCart;
import ru.isha.store.model.ShoppingCartItem;
import ru.isha.store.utils.Constants;
import ru.isha.store.utils.Views;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping(value = "/rest/cart")
//@Secured({"ROLE_USER"})
public class CartRestController {


	/**
	 * Viewing the cart.
	 */
	@RequestMapping(
			method = RequestMethod.GET,
			produces = "application/json;charset=UTF-8")
	@ResponseBody
	@JsonView(Views.Internal.class)
	public ShoppingCart getCart(Principal principal, HttpSession session) {
		return (ShoppingCart) session.getAttribute(Constants.CURRENT_SHOPPING_CART);
	}

	/**
	 * Adding a product.
	 *
	 * @return updated cart
	 * @throws  if the specified product does not exist
	 */
	@RequestMapping(
		method = RequestMethod.PUT,
		consumes = "application/json;charset=UTF-8",
		produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ShoppingCart addItem(Principal principal, @RequestBody ShoppingCartItem item)  {
		return null;
	}

	/**
	 * Clearing the cart.
	 *
	 * @return cleared cart
	 */
	@RequestMapping(
		method = RequestMethod.DELETE,
		produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ShoppingCart clearCart(Principal principal) {
		return null;
	}

	/**
	 * Setting delivery option.
	 *
	 * @return updated cart
	 */
	@RequestMapping(value = "/delivery/{delivery}",
		method = RequestMethod.PUT,
		produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ShoppingCart setDelivery(Principal principal, @PathVariable String delivery) {
		return null;
	}

	/**
	 * Order registration.
	 *
	 * @return created order
	 */
	@RequestMapping(value = "/payment",
		method = RequestMethod.POST,
		consumes = "application/json;charset=UTF-8",
		produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResponseEntity<ClientOrder> payByCard(Principal principal, @Valid @RequestBody OrderForm orderForm)  {
		return null;
	}
}
