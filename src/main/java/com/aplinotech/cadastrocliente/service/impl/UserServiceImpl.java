package com.aplinotech.cadastrocliente.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aplinotech.cadastrocliente.model.Usuario;
import com.aplinotech.cadastrocliente.repository.UserRepository;
import com.aplinotech.cadastrocliente.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public Usuario findById(Long id) {
		Usuario user = userRepository.findOne(id);
		return user;
	}

	@Override
	public List<Usuario> findByNome(String nome) {
		List<Usuario> users = userRepository.findByNome(nome);
		return users;
	}

	@Override
	public void saveUser(Usuario user) {
		userRepository.save(user);
	}

	@Override
	public void updateUser(Usuario user) {
		saveUser(user);
	}

	@Override
	public void deleteUserById(Long id) {
		userRepository.delete(id);
	}
	
	@Override
	public void deleteUser(Usuario user){
		userRepository.delete(user);
	}

	@Override
	public void deleteAllUsers() {
		userRepository.deleteAll();
	}

	@Override
	public List<Usuario> findAllUsers() {
		List<Usuario> users = userRepository.findAll();
		return users;
	}

	@Override
	public boolean isUserExist(Usuario user) {
		return findByNome(user.getNome()) != null;
	}
	
}
