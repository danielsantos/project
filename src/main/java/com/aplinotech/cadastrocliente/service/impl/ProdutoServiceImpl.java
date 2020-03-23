package com.aplinotech.cadastrocliente.service.impl;

import java.util.List;

import com.aplinotech.cadastrocliente.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aplinotech.cadastrocliente.model.Produto;
import com.aplinotech.cadastrocliente.repository.ProdutoRepository;
import com.aplinotech.cadastrocliente.service.ProdutoService;

@Service
@Transactional
public class ProdutoServiceImpl implements ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;

	@Override
	public void saveOrUpdate(Produto produto) {
		produtoRepository.save(produto);
	}

	@Override
	public void deleteLogic(String codigo, Usuario usuario) {
		Produto produto = produtoRepository.findByCodigoAndActive(codigo, usuario.getId());
		produto.setStatus("I");
		saveOrUpdate(produto);
	}

	@Override
	public Produto findById(Long id) {
		return produtoRepository.findOne(id);
	}
	
	@Override
	public Produto findByCodigoAndActive(String codigo, Usuario usuario) {
		return produtoRepository.findByCodigoAndActive(codigo, usuario.getId());
	}

	@Override
	public List<Produto> findAll() {
		return produtoRepository.findAll();
	}
	
	@Override
	public List<Produto> findAllActive() {
		return produtoRepository.findAllActive();
	}

	@Override
	public List<Produto> findAllActiveByUser(Long idUsuario) {
		return produtoRepository.findAllActiveByUser(idUsuario);
	}
	
	@Override
	public List<Produto> findByNome(String nome, Long idUsuario) {
		return produtoRepository.findByNome(nome, idUsuario);
	}
	
	@Override
	public Produto findByCodigo(String codigo, Long idUsuario) {
		return produtoRepository.findByCodigo(codigo, idUsuario);
	}

}
