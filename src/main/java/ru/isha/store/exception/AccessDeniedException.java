package ru.isha.store.exception;

import javax.servlet.http.HttpServletResponse;


public class AccessDeniedException extends AbstractApplicationException {
	private static final long serialVersionUID = -8981777225188967376L;

	public AccessDeniedException(String s) {
		super(s, HttpServletResponse.SC_FORBIDDEN);
	}
}
