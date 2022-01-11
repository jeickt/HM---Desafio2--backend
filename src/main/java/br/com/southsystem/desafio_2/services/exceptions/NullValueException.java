package br.com.southsystem.desafio_2.services.exceptions;

public class NullValueException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public NullValueException(String msg) {
		super(msg);
	}
}
