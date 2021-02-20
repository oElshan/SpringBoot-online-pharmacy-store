package ru.isha.store.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.isha.store.entity.Product;

import java.util.Collection;

@Controller
@RequestMapping(value = "/rest/products")
public class ProductsRestController {

	/**
	 * All the products.
	 */
	@RequestMapping(
		method = RequestMethod.GET,
		produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Collection<Product> getProducts() {
		return null;
	}

	/**
	 * Viewing a single product.
	 *
	 * @return product with the specified id
	 */
	@RequestMapping(value = "/{id}",
		method = RequestMethod.GET,
		produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Product getProduct(@PathVariable long id){
		return null;
	}
}
