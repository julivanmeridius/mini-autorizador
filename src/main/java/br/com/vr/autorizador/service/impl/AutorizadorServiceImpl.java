package br.com.vr.autorizador.service.impl;

import java.math.BigDecimal;

import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vr.autorizador.domain.CartaoDomain;
import br.com.vr.autorizador.dto.CartaoDto;
import br.com.vr.autorizador.dto.CartaoTransacaoDto;
import br.com.vr.autorizador.exception.CardWithInsuficientFundsException;
import br.com.vr.autorizador.exception.DuplicateRegistryException;
import br.com.vr.autorizador.exception.IncorrectPasswordCardException;
import br.com.vr.autorizador.exception.InexistentCardInTransactionException;
import br.com.vr.autorizador.exception.RecordNotFoundException;
import br.com.vr.autorizador.mapper.AutorizadorMapper;
import br.com.vr.autorizador.repository.AutorizadorRepository;
import br.com.vr.autorizador.service.AutorizadorService;
import br.com.vr.autorizador.util.AutorizadorConstants;

/**
 * Autorizador Service
 * 
 * @author Julivan Silva
 */
@Service
@Transactional
public class AutorizadorServiceImpl implements AutorizadorService {

	Logger logger = LoggerFactory.getLogger(AutorizadorServiceImpl.class);

	@Autowired
	private AutorizadorRepository repository;

	@Autowired
	private AutorizadorMapper mapper;

	@Override
	public CartaoDto createNewCard(final CartaoDto cartaoDto) throws DuplicateRegistryException {
		logger.info("Executando Metodo:: createNewCard() Service -- Entrada:{}" + cartaoDto);
		
		try {
			return mapper.performMappingToCartaoDto(repository.save(mapper.performMappingToCartaoDomain(cartaoDto)));
		} catch(ConstraintViolationException | DataIntegrityViolationException ec) { 
			logger.error("Cartao ja existe no Banco de Dados--:  ERROR: " + ExceptionUtils.getStackTrace(ec));
			throw new DuplicateRegistryException();
		}
		catch (Exception e) {
			logger.error("Falha ao tentar gravar objeto no Banco de Dados--:  ERROR: " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		
		return cartaoDto;
	}

	@Override
	public BigDecimal getCardBalance(final Long numeroCartao) {
		logger.info("Executando Metodo:: getCardBalance() Service -- Numero Cartao: " + numeroCartao);

		final CartaoDomain domain = repository.findByNumeroCartao(numeroCartao)
				.orElseThrow(() -> new RecordNotFoundException(AutorizadorConstants.CARTAO_INEXISTENTE));
		logger.debug("Objeto Retornado {} ", domain);

		return domain.getSaldo();
	}

	@Override
	public void processTransaction(final CartaoTransacaoDto cartaoTransacaoDto) {
		logger.info("Executando Metodo:: processTransaction() Service -- Entrada: {}", cartaoTransacaoDto);

		Long numeroCartao = cartaoTransacaoDto.getNumeroCartao();
		logger.debug("numeroCartao:: {}", numeroCartao);
		
		final CartaoDomain domain = repository.findByNumeroCartao(numeroCartao)
				.orElseThrow(() -> new InexistentCardInTransactionException(AutorizadorConstants.CARTAO_INEXISTENTE));
		logger.debug("Objeto Retornado {} ", domain);
		
		Boolean isPasswordOK = isPasswordCorrect(domain, cartaoTransacaoDto);
		logger.debug("isPasswordOK:: {}", isPasswordOK);
		if(!isPasswordOK) {throw new IncorrectPasswordCardException(AutorizadorConstants.SENHA_INVALIDA);}
		
		Boolean isBalanceOK = hasSuficientBalance(domain, cartaoTransacaoDto);
		logger.debug("isBalanceOK:: {}", isBalanceOK);
		if(!isBalanceOK) {throw new CardWithInsuficientFundsException(AutorizadorConstants.CARTAO_SEM_SALDO);}; 		
				
		logger.debug("Iniciando Transacao...");
		domain.setSaldo(getNewBalanceValue(domain.getSaldo(), cartaoTransacaoDto.getValor()));
		repository.save(domain);	
		logger.debug("Termino da Transacao...");
	}
		
	private boolean isPasswordCorrect(CartaoDomain cartaoDomain, CartaoTransacaoDto cartaoTransacaoDto) {
		logger.info("Executando Metodo:: isPasswordCorrect()");
		logger.debug("Senha Cadastrada:: {}", cartaoDomain.getSenha());
		logger.debug("Senha Parametro :: {}", cartaoTransacaoDto.getSenhaCartao());
		
		return cartaoDomain.getSenha().intValue() == cartaoTransacaoDto.getSenhaCartao().intValue();	
	}
	
	private boolean hasSuficientBalance(CartaoDomain cartaoDomain, CartaoTransacaoDto cartaoTransacaoDto) {
		logger.info("Executando Metodo:: isTransactionValid()");
		
		BigDecimal saldoAtualCartao = cartaoDomain.getSaldo();
		logger.debug("saldoAtualCartao:: {}", saldoAtualCartao);
		
		BigDecimal valorParaDebito = cartaoTransacaoDto.getValor();
		logger.debug("valorParaDebito :: {}", valorParaDebito);
		
		return saldoAtualCartao.subtract(valorParaDebito).intValue() >= 0;
	}
	
	private BigDecimal getNewBalanceValue(BigDecimal valorSaldoAtual, BigDecimal valorParaDebito) {
		return valorSaldoAtual.subtract(valorParaDebito);
	}
}
