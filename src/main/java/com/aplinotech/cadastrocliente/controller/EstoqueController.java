package com.aplinotech.cadastrocliente.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.aplinotech.cadastrocliente.model.Baixa;
import com.aplinotech.cadastrocliente.model.Entrada;
import com.aplinotech.cadastrocliente.model.ItemBaixa;
import com.aplinotech.cadastrocliente.model.Produto;
import com.aplinotech.cadastrocliente.model.dto.PesquisarProdutoDTO;
import com.aplinotech.cadastrocliente.service.impl.BaixaServiceImpl;
import com.aplinotech.cadastrocliente.service.impl.EntradaServiceImpl;
import com.aplinotech.cadastrocliente.service.impl.ItemBaixaServiceImpl;
import com.aplinotech.cadastrocliente.service.impl.ProdutoServiceImpl;
import com.aplinotech.cadastrocliente.service.impl.SetupServiceImpl;

@Controller
@RequestMapping("/estoque") 
public class EstoqueController {
	
	@Autowired
	private SetupServiceImpl setupServiceImpl;
	
	@Autowired
	private ProdutoServiceImpl produtoServiceImpl;
	
	@Autowired
	private EntradaServiceImpl entradaServiceImpl;
	
	@Autowired
	private BaixaServiceImpl baixaServiceImpl;

	@Autowired
	private ItemBaixaServiceImpl itemBaixaServiceImpl;
	
	
	@RequestMapping(value = "/entrada")
	public ModelAndView entrada(){
		
		if (setupServiceImpl.sistemaExpirou()) 
			return new ModelAndView("login/expirado");
		
		ModelAndView mv = new ModelAndView("produto/entrada");
		mv.addObject("dto", new PesquisarProdutoDTO());
		mv.addObject("produto", new Produto());
		return mv;
		
	}
	
	@RequestMapping(value = "/entrada/pesquisar", method = RequestMethod.POST)
	public ModelAndView entradaPesquisar(@ModelAttribute("dto") PesquisarProdutoDTO dto) {
		
		if (setupServiceImpl.sistemaExpirou()) 
			return new ModelAndView("login/expirado");
		
		Produto produto = produtoServiceImpl.findByCodigoAndActive(dto.getCodigoProduto());
		ModelAndView mv = new ModelAndView("produto/entrada");
		mv.addObject("dto", new PesquisarProdutoDTO());
		mv.addObject("produto", produto);
		return mv;
		
	}
	
	@RequestMapping(value = "/entrada/salvar", method = RequestMethod.POST)
	public ModelAndView entradaSalvar(@ModelAttribute(value = "produto") Produto produto) {
		
		if (setupServiceImpl.sistemaExpirou()) 
			return new ModelAndView("login/expirado");

		Produto produtoBanco = produtoServiceImpl.findById(produto.getId());
		produtoBanco.setValorVendaUnitario(produto.getValorVendaUnitario());
		produtoBanco.setCustoUnitario(produto.getCustoUnitario());
		produtoBanco.setQuantidadeTotal(produto.getQtdParaBaixa() + produtoBanco.getQuantidadeTotal());
		
		produtoServiceImpl.saveOrUpdate(produtoBanco);
		
		Entrada entrada = new Entrada();
		entrada.setCustoUnitario(produto.getCustoUnitario());
		entrada.setValorVendaUnitario(produto.getValorVendaUnitario());
		entrada.setProduto(produto);
		entrada.setQuantidade(produto.getQtdParaBaixa());
		entrada.setData(new Date());
		
		entradaServiceImpl.saveOrUpdate(entrada);
		
		ModelAndView mv = new ModelAndView("produto/entrada");
		mv.addObject("dto", new PesquisarProdutoDTO());
 		mv.addObject("produto", new Produto());
 		mv.addObject("mensagem", "Entrada de Estoque efetuada com sucesso!");
 		return mv;
 		
	}
	
	@RequestMapping(value = "/removeProdutoBaixa/{id}", method = RequestMethod.GET)
	public ModelAndView removeProdutoBaixa(@PathVariable(value = "id") Long id, HttpSession session) {
		
		if (setupServiceImpl.sistemaExpirou()) 
			return new ModelAndView("login/expirado");

		Baixa baixa = (Baixa) session.getAttribute("baixa");
		List<Produto> novaLista = new ArrayList<Produto>();

		for (Produto p : baixa.getProdutos()) {
			if (!p.getId().equals(id)) {
				novaLista.add(p);
			}
		}
		
		BigDecimal total = BigDecimal.ZERO;
		if (!novaLista.isEmpty()) {
			for (Produto p : novaLista) {
				total = p.getValorTotal().add(total);
			}
		}
		
		baixa.setValorTotal(total);
		baixa.setProdutos(novaLista);
		session.setAttribute("baixa", baixa);

		ModelAndView mv = new ModelAndView("produto/baixa");
		mv.addObject("produtosBaixa", baixa.getProdutos());
		mv.addObject("produto", new Produto());
		mv.addObject("dto", new PesquisarProdutoDTO());
		mv.addObject("baixa", baixa);
		return mv;
		
	}
	
