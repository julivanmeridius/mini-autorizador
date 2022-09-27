package br.com.vr.autorizador.util;

import static br.com.vr.autorizador.util.StringUtils.getMensagemPadrao;

public class AutorizadorConstants {

	public AutorizadorConstants() {
		super();
	}
	
	/** Mensagens padrões de Response de acordo com o código HTTP retornado - ideia inicial **/
	public static final String RESPONSE_200 = getMensagemPadrao("response.code200");
	public static final String RESPONSE_201 = getMensagemPadrao("response.code201");
	public static final String RESPONSE_204 = getMensagemPadrao("response.code204");

	public static final String RESPONSE_304 = getMensagemPadrao("response.code304");

	public static final String RESPONSE_400 = getMensagemPadrao("response.code400");
	public static final String RESPONSE_401 = getMensagemPadrao("response.code401");
	public static final String RESPONSE_404 = getMensagemPadrao("response.code404");

	public static final String RESPONSE_500 = getMensagemPadrao("response.code500");
		
	public static final String CARTAO_INEXISTENTE = "CARTAO_INEXISTENTE ";
	public static final String CARTAO_SEM_SALDO = "SALDO_INSUFICIENTE";
	public static final String SENHA_INVALIDA = "SENHA_INVALIDA";
	public static final String BODY_RESPONSE_OK = "OK";
	
	public static final String NUMERO_CARTAO = "numeroCartao";
	public static final String SENHA_CARTAO = "senha";
	
}
