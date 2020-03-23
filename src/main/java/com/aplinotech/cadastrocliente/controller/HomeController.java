package com.aplinotech.cadastrocliente.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aplinotech.cadastrocliente.service.impl.SetupServiceImpl;

@Controller
public class HomeController {
	
	@Autowired
	private SetupServiceImpl setupServiceImpl;
	
	@GetMapping("/")
	public String home() {
		return "login/home";
	}

}
