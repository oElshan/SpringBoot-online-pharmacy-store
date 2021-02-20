package ru.isha.store.rest;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.isha.store.entity.Client;

import javax.validation.Valid;
import java.security.Principal;

/**
 * Customer contacts controller.
 */

@Controller
@RequestMapping(value = "/rest/customer/contacts")
@Secured({"ROLE_USER"})
public class ContactsRestController {

	/**
	 * Viewing contacts.
	 */
	@RequestMapping(
		method = RequestMethod.GET,
		produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Client getContacts(Principal principal) {
		return null;
	}

	/**
	 * Updating contacts.
	 * @return updated contacts
	 */
	@RequestMapping(
		method = RequestMethod.PUT,
		consumes = "application/json;charset=UTF-8",
		produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Client postContacts(Principal principal, @Valid @RequestBody Client client) {
		return null;
	}
}
