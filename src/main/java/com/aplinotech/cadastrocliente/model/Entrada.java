package com.aplinotech.cadastrocliente.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "entrada")
public class Entrada {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@OneToOne
	private Produto produto;
	
	private Integer quantidade;
	private BigDecimal custoUnitario = BigDecimal.ZERO;
	private BigDecimal valorVendaUnitario = BigDecimal.ZERO;
	private Date data;

	@OneToOne
	private Usuario usuario;
	
	@Transient
	private String dataFormatada;
	
	@Transient
	private BigDecimal custoUnitarioTotal;

	@Transient
	private BigDecimal valorVendaUnitarioTotal;

	public BigDecimal getCustoUnitarioTotal() {
		return custoUnitario.multiply(new BigDecimal(quantidade));
	}

	public BigDecimal getValorVendaUnitarioTotal() {
		return valorVendaUnitario.multiply(new BigDecimal(quantidade));
	}


}
