package com.aplinotech.cadastrocliente.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item_baixa")
public class ItemBaixa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@OneToOne
	private Baixa baixa;
	
	@OneToOne
	private Produto produto;
	
	private BigDecimal valorUnitario;
	private Integer quantidade;

	@OneToOne
	private Usuario usuario;
	
	@Transient
	private String dataFormatada;
	
	@Transient
	private BigDecimal valorUnitarioTotal = BigDecimal.ZERO;

	public BigDecimal getValorUnitarioTotal() {
		return valorUnitario.multiply(new BigDecimal(quantidade));
	}

}
