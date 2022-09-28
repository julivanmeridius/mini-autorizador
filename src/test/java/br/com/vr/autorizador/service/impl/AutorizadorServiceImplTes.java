package br.com.vr.autorizador.service.impl;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

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


@ExtendWith(MockitoExtension.class)
class AutorizadorServiceImplTes {
				
	@Mock
	public AutorizadorMapper autorizadorMapper;
	
	@Mock	
	private AutorizadorRepository repository;
	
	@InjectMocks
	private AutorizadorService autorizadorService = new AutorizadorServiceImpl();

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
	}
		
	@DisplayName("testRecuperarSaldoPorNumeroCartao_CartaoExistente()")
	@Test	
	public void testRecuperarSaldoPorNumeroCartao_CartaoExistente() {
		System.out.println("###TEST:01 -- testRecuperarSaldoPorNumeroCartao_CartaoExistente()");	
		Long numeroCartao = 6549873025634501L;

		when(repository.findByNumeroCartao(Mockito.anyLong()))
		.thenReturn(Optional.of(criarCartaoDomain()));
		
		assertEquals(500.00, autorizadorService.getCardBalance(numeroCartao).doubleValue());
		verify(repository, times(1)).findByNumeroCartao(numeroCartao);
	}
	
	@DisplayName("testRecuperarSaldoPorNumeroCartao_CartaoInexistente()")
	@Test	
	public void testRecuperarSaldoPorNumeroCartao_CartaoInexistente() {
		System.out.println("###TEST:02 -- testRecuperarSaldoPorNumeroCartao_CartaoInexistente()");		
		Long numeroCartao = 0l;
		
		when(repository.findByNumeroCartao(Mockito.anyLong()))
		.thenThrow(RecordNotFoundException.class);
		
		Assertions.assertThatExceptionOfType(RecordNotFoundException.class)
        .isThrownBy(() -> autorizadorService.getCardBalance(numeroCartao));
	}
	
	@DisplayName("testCriarNovoCartao_CartaoInexistenteNoBD()")
	@Test
	public void testCriarNovoCartao_CartaoInexistenteNoBD() {
		System.out.println("###TEST:03 -- testCriarNovoCartao_CartaoInexistenteNoBD()");

		when(repository.save(Mockito.any())).thenReturn(criarCartaoDto());
		
		assertEquals(6549873025634501l, autorizadorService.createNewCard(criarCartaoDto()).getNumeroCartao());
		assertEquals(1234, autorizadorService.createNewCard(criarCartaoDto()).getSenha());
	}
	
	@DisplayName("testCriarNovoCartao_CartaoJaExistenteNoBD()")
	@Test
	public void testCriarNovoCartao_CartaoJaExistenteNoBD() {
		System.out.println("###TEST:04 -- testCriarNovoCartao_CartaoJaExistenteNoBD()");

		when(repository.save(Mockito.any())).thenThrow(ConstraintViolationException.class);
		
		Assertions.assertThatExceptionOfType(DuplicateRegistryException.class)
        .isThrownBy(() -> autorizadorService.createNewCard(criarCartaoDto()));
	}
		
	@DisplayName("testProcessarTransacaoCartao_ComSucesso")
	@Test
	public void testProcessarTransacaoCartao_ComSucesso() {
		System.out.println("###TEST:05 -- testProcessarTransacaoCartao_ComSucesso()");
		
		when(repository.findByNumeroCartao(Mockito.anyLong()))
			.thenReturn(Optional.of(criarCartaoDomain()));
		
		when(repository.save(Mockito.any()))
			.thenReturn(criarCartaoDomain());
		
		assertThatNoException().isThrownBy(() -> autorizadorService.processTransaction(criarCartaoTransacaoDto()));
	}
	
	@DisplayName("testProcessarTransacaoCartao_ErroCartaoNaoEncontrado()")
	@Test
	public void testProcessarTransacaoCartao_ErroCartaoNaoEncontrado() {
		System.out.println("###TEST:06 -- testProcessarTransacaoCartao_ErroCartaoNaoEncontrado()");

		when(repository.findByNumeroCartao(Mockito.anyLong()))
			.thenThrow(InexistentCardInTransactionException.class);
		
		Assertions.assertThatExceptionOfType(InexistentCardInTransactionException.class)
        	.isThrownBy(() -> autorizadorService.processTransaction(criarCartaoTransacaoDto()));
	}
	
	@DisplayName("testProcessarTransacaoCartao_ErroSenhaCartaoIncorreta()")
	@Test
	public void testProcessarTransacaoCartao_ErroSenhaCartaoIncorreta() {
		System.out.println("###TEST:07 -- testProcessarTransacaoCartao_ErroSenhaCartaoIncorreta()");

		when(repository.findByNumeroCartao(Mockito.anyLong()))
			.thenThrow(IncorrectPasswordCardException.class);
		
		Assertions.assertThatExceptionOfType(IncorrectPasswordCardException.class)
        	.isThrownBy(() -> autorizadorService.processTransaction(criarCartaoTransacaoDto()));
	}
	
	@DisplayName("testProcessarTransacaoCartao_CartaoSemSaldoSuficiente()")
	@Test
	public void testProcessarTransacaoCartao_CartaoSemSaldoSuficiente() {
		System.out.println("###TEST:08 -- testProcessarTransacaoCartao_CartaoSemSaldoSuficiente()");

		when(repository.findByNumeroCartao(Mockito.anyLong()))
			.thenThrow(CardWithInsuficientFundsException.class);
		
		Assertions.assertThatExceptionOfType(CardWithInsuficientFundsException.class)
        	.isThrownBy(() -> autorizadorService.processTransaction(criarCartaoTransacaoDto()));
	}
	
	private CartaoDomain criarCartaoDomain() {
		Long numeroCartao = 6549873025634501L;
		Integer senhaCartao = 1234;
		BigDecimal saldo = BigDecimal.valueOf(500.00);
		
		CartaoDomain cartaoDomain = new CartaoDomain();
		cartaoDomain.setNumeroCartao(numeroCartao);
		cartaoDomain.setSenha(senhaCartao);
		cartaoDomain.setSaldo(saldo);
		
		return cartaoDomain;
	}
	
	private CartaoDto criarCartaoDto() {
		Long numeroCartao = 6549873025634501L;
		Integer senhaCartao = 1234;
		
		CartaoDto cartaoDto = new CartaoDto();
		cartaoDto.setNumeroCartao(numeroCartao);
		cartaoDto.setSenha(senhaCartao);
		
		return cartaoDto;
	}
	
	private CartaoTransacaoDto criarCartaoTransacaoDto() {
		Long numeroCartao = 6549873025634501L;
		Integer senhaCartao = 1234;
		BigDecimal saldo = BigDecimal.valueOf(500.00);
		
		CartaoTransacaoDto ctDto = new CartaoTransacaoDto();
		ctDto.setNumeroCartao(numeroCartao);
		ctDto.setSenhaCartao(senhaCartao);
		ctDto.setValor(saldo);
		
		return ctDto;
	}
}
