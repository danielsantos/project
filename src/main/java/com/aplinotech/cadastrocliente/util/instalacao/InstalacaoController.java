package com.aplinotech.cadastrocliente.util.instalacao;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.aplinotech.cadastrocliente.repository.SetupRepository;

@Controller
public class InstalacaoController {
	
	@Autowired
	private InstalacaoRepository repository;
	
	@Autowired
	private SetupRepository setupRepository;

	@RequestMapping(value = "/instalar")
	public ModelAndView instala() {

		ModelAndView mv = new ModelAndView("util/home");
		
		if ( setupRepository.findAll().isEmpty() ) {
		
			Calendar cal = new GregorianCalendar();
			cal.add(Calendar.DATE, 10);
			
			repository.insereSetup(cal.getTime());
			repository.insereUsuario();
			repository.insereRole();
			repository.insereUsuarioRole();
			
			mv.addObject("msgSucesso", "Instalação Concluída com Sucesso!");
			
		} else {

			mv.addObject("msgError", "Instalação já efetuada anteiormente");
			
		}
		
	 	return mv;
		
	}
	
}
