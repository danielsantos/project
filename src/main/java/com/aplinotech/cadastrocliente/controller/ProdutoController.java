package com.aplinotech.cadastrocliente.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.aplinotech.cadastrocliente.model.Produto;
import com.aplinotech.cadastrocliente.model.dto.PesquisarProdutoDTO;
import com.aplinotech.cadastrocliente.service.impl.ProdutoServiceImpl;
import com.aplinotech.cadastrocliente.service.impl.SetupServiceImpl;


@Controller
@RequestMapping("/produto") 
public class ProdutoController {	
	
	@Autowired
	private ProdutoServiceImpl produtoServiceImpl;
	
	@Autowired
	private SetupServiceImpl setupServiceImpl;
	
	
	@RequestMapping(value = "/novo", method = RequestMethod.GET)
	public ModelAndView novo(){
		
		if (setupServiceImpl.sistemaExpirou()) 
			return new ModelAndView("login/expirado");
		
		ModelAndView mv = new ModelAndView("produto/novo");
		mv.addObject("produto", new Produto());
		return mv;
		
	}
	
	@RequestMapping(value = "/salvar", method = RequestMethod.POST)
	public ModelAndView salvar(@ModelAttribute(value = "produto") Produto produto, Errors errors, ModelMap modelMap){
		
		if (setupServiceImpl.sistemaExpirou()) 
			return new ModelAndView("login/expirado");
		
		ModelAndView mv = new ModelAndView("produto/novo");
		
		if (produto.getCodigo() == null || "".equals(produto.getCodigo())) {
			modelMap.addAttribute("produto", produto);
			modelMap.addAttribute("msgError", "O campo Código é obrigatório.");
			return mv;
		}
		
		Produto prod = produtoServiceImpl.findByCodigo(produto.getCodigo());
		if (prod != null) {
			modelMap.addAttribute("produto", produto);
			modelMap.addAttribute("msgError", "O Código '" + produto.getCodigo() + "' já está cadastrado para outro Produto.");
			return mv;
		}
		
		if(errors.hasErrors()){
			modelMap.addAttribute("produto", produto);
			return mv;
		} else {
			produto.setStatus("A"); // TODO criar um enum
			produtoServiceImpl.saveOrUpdate(produto);
			mv.addObject("mensagem", "Produto cadastrado com sucesso!");
			modelMap.addAttribute("produto", new Produto());
		}
		return mv;
		
	}
	
	@RequestMapping(value = "/atualizar/{codigo}", method = RequestMethod.GET)
	public ModelAndView alterar(@PathVariable(value = "codigo") String codigo){
		
		if (setupServiceImpl.sistemaExpirou()) 
			return new ModelAndView("login/expirado");
		
		ModelAndView mv = new ModelAndView("produto/atualizar");
		mv.addObject("produto", produtoServiceImpl.findByCodigoAndActive(codigo));
		return mv;
		
	}
	
	@RequestMapping(value = "/alterar", method = RequestMethod.POST)
	public ModelAndView atualizar(@ModelAttribute(value = "produto") Produto produto, Errors errors, ModelMap modelMap) {
		
		if (setupServiceImpl.sistemaExpirou()) 
			return new ModelAndView("login/expirado");
		
		ModelAndView mv = new ModelAndView("produto/atualizar");
		modelMap.addAttribute("produto", produto);
		
		if(errors.hasErrors()){
			return mv;
		} else {
			produtoServiceImpl.saveOrUpdate(produto);
			mv.addObject("mensagem", "Dados atualizados com sucesso!");
		}
		
		return mv;
		
	}
	
	@RequestMapping(value = "/excluir/{codigo}", method =RequestMethod.GET)
	public String excluir(@PathVariable(value = "codigo") String codigo, ModelMap modelMap){
		
		if (setupServiceImpl.sistemaExpirou()) 
			return "redirect:/login/expirado";
		
		produtoServiceImpl.deleteLogic(codigo);
		return "redirect:/produto/listar";
		
	}
	
	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public String listar(ModelMap modelMap) {
		
		if (setupServiceImpl.sistemaExpirou()) 
			return "login/expirado";
		
		modelMap.addAttribute("produtos", produtoServiceImpl.findAllActive());
		modelMap.addAttribute("dto", new PesquisarProdutoDTO());
		return "produto/listar";
		
	}
	
	@RequestMapping(value = "/visualizar/{codigo}", method = RequestMethod.GET)
	public String visualizar(@PathVariable(value = "codigo") String codigo, ModelMap modelMap){
		
		if (setupServiceImpl.sistemaExpirou()) 
			return "login/expirado";
		
		modelMap.addAttribute("produto", produtoServiceImpl.findByCodigoAndActive(codigo));
		return "produto/visualizar";
		
	}	
	
	@RequestMapping(value = "/consultar", method = RequestMethod.POST)
	public String consultaProduto(@ModelAttribute("dto") PesquisarProdutoDTO dto, ModelMap modelMap, HttpSession session) {
		
		if (setupServiceImpl.sistemaExpirou()) 
			return "login/expirado";
		
		if ( !"".equals(dto.getNome()) ) {
			
			modelMap.addAttribute("produtos", produtoServiceImpl.findByNome(dto. getNome()));
			
		} else if ( !"".equals(dto.getCodigoProduto()) ) {
			
			List<Produto> produtos = new ArrayList<Produto>();
			Produto produto = produtoServiceImpl.findByCodigo(dto.getCodigoProduto());
			
			if (produto != null)
				produtos.add(produto);
			
			modelMap.addAttribute("produtos", produtos);
			
		} else { 
			modelMap.addAttribute("produtos", produtoServiceImpl.findAllActive());
		}
			
	    modelMap.addAttribute("dto", new PesquisarProdutoDTO());		
		return "produto/listar";
		
	}

}
