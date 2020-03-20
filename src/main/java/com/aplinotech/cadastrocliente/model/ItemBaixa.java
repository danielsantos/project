package com.aplinotech.cadastrocliente.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class ItemBaixa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@OneToOne
	private Baixa baixa;
	
	@OneToOne
	private Produto produto;
	
	@Column
	private BigDecimal valorUnitario;
	
	@Column
	private Integer quantidade;
	
	@Transient
	private String dataFormatada;
	
	@Transient
	private BigDecimal valorUnitarioTotal = BigDecimal.ZERO;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Baixa getBaixa() {
		return baixa;
	}

	public void setBaixa(Baixa baixa) {
		this.baixa = baixa;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public String getDataFormatada() {
		return dataFormatada;
	}

	public void setDataFormatada(String dataFormatada) {
		this.dataFormatada = dataFormatada;
	}
	
	public BigDecimal getValorUnitarioTotal() {
		return valorUnitario.multiply(new BigDecimal(quantidade));
	}
	
}
