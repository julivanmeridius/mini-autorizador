package br.com.vr.autorizador.resource;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vr.autorizador.dto.CartaoDto;
import br.com.vr.autorizador.dto.CartaoTransacaoDto;
import br.com.vr.autorizador.exception.DuplicateRegistryException;
import br.com.vr.autorizador.exception.RecordNotFoundException;
import br.com.vr.autorizador.service.AutorizadorService;
import br.com.vr.autorizador.util.AutorizadorConstants;
import io.swagger.annotations.ApiOperation;

/**
 * Cartao Resource
 *  
 * @author Julivan Silva
 */
@RestController
//--@RequestMapping("/autorizador")
public class AutorizadorResource {

	@Autowired
	private AutorizadorService service;
	
	@ApiOperation(
			value = "${swagger.api.cartoes.criar.cartao.value}",
			notes = "${swagger.api.cartoes.criar.cartao.notes}",
			tags = { "Cartoes" })
	@PostMapping("/cartoes")
    public ResponseEntity<CartaoDto> createNewCard(final CartaoDto cartaoDto) throws DuplicateRegistryException {
        return new ResponseEntity<CartaoDto>(service.createNewCard(cartaoDto), new HttpHeaders(), HttpStatus.CREATED);
    }
	
	@ApiOperation(
			value = "${swagger.api.cartoes.consulta.saldo.cartao.value}",
			notes = "${swagger.api.cartoes.consulta.saldo.cartao.notes}",
			tags = { "Cartoes" })
	@GetMapping("/cartoes/{numeroCartao}")
    public ResponseEntity<BigDecimal> getCardBalance(@PathVariable("numeroCartao") final Long numeroCartao) throws RecordNotFoundException {
		return new ResponseEntity<BigDecimal>(service.getCardBalance(numeroCartao), new HttpHeaders(), HttpStatus.OK);
    }
	
	@ApiOperation(
			value = "${swagger.api.cartoes.registrar.transacao.cartao.value}",
			notes = "${swagger.api.cartoes.registrar.transacao.cartao.notes}",
			tags = { "Cartoes" })
	@PostMapping("/transacoes")
	public ResponseEntity<String> processTransaction(final CartaoTransacaoDto cartaoTransacao) {
        service.processTransaction(cartaoTransacao);
		return ResponseEntity.status(HttpStatus.CREATED).body(AutorizadorConstants.BODY_RESPONSE_OK);
    }
}
