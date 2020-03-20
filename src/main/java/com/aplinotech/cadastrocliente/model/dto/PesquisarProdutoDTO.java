package com.aplinotech.cadastrocliente.model.dto;

public class PesquisarProdutoDTO {
	
	private String nome;
	private String codigoProduto;

	public String getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigoProduto(String codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
