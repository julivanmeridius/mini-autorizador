package br.com.vr.autorizador.exception;

public class DuplicateRegistryException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DuplicateRegistryException(String message) {
		super(message);
	}
	
	public DuplicateRegistryException(String message, Throwable t) {
		super(message, t);
	}

	public DuplicateRegistryException() {
	}	
}
