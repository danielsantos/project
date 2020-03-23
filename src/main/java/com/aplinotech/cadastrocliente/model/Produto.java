package com.aplinotech.cadastrocliente.model;

import lombok.Data;

import java.math.BigDecimal;

import javax.persistence.*;

@Entity
@Data
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

	@OneToOne
	private Usuario usuario;


	public BigDecimal getValorTotal() {
		return valorVendaUnitario.multiply(new BigDecimal(qtdParaBaixa));
	}

	public BigDecimal getCustoUnitarioTotal() {
		return custoUnitario.multiply(new BigDecimal(quantidadeTotal));
	}

	public BigDecimal getValorVendaUnitarioTotal() {
		return valorVendaUnitario.multiply(new BigDecimal(quantidadeTotal));
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

}
