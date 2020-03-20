package com.aplinotech.cadastrocliente.service;

import java.util.List;

import com.aplinotech.cadastrocliente.model.Usuario;

public interface UserService {

	Usuario findById(Long id);
	
	List<Usuario> findByNome(String nome);

    void saveUser(Usuario user);
 
    void updateUser(Usuario user);
 
    void deleteUserById(Long id);
    
    void deleteUser(Usuario user);
 
    void deleteAllUsers();
 
    List<Usuario> findAllUsers();
 
    boolean isUserExist(Usuario user);
	
}
