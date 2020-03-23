package com.aplinotech.cadastrocliente.service;

import java.util.List;

import com.aplinotech.cadastrocliente.model.Produto;
import com.aplinotech.cadastrocliente.model.Usuario;

public interface ProdutoService {

    void saveOrUpdate(Produto produto);
 
    void deleteLogic(String codigo, Usuario usuario);
    
    Produto findById(Long id);
    
    Produto findByCodigoAndActive(String codigo, Usuario usuario);
 
    List<Produto> findAll();
    
    List<Produto> findAllActive();
    
    List<Produto> findByNome(String nome, Long idUsuario);
    
    Produto findByCodigo(String codigo, Long idUsuario);

    List<Produto> findAllActiveByUser(Long idUsuario);
 
}
