package com.aplinotech.cadastrocliente.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class Entrada {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@OneToOne
	private Produto produto;
	
	@Column 
	private Integer quantidade;
	
	@Column
	private BigDecimal custoUnitario = BigDecimal.ZERO;
	
	@Column
	private BigDecimal valorVendaUnitario = BigDecimal.ZERO;
	
	@Column
	private Date data;
	
	@Transient
	private String dataFormatada;
	
	@Transient
	private BigDecimal custoUnitarioTotal;

	@Transient
	private BigDecimal valorVendaUnitarioTotal;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getCustoUnitario() {
		return custoUnitario;
	}

	public void setCustoUnitario(BigDecimal custoUnitario) {
		this.custoUnitario = custoUnitario;
	}

	public BigDecimal getValorVendaUnitario() {
		return valorVendaUnitario;
	}

	public void setValorVendaUnitario(BigDecimal valorVendaUnitario) {
		this.valorVendaUnitario = valorVendaUnitario;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDataFormatada() {
		return dataFormatada;
	}

	public void setDataFormatada(String dataFormatada) {
		this.dataFormatada = dataFormatada;
	}

	public BigDecimal getCustoUnitarioTotal() {
		return custoUnitario.multiply(new BigDecimal(quantidade));
	}

	public BigDecimal getValorVendaUnitarioTotal() {
		return valorVendaUnitario.multiply(new BigDecimal(quantidade));
	}
	
}
