package com.spring.crm.error;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = -8090887188789992873L;

	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotFoundException(String message) {
		super(message);
	}

	public NotFoundException(Throwable cause) {
		super(cause);
	}
}
