package com.aplinotech.cadastrocliente.model;

import lombok.Data;

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
@Data
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

	@OneToOne
	private Usuario usuario;
	
	@Transient
	private String dataFormatada;
	
	@Transient
	private BigDecimal custoUnitarioTotal;

	@Transient
	private BigDecimal valorVendaUnitarioTotal;

}
