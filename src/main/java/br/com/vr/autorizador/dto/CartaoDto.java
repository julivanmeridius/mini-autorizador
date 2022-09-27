package br.com.vr.autorizador.dto;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.swagger.annotations.ApiModelProperty;

public class CartaoDto implements Serializable {

	private static final long serialVersionUID = -6326759176679855517L;

	@ApiModelProperty(value = "Numero do Cartao", required = true)	
	private Long numeroCartao;

	@ApiModelProperty(value = "Senha do Cartao", required = true)	
	private Integer senha;
	
	public Long getNumeroCartao() {
		return numeroCartao;
	}

	public void setNumeroCartao(Long numeroCartao) {
		this.numeroCartao = numeroCartao;
	}

	public Integer getSenha() {
		return senha;
	}

	public void setSenha(Integer senha) {
		this.senha = senha;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
