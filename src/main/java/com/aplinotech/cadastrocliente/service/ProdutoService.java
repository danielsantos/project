package com.aplinotech.cadastrocliente.service;

import java.util.List;

import com.aplinotech.cadastrocliente.model.Produto;

public interface ProdutoService {

    void saveOrUpdate(Produto produto);
 
    void deleteLogic(String codigo);
    
    Produto findById(Long id);
    
    Produto findByCodigoAndActive(String codigo);
 
    List<Produto> findAll();
    
    List<Produto> findAllActive();
    
    List<Produto> findByNome(String nome);
    
    Produto findByCodigo(String codigo);
 
}