	@RequestMapping(value = "/registrarBaixa", method = RequestMethod.GET)
	public ModelAndView registrarBaixa(HttpSession session) {
		
		if (setupServiceImpl.sistemaExpirou()) 
			return new ModelAndView("login/expirado");

		Baixa baixa = (Baixa) session.getAttribute("baixa");
		baixa.setData(new Date());	
		
		baixaServiceImpl.saveOrUpdate(baixa);
			
		for ( Produto produto : baixa.getProdutos() ) {
			
			ItemBaixa item = new ItemBaixa();
			item.setProduto(produto);
			item.setQuantidade(produto.getQtdParaBaixa());
			item.setBaixa(baixa);
			item.setValorUnitario(produto.getValorVendaUnitario());
			
			itemBaixaServiceImpl.saveOrUpdate(item);
			
			Produto prod = produtoServiceImpl.findById(produto.getId());
			prod.setQuantidadeTotal(prod.getQuantidadeTotal() - produto.getQtdParaBaixa());
			produtoServiceImpl.saveOrUpdate(prod);
			
		}

		session.setAttribute("baixa", null);
			
		ModelAndView mv = new ModelAndView("produto/baixa");
		mv.addObject("mensagem", "Baixa efetuada com sucesso!");
		mv.addObject("produto", new Produto());
		mv.addObject("dto", new PesquisarProdutoDTO());
		mv.addObject("baixa", new Baixa());
		return mv;
		
	}
	
	@RequestMapping(value = "/baixa/add", method = RequestMethod.POST)
	public String baixaAddProd(@ModelAttribute("produto") Produto produto, ModelMap modelMap, HttpSession session) {
		
		if (setupServiceImpl.sistemaExpirou()) 
			return "login/expirado";
		
		List<Produto> list = new ArrayList<Produto>();
		Baixa baixa = new Baixa();
		
		if ( session.getAttribute("baixa") == null ) {

			session.setAttribute("baixa", baixa);
			
		} else {
			
			baixa = (Baixa) session.getAttribute("baixa");
			
			BigDecimal total = baixa.getValorTotal();

			if ( baixa.getProdutos() != null && !baixa.getProdutos().isEmpty() ) {
				
				for (Produto p : list) {
					total = p.getValorTotal().add(total);
				}
				
								
				if ( !baixa.getProdutos().contains(produto) ) {
					
					baixa.getProdutos().add(produto);
					
				} else {
					
					int pos = baixa.getProdutos().indexOf(produto);
					Integer qtdAtual = baixa.getProdutos().get(pos).getQtdParaBaixa();
					baixa.getProdutos().get(pos).setQtdParaBaixa( qtdAtual + produto.getQtdParaBaixa() );
					baixa.getProdutos().get(pos).setValorVendaUnitario(produto.getValorVendaUnitario());
					
				}
				
				total = produto.getValorTotal().add(total);
				
			} else {
				
				baixa.setProdutos(new ArrayList<Produto>());
				baixa.getProdutos().add(produto);
				total = produto.getValorTotal().add(total);
				
			}
			
			baixa.setValorTotal(total);
			session.setAttribute("baixa", baixa);
			
		}
		
		modelMap.addAttribute("produtosBaixa", baixa.getProdutos());
		modelMap.addAttribute("produto", new Produto());
		modelMap.addAttribute("dto", new PesquisarProdutoDTO());
		modelMap.addAttribute("baixa", baixa);
		return "produto/baixa";
		
	}
	
