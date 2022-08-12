package com.santex.webcrawler.exception;

public class BadRequestException extends RuntimeException {
	
	private static final long serialVersionUID = -7322075533884425748L;

	public BadRequestException(String message) {
		super(message);
	}
}
