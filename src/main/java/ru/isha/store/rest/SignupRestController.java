package ru.isha.store.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.isha.store.entity.Account;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/rest/signup")
public class SignupRestController {

	/**
	 * New account registration.
	 *
	 * @return newly created account
	 */
	@RequestMapping(
		method = RequestMethod.POST,
		consumes ="application/json;charset=UTF-8",
		produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Account postNewAccount(@Valid @RequestBody Account account) {
		return null;
	}
}
