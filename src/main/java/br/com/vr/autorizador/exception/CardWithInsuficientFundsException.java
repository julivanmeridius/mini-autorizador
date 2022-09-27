package br.com.vr.autorizador.exception;

public class CardWithInsuficientFundsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public CardWithInsuficientFundsException(String message) {
		super(message);
	}
	
	public CardWithInsuficientFundsException(String message, Throwable t) {
		super(message, t);
	}

	public CardWithInsuficientFundsException() {
	}	
}
