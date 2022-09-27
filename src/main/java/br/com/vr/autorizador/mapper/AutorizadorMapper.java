package br.com.vr.autorizador.mapper;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.vr.autorizador.domain.CartaoDomain;
import br.com.vr.autorizador.dto.CartaoDto;
import br.com.vr.autorizador.dto.CartaoTransacaoDto;

/**
 * Mapper
 * 
 * @author Julivan Silva
 */
@Component
public class AutorizadorMapper {

	Logger logger = LoggerFactory.getLogger(AutorizadorMapper.class);
	private static final String MSG_SAIDA_CONVERSAO = "Objeto Saida: {}";

	@Value("${autorizador.cartao.valor.default.inicial}")
	private BigDecimal valorDefaultInicial;
	
	public CartaoDto performMappingToCartaoDto(CartaoDomain domain) {
		logger.debug("Executando Mapper--> performMappingToCartaoDto()");
		logger.debug("CartaoDomain{}", domain);

		CartaoDto cartaoDto = new CartaoDto();
		cartaoDto.setNumeroCartao(domain.getNumeroCartao());
		cartaoDto.setSenha(domain.getSenha());

		logger.debug(MSG_SAIDA_CONVERSAO, cartaoDto);

		return cartaoDto;
	}

	public CartaoDomain performMappingToCartaoDomain(CartaoDto dto) {
		logger.debug("Executando Mapper--> performMappingToCartaoDomain()");
		logger.debug("CartaoDto{}", dto);

		CartaoDomain domain = new CartaoDomain();
		domain.setNumeroCartao(dto.getNumeroCartao());
		domain.setSenha(dto.getSenha());
		domain.setSaldo(valorDefaultInicial);

		logger.debug(MSG_SAIDA_CONVERSAO, domain);

		return domain;
	}
	
	public CartaoDto performMappingToCartaoDtoForReturn(CartaoTransacaoDto cartaoTransacaoDto) {
		logger.info("Executando Mapper--> performMappingToCartaoDto()");
		logger.debug("CartaoTransacaoDto{}", cartaoTransacaoDto);

		CartaoDto cartaoDto = new CartaoDto();
		cartaoDto.setNumeroCartao(cartaoTransacaoDto.getNumeroCartao());
		cartaoDto.setSenha(cartaoTransacaoDto.getSenhaCartao());

		logger.debug(MSG_SAIDA_CONVERSAO, cartaoDto);

		return cartaoDto;
	}
}
