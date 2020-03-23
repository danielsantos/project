package com.aplinotech.cadastrocliente.model;

import lombok.Data;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
@Data
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

	@OneToOne
	private Usuario usuario;
	
	@Transient
	private String dataFormatada;
	
	@Transient
	private BigDecimal valorUnitarioTotal = BigDecimal.ZERO;

}
