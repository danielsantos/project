package com.aplinotech.cadastrocliente.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.aplinotech.cadastrocliente.model.Usuario;
import com.aplinotech.cadastrocliente.service.impl.UserServiceImpl;
import com.aplinotech.cadastrocliente.util.Routes;
import com.aplinotech.cadastrocliente.util.Views;

@Controller
public class UserController {	
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	/**
	 * Direciona para página de cadastro
	 */
	@RequestMapping(value = Routes.USER_NOVO, method = RequestMethod.GET)
	public ModelAndView novo(){
		ModelAndView mv = new ModelAndView(Views.NOVO);
		Usuario user = new Usuario();
		mv.addObject("user", user);
		return mv;
	}
	
	/**
	 * Cadastra um user no banco de dados
	 */
	@RequestMapping(value = Routes.USER_SALVAR, method = RequestMethod.POST)
	public ModelAndView salvar(@ModelAttribute(value = "user") Usuario user, Errors errors, ModelMap modelMap){
		ModelAndView mv = new ModelAndView(Views.NOVO);
		modelMap.addAttribute("user", user);
		if(errors.hasErrors()){
			return mv;
		}else{
			userServiceImpl.saveUser(user);
			mv.addObject("mensagem", "Salvo com sucesso!");
		}
		return mv;
	}
	
	/**
	 * Direciona para página de atualização
	 */
	@RequestMapping(value = Routes.USER_ATUALIZAR, method = RequestMethod.GET)
	public ModelAndView alterar(@PathVariable(value = "id") Long id){
		ModelAndView mv = new ModelAndView(Views.ATUALIZAR);
		Usuario user = userServiceImpl.findById(id);
		mv.addObject("user", user);
		return mv;
	}
	
	/**
	 * Atualiza um user no banco de dados
	 */
	@RequestMapping(value = Routes.USER_ALTERAR, method = RequestMethod.POST)
	public ModelAndView atualizar(@ModelAttribute(value = "user") Usuario user, Errors errors, ModelMap modelMap){
		ModelAndView mv = new ModelAndView(Views.ATUALIZAR);
		modelMap.addAttribute("user", user);
		if(errors.hasErrors()){
			return mv;
		}else{
			userServiceImpl.updateUser(user);
			mv.addObject("mensagem", "Dados atualizados com sucesso!");
		}
		return mv;
	}
	
	/**
	 * Exclui um user do banco de dados
	 */
	@RequestMapping(value = Routes.USER_EXCLUIR, method =RequestMethod.GET)
	public String excluir(@PathVariable(value = "id") Long id, ModelMap modelMap){
		Usuario user = userServiceImpl.findById(id);
		userServiceImpl.deleteUser(user);
		return "redirect:/";
	}
	
	/**
	 * Lista os users
	 */
	@RequestMapping(value = Routes.USERS, method = RequestMethod.GET)
	public String listar(ModelMap modelMap) {
		List<Usuario> users = userServiceImpl.findAllUsers();
		modelMap.addAttribute("users", users);
		return Views.LISTAR;
	}
	
	/**
	 * Visualizar detalhes de um user
	 */
	@RequestMapping(value = Routes.USER_VISUALIZAR, method = RequestMethod.GET)
	public String visualizar(@PathVariable(value = "id") Long id, ModelMap modelMap){
		Usuario user = userServiceImpl.findById(id);
		modelMap.addAttribute("user", user);
		return Views.VISUALIZAR;
	}	
	
	/**
	 * Pesquisa um user pelo nome
	 */
	@RequestMapping(value = Routes.USER_PESQUISAR, method = RequestMethod.GET)
	public String pesquisar(@ModelAttribute(value = "name") String name, ModelMap modelMap){
		List<Usuario> users = userServiceImpl.findByNome(name);
		modelMap.addAttribute("users", users);
		return Views.LISTAR;
	}

}
