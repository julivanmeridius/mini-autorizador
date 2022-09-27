package br.com.vr.autorizador.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.PositiveOrZero;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.swagger.annotations.ApiModelProperty;

public class CartaoTransacaoDto implements Serializable {

	private static final long serialVersionUID = -6326759176679855517L;

	@ApiModelProperty(value = "Numero do Cartao", required = true)
	private Long numeroCartao;

	@ApiModelProperty(value = "Senha do Cartao", required = true)
	private Integer senhaCartao;

	@ApiModelProperty(value = "Valor da Transacao a ser realizada no Cartao", required = true)
	@PositiveOrZero
	private BigDecimal valor;

	public Long getNumeroCartao() {
		return numeroCartao;
	}

	public void setNumeroCartao(Long numeroCartao) {
		this.numeroCartao = numeroCartao;
	}

	public Integer getSenhaCartao() {
		return senhaCartao;
	}

	public void setSenhaCartao(Integer senhaCartao) {
		this.senhaCartao = senhaCartao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
