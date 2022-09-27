package br.com.vr.autorizador.exception;

public class IncorrectPasswordCardException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public IncorrectPasswordCardException(String message) {
		super(message);
	}
	
	public IncorrectPasswordCardException(String message, Throwable t) {
		super(message, t);
	}

	public IncorrectPasswordCardException() {
	}	
}
