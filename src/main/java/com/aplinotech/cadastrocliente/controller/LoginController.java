package com.aplinotech.cadastrocliente.controller;

import com.aplinotech.cadastrocliente.model.Produto;
import com.aplinotech.cadastrocliente.model.Setup;
import com.aplinotech.cadastrocliente.model.Usuario;
import com.aplinotech.cadastrocliente.model.dto.AtivacaoDTO;
import com.aplinotech.cadastrocliente.repository.UserRepository;
import com.aplinotech.cadastrocliente.service.EmailService;
import com.aplinotech.cadastrocliente.service.impl.SetupServiceImpl;
import com.aplinotech.cadastrocliente.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
public class LoginController {
	
	private static final String CODE_ACTIVE = "890531";
	
	@Autowired
	private SetupServiceImpl setupServiceImpl;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmailService emailService;


	@GetMapping("/login")
	public String login(@AuthenticationPrincipal User user) {
		return "login/login";
	}

	@GetMapping("/cadastrese/form")
	public ModelAndView cadastreseForm() {

		ModelAndView mv = new ModelAndView("login/cadastrese");
		mv.addObject("usuario", new Usuario());

		return mv;
	}

	@PostMapping("/cadastrese")
	public ModelAndView cadastrese(@ModelAttribute(value = "usuario") Usuario usuario) {
		usuario.setDataCadastro(new Date());
		usuario.setEmail(usuario.getUsername());
		usuario.setStatus("I");
		usuario.setToken(Util.geraToken());
		userRepository.save(usuario);
		userRepository.insereUsuarioRole(usuario.getId());
		emailService.confirmarCadastro(usuario);

		ModelAndView mv = new ModelAndView("login/login");
		mv.addObject("msgSucesso", "Enviamos um link de ativação da conta para seu endereço de e-mail.");
		return mv;
	}

	@GetMapping("/ativar")
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

	@PostMapping("/ativar")
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

	@GetMapping("/confirmaCadastro/{token}")
	public ModelAndView confirmaCadastro(@PathVariable("token") String token) {

		ModelAndView mv = new ModelAndView("login/login");
		Usuario usuario = userRepository.findByToken(token);

		if ( usuario != null ) {
			usuario.setStatus("A");
			userRepository.save(usuario);
			mv.addObject("msgSucesso", "Conta ativa com sucesso!");

		} else {
			mv.addObject("msgErro", "Link inválido.");
		}

		return mv;

	}
	
}