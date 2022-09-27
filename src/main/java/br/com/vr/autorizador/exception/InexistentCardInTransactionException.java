package br.com.vr.autorizador.exception;

public class InexistentCardInTransactionException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public InexistentCardInTransactionException(String message) {
		super(message);
	}
	
	public InexistentCardInTransactionException(String message, Throwable t) {
		super(message, t);
	}

	public InexistentCardInTransactionException() {
	}	
}
