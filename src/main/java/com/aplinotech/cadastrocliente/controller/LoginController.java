package com.aplinotech.cadastrocliente.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.aplinotech.cadastrocliente.model.Setup;
import com.aplinotech.cadastrocliente.model.dto.AtivacaoDTO;
import com.aplinotech.cadastrocliente.service.impl.SetupServiceImpl;

@Controller
public class LoginController {
	
	private static final String CODE_ACTIVE = "890531";
	
	@Autowired
	private SetupServiceImpl setupServiceImpl;

	/*@RequestMapping(value = "/login")
	public String login(@AuthenticationPrincipal User user) {
		return "login/login";
	}*/
	
	@RequestMapping(value = "/ativar", method = RequestMethod.GET)
	public ModelAndView ativar() {
		
		Setup setup = setupServiceImpl.find();
		
		if ( setup.getCodigoAtivacao() != null ) {
			ModelAndView mv = new ModelAndView("util/home");
			mv.addObject("msgSucesso", "Sistema já ativado!");
			return mv;
		}
		
		ModelAndView mv = new ModelAndView("login/ativar");
		mv.addObject("obj", new AtivacaoDTO());
		return mv;
	}
	
	@RequestMapping(value = "/ativar", method = RequestMethod.POST)
	public ModelAndView ativarok(@ModelAttribute(value = "obj") AtivacaoDTO obj) {
		
		ModelAndView mv = new ModelAndView("util/home");
		Setup setup = setupServiceImpl.find();
		
		if ( setup.getCodigoAtivacao() != null ) {
			mv.addObject("msgSucesso", "Sistema já ativado!");
		}
		
		if ( obj.getCodigoAtivacao().equals(CODE_ACTIVE) ) {
			
			setup.setCodigoAtivacao(CODE_ACTIVE);
			setupServiceImpl.saveOrUpdate(setup);
			mv.addObject("msgSucesso", "Ativação Concluída com Sucesso!");
			
		} else {
			mv.addObject("msgError", "Código de Ativação inválido");
		}
		
		return mv;
		
	}
	
}