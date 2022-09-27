package br.com.vr.autorizador.util;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import static java.util.ResourceBundle.getBundle;
import static java.util.Locale.getDefault;

public class StringUtils {
		
	public static final String BLANK = "";
	
	public static String getMensagemPadrao(final String chaveMensagem, final Object... params) {
		final ResourceBundle bundle = getBundle("autorizador-messages", getDefault());

		return recuperarTexto(bundle, chaveMensagem, params);
	}
	
	private static String recuperarTexto(final ResourceBundle bundle, final String chaveMensagem, final Object params) {
		String mensagem = BLANK;

		try {
			mensagem = bundle.getString(chaveMensagem);
		} catch (final MissingResourceException e) {
			return chaveMensagem;
		}

		return new MessageFormat(mensagem).format(params);
	}
}
