package com.aplinotech.cadastrocliente.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.aplinotech.cadastrocliente.model.Usuario;
import com.aplinotech.cadastrocliente.service.impl.UserServiceImpl;
import com.aplinotech.cadastrocliente.util.Routes;
import com.aplinotech.cadastrocliente.util.Views;

@Controller
public class UserController {	
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@GetMapping(value = Routes.USER_NOVO)
	public ModelAndView novo(){
		ModelAndView mv = new ModelAndView(Views.NOVO);
		Usuario user = new Usuario();
		mv.addObject("user", user);
		return mv;
	}
	
	@PostMapping(Routes.USER_SALVAR)
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
	
	@GetMapping(value = Routes.USER_ATUALIZAR)
	public ModelAndView alterar(@PathVariable(value = "id") Long id){
		ModelAndView mv = new ModelAndView(Views.ATUALIZAR);
		Usuario user = userServiceImpl.findById(id);
		mv.addObject("user", user);
		return mv;
	}
	
	@PostMapping(value = Routes.USER_ALTERAR)
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
	
	@GetMapping(value = Routes.USER_EXCLUIR)
	public String excluir(@PathVariable(value = "id") Long id, ModelMap modelMap){
		Usuario user = userServiceImpl.findById(id);
		userServiceImpl.deleteUser(user);
		return "redirect:/";
	}
	
	@GetMapping(value = Routes.USERS)
	public String listar(ModelMap modelMap) {
		List<Usuario> users = userServiceImpl.findAllUsers();
		modelMap.addAttribute("users", users);
		return Views.LISTAR;
	}
	
	@GetMapping(value = Routes.USER_VISUALIZAR)
	public String visualizar(@PathVariable(value = "id") Long id, ModelMap modelMap){
		Usuario user = userServiceImpl.findById(id);
		modelMap.addAttribute("user", user);
		return Views.VISUALIZAR;
	}	
	
	@GetMapping(Routes.USER_PESQUISAR)
	public String pesquisar(@ModelAttribute(value = "name") String name, ModelMap modelMap){
		List<Usuario> users = userServiceImpl.findByNome(name);
		modelMap.addAttribute("users", users);
		return Views.LISTAR;
	}

}
