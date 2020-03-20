package com.aplinotech.cadastrocliente.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Setup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(nullable = false)
	private Date dataPrimeiroAcesso;
	
	@Column(nullable = false)
	private Date dataExpiracao;
	
	@Column
	private String codigoAtivacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataPrimeiroAcesso() {
		return dataPrimeiroAcesso;
	}

	public void setDataPrimeiroAcesso(Date dataPrimeiroAcesso) {
		this.dataPrimeiroAcesso = dataPrimeiroAcesso;
	}

	public Date getDataExpiracao() {
		return dataExpiracao;
	}

	public void setDataExpiracao(Date dataExpiracao) {
		this.dataExpiracao = dataExpiracao;
	}

	public String getCodigoAtivacao() {
		return codigoAtivacao;
	}

	public void setCodigoAtivacao(String codigoAtivacao) {
		this.codigoAtivacao = codigoAtivacao;
	}
	
}
