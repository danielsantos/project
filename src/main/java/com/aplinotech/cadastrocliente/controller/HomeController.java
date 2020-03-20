package com.aplinotech.cadastrocliente.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aplinotech.cadastrocliente.service.impl.SetupServiceImpl;

@Controller
public class HomeController {
	
	@Autowired
	private SetupServiceImpl setupServiceImpl;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		
		if (setupServiceImpl.sistemaExpirou()) 
			return "login/expirado";
		
		return "login/home";
		
	}

}
