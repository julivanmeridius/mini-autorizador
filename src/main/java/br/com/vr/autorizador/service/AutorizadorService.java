package br.com.vr.autorizador.service;

import java.math.BigDecimal;

import br.com.vr.autorizador.dto.CartaoDto;
import br.com.vr.autorizador.dto.CartaoTransacaoDto;

public interface AutorizadorService {

	CartaoDto createNewCard(CartaoDto cartaoDto);
	
	BigDecimal getCardBalance(Long numeroCartao);
	
	void processTransaction(CartaoTransacaoDto cartaoTransacao);

}
