package com.aplinotech.cadastrocliente.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(nullable = true)
	private String codigo;
	
	@Column(nullable = true)
	private String nome;
	
	@Column(length = 520)
	private String descricao;
	
	@Column
	private Long quantidadeTotal = 0L; // TODO alterar nome, passar para qtdEmEstoque

	@Column
	private BigDecimal custoUnitario = BigDecimal.ZERO;
	
	@Column
	private BigDecimal valorVendaUnitario = BigDecimal.ZERO;
	
	@Column(nullable = true, length = 1)
	private String status;
	
	@Transient
	private Integer qtdParaBaixa = 0;
	
	@Transient
	private BigDecimal valorTotal;
	
	@Transient
	private BigDecimal custoUnitarioTotal = BigDecimal.ZERO;
	
	@Transient
	private BigDecimal valorVendaUnitarioTotal = BigDecimal.ZERO;

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getQuantidadeTotal() {
		return quantidadeTotal;
	}

	public void setQuantidadeTotal(Long quantidadeTotal) {
		this.quantidadeTotal = quantidadeTotal;
	}

	public BigDecimal getCustoUnitario() {
		return custoUnitario;
	}

	public void setCustoUnitario(BigDecimal custoUnitario) {
		this.custoUnitario = custoUnitario;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getValorVendaUnitario() {
		return valorVendaUnitario;
	}

	public void setValorVendaUnitario(BigDecimal valorVendaUnitario) {
		this.valorVendaUnitario = valorVendaUnitario;
	}

	public Integer getQtdParaBaixa() {
		return qtdParaBaixa;
	}

	public void setQtdParaBaixa(Integer qtdParaBaixa) {
		this.qtdParaBaixa = qtdParaBaixa;
	}

	public BigDecimal getValorTotal() {
		return valorVendaUnitario.multiply(new BigDecimal(qtdParaBaixa));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public BigDecimal getCustoUnitarioTotal() {
		return custoUnitario.multiply(new BigDecimal(quantidadeTotal));
	}

	public BigDecimal getValorVendaUnitarioTotal() {
		return valorVendaUnitario.multiply(new BigDecimal(quantidadeTotal));
	}
	
}