	@RequestMapping(value = "/pesquisar", method = RequestMethod.POST)
	public String baixa(@ModelAttribute("dto") PesquisarProdutoDTO dto, ModelMap modelMap, HttpSession session) {
		
		if (setupServiceImpl.sistemaExpirou()) 
			return "login/expirado";
		
		Baixa baixa = new Baixa();
		
		if ( session.getAttribute("baixa") == null ) {

			session.setAttribute("baixa", new Baixa());
			
		} else {
			
			BigDecimal total = BigDecimal.ZERO;
			baixa = (Baixa) session.getAttribute("baixa");
			
			if (baixa.getProdutos() != null && !baixa.getProdutos().isEmpty()) {
				for (Produto p : baixa.getProdutos()) {
					total = p.getValorTotal().add(total);
				}
			}
			
			baixa.setValorTotal(total);
			session.setAttribute("baixa", baixa);
			
		}
		
		Produto produto = produtoServiceImpl.findByCodigoAndActive(dto.getCodigoProduto());
		
		// TODO retorna msg de erro caso nao encontre o produto
		
		modelMap.addAttribute("produtosBaixa", baixa.getProdutos());
		modelMap.addAttribute("dto", new PesquisarProdutoDTO());
		modelMap.addAttribute("produto", produto);
		modelMap.addAttribute("baixa", baixa);
		return "produto/baixa";
		
	}
	
	@RequestMapping("/retorna/produto/{codigo}") 
	public String retornaProdutoPesquisadoPorNome(@PathVariable(value = "codigo") String codigo, ModelMap modelMap, HttpSession session) {
		
		if (setupServiceImpl.sistemaExpirou()) 
			return "login/expirado";
		
		Baixa baixa = new Baixa();
		
		if ( session.getAttribute("baixa") == null ) {

			session.setAttribute("baixa", new Baixa());
			
		} else {
			
			BigDecimal total = BigDecimal.ZERO;
			baixa = (Baixa) session.getAttribute("baixa");
			
			if (baixa.getProdutos() != null && !baixa.getProdutos().isEmpty()) {
				for (Produto p : baixa.getProdutos()) {
					total = p.getValorTotal().add(total);
				}
			}
			
			baixa.setValorTotal(total);
			session.setAttribute("baixa", baixa);
			
		}
		
		Produto produto = produtoServiceImpl.findByCodigoAndActive(codigo);
		
		modelMap.addAttribute("produtosBaixa", baixa.getProdutos());
		modelMap.addAttribute("dto", new PesquisarProdutoDTO());
		modelMap.addAttribute("produto", produto);
		modelMap.addAttribute("baixa", baixa);
		
		return "produto/baixa";
		
	}
	
	@RequestMapping(value = "/baixa", method = RequestMethod.GET)
	public String baixa(ModelMap modelMap, HttpSession session) {
		
		if (setupServiceImpl.sistemaExpirou()) 
			return "login/expirado";
		
		//List<Produto> list = new ArrayList<Produto>();
		Baixa baixa = new Baixa();
		
		if ( session.getAttribute("baixa") == null ) {

			session.setAttribute("baixa", baixa);
			
		} else {
			
			baixa = (Baixa) session.getAttribute("baixa");
			BigDecimal total = BigDecimal.ZERO;

			if ( baixa.getProdutos() != null && !baixa.getProdutos().isEmpty() ) {
				for (Produto p : baixa.getProdutos()) {
					total = p.getValorTotal().add(total);
				}
			}
			
			baixa.setValorTotal(total);
			session.setAttribute("baixa", baixa);
			
		}
		
		modelMap.addAttribute("produtos", produtoServiceImpl.findAll());
		modelMap.addAttribute("produtosBaixa", baixa.getProdutos());
		modelMap.addAttribute("dto", new PesquisarProdutoDTO());
		modelMap.addAttribute("produto", new Produto());
		modelMap.addAttribute("baixa", baixa);
		return "produto/baixa";
		
	}
	
	@RequestMapping(value = "/consultar/produto/nome/form")
	public ModelAndView consultaProdutoForm() {
		
		if (setupServiceImpl.sistemaExpirou()) 
			return new ModelAndView("login/expirado");
		
		ModelAndView mv = new ModelAndView("estoque/listar");
		mv.addObject("produtos", new ArrayList<Produto>());
	    mv.addObject("dto", new PesquisarProdutoDTO());		
		return mv;
		
	}
	
	@RequestMapping(value = "/consultar/produto/nome", method = RequestMethod.POST)
	public String consultaProduto(@ModelAttribute("dto") PesquisarProdutoDTO dto, ModelMap modelMap, HttpSession session) {
		
		if (setupServiceImpl.sistemaExpirou()) 
			return "login/expirado";
		
		if ( !"".equals(dto.getNome()) ) {
			
			modelMap.addAttribute("produtos", produtoServiceImpl.findByNome(dto. getNome()));
			
		} else { 

			modelMap.addAttribute("produtos", produtoServiceImpl.findAllActive());
			
		}
			
	    modelMap.addAttribute("dto", new PesquisarProdutoDTO());		
		return "estoque/listar";
		
	}

}
