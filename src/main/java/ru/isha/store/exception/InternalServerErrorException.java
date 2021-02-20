package ru.isha.store.exception;

import javax.servlet.http.HttpServletResponse;


public class InternalServerErrorException extends AbstractApplicationException {
	private static final long serialVersionUID = 6735903147426906964L;

	public InternalServerErrorException(String message) {
		super(message, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}

	public InternalServerErrorException(Throwable cause) {
		super(cause, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}

	public InternalServerErrorException(String message, Throwable cause) {
		super(message, cause, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}

}
