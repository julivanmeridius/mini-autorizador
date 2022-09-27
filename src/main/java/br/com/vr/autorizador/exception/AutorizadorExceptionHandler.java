package br.com.vr.autorizador.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.vr.autorizador.dto.CartaoDto;
import br.com.vr.autorizador.util.AutorizadorConstants;

/**
 * Excpetion Handler
 * 
 * @author Julivan Silva
 */
@ControllerAdvice
public class AutorizadorExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(DuplicateRegistryException.class)
	public ResponseEntity<CartaoDto> handleDuplicateRegistryException(	final DuplicateRegistryException e,
																		final WebRequest request) {
		CartaoDto cartaoDto = generateCartaoDtoFromRequestData(request);
		return new ResponseEntity<CartaoDto>(cartaoDto, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	/**
	 * @param request -
	 * @return CartaoDto
	 */
	private CartaoDto generateCartaoDtoFromRequestData(final WebRequest request) {
		Map<String, String[]> params = request.getParameterMap();		
		String numeroCartao = (String) params.get(AutorizadorConstants.NUMERO_CARTAO)[0];
		String senhaCartao = (String) params.get(AutorizadorConstants.SENHA_CARTAO)[0];
		
		CartaoDto cartaoDto = new CartaoDto();		
		cartaoDto.setNumeroCartao(Long.valueOf(numeroCartao));
		cartaoDto.setSenha(Integer.valueOf(senhaCartao));
		return cartaoDto;
	}
	
	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<Void> handleRecordNotFoundException(RecordNotFoundException rn, final WebRequest request) {
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InexistentCardInTransactionException.class)
	public ResponseEntity<String> handleInexistentCardInTransactionException(InexistentCardInTransactionException rn, final WebRequest request) {
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(AutorizadorConstants.CARTAO_INEXISTENTE);
	}
	
	@ExceptionHandler(CardWithInsuficientFundsException.class)
	public ResponseEntity<String> handleCardWithInsuficientFundsException(CardWithInsuficientFundsException rn, final WebRequest request) {
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(AutorizadorConstants.CARTAO_SEM_SALDO);
	}
	
	@ExceptionHandler(IncorrectPasswordCardException.class)
	public ResponseEntity<String> handleIncorrectPasswordCardException(IncorrectPasswordCardException rn, final WebRequest request) {
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(AutorizadorConstants.SENHA_INVALIDA);
	}
}
