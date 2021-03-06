package com.aplinotech.cadastrocliente.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.aplinotech.cadastrocliente.model.*;
import com.aplinotech.cadastrocliente.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

	@Autowired
	private UserService userService;


	@GetMapping("/entrada")
	public ModelAndView entrada(){
		ModelAndView mv = new ModelAndView("produto/entrada");
		mv.addObject("dto", new PesquisarProdutoDTO());
		mv.addObject("produto", new Produto());
		return mv;
	}
	
	@PostMapping("/entrada/pesquisar")
	public ModelAndView entradaPesquisar(@ModelAttribute("dto") PesquisarProdutoDTO dto, HttpServletRequest req) {
		Usuario usuario = userService.findByUsername(req.getRemoteUser());
		Produto produto = produtoServiceImpl.findByCodigoAndActive(dto.getCodigoProduto(), usuario);
		ModelAndView mv = new ModelAndView("produto/entrada");
		mv.addObject("dto", new PesquisarProdutoDTO());
		mv.addObject("produto", produto == null ? new Produto() : produto);
		return mv;
	}
	
	@PostMapping("/entrada/salvar")
	public ModelAndView entradaSalvar(@ModelAttribute(value = "produto") Produto produto, HttpServletRequest req) {

		Usuario usuario = userService.findByUsername(req.getRemoteUser());

		Produto produtoBanco = produtoServiceImpl.findById(produto.getId());
		produtoBanco.setValorVendaUnitario(produto.getValorVendaUnitario());
		produtoBanco.setCustoUnitario(produto.getCustoUnitario());
		produtoBanco.setQuantidadeTotal(produto.getQtdParaBaixa() + produtoBanco.getQuantidadeTotal());
		
		produtoServiceImpl.saveOrUpdate(produtoBanco);
		
		Entrada entrada = Entrada.builder()
				.custoUnitario(produto.getCustoUnitario())
			    .valorVendaUnitario(produto.getValorVendaUnitario())
				.produto(produto)
				.quantidade(produto.getQtdParaBaixa())
				.data(new Date())
				.usuario(usuario).build();
		
		entradaServiceImpl.saveOrUpdate(entrada);
		
		ModelAndView mv = new ModelAndView("produto/entrada");
		mv.addObject("dto", new PesquisarProdutoDTO());
 		mv.addObject("produto", new Produto());
 		mv.addObject("mensagem", "Entrada de Estoque efetuada com sucesso!");
 		return mv;
	}
	
	@GetMapping("/removeProdutoBaixa/{id}")
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
	
	@GetMapping("/registrarBaixa")
	public ModelAndView registrarBaixa(HttpSession session, HttpServletRequest req) {

		Usuario usuario = userService.findByUsername(req.getRemoteUser());

		Baixa baixa = (Baixa) session.getAttribute("baixa");
		baixa.setData(new Date());	
		
		baixaServiceImpl.saveOrUpdate(baixa);
			
		for ( Produto produto : baixa.getProdutos() ) {
			
			ItemBaixa item = ItemBaixa.builder()
					.produto(produto)
					.quantidade(produto.getQtdParaBaixa())
					.baixa(baixa)
					.valorUnitario(produto.getValorVendaUnitario())
					.usuario(usuario).build();
			
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
	
	@PostMapping("/baixa/add")
	public String baixaAddProd(@ModelAttribute("produto") Produto produto, ModelMap modelMap, HttpSession session) {
		
		List<Produto> list = new ArrayList<>();
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
	
	@PostMapping("/pesquisar")
	public String baixa(@ModelAttribute("dto") PesquisarProdutoDTO dto, ModelMap modelMap, HttpSession session,
						HttpServletRequest req) {

		Usuario usuario = userService.findByUsername(req.getRemoteUser());

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
		
		Produto produto = produtoServiceImpl.findByCodigoAndActive(dto.getCodigoProduto(), usuario);
		
		// TODO retorna msg de erro caso nao encontre o produto
		
		modelMap.addAttribute("produtosBaixa", baixa.getProdutos());
		modelMap.addAttribute("dto", new PesquisarProdutoDTO());
		modelMap.addAttribute("produto", produto == null ? new Produto() : produto);
		modelMap.addAttribute("baixa", baixa);
		return "produto/baixa";
		
	}
	
	@GetMapping("/retorna/produto/{codigo}")
	public String retornaProdutoPesquisadoPorNome(@PathVariable(value = "codigo") String codigo, ModelMap modelMap,
												  HttpSession session, HttpServletRequest req) {

		Usuario usuario = userService.findByUsername(req.getRemoteUser());
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
		
		Produto produto = produtoServiceImpl.findByCodigoAndActive(codigo, usuario);
		
		modelMap.addAttribute("produtosBaixa", baixa.getProdutos());
		modelMap.addAttribute("dto", new PesquisarProdutoDTO());
		modelMap.addAttribute("produto", produto);
		modelMap.addAttribute("baixa", baixa);
		
		return "produto/baixa";
		
	}

	@GetMapping("/retorna/produto/entrada/{codigo}")
	public ModelAndView retornaProdutoEntradaPesquisadoPorNome(@PathVariable(value = "codigo") String codigo, ModelMap modelMap,
												  HttpSession session, HttpServletRequest req) {
		Usuario usuario = userService.findByUsername(req.getRemoteUser());
		Produto produto = produtoServiceImpl.findByCodigoAndActive(codigo, usuario);
		ModelAndView mv = new ModelAndView("produto/entrada");
		mv.addObject("dto", new PesquisarProdutoDTO());
		mv.addObject("produto", produto == null ? new Produto() : produto);
		return mv;
	}

	@GetMapping("/baixa")
	public String baixa(ModelMap modelMap, HttpSession session) {
		
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
	
	@GetMapping(value = "/consultar/produto/nome/form")
	public ModelAndView consultaProdutoForm() {
		ModelAndView mv = new ModelAndView("estoque/listar");
		mv.addObject("produtos", new ArrayList<Produto>());
	    mv.addObject("dto", new PesquisarProdutoDTO());		
		return mv;
	}

	@GetMapping(value = "/entrada/consultar/produto/nome/form")
	public ModelAndView consultaProdutoEntradaForm() {
		ModelAndView mv = new ModelAndView("estoque/listarEntrada");
		mv.addObject("produtos", new ArrayList<Produto>());
		mv.addObject("dto", new PesquisarProdutoDTO());
		return mv;
	}

	@PostMapping("/consultar/produto/nome")
	public String consultaProduto(@ModelAttribute("dto") PesquisarProdutoDTO dto, ModelMap modelMap, HttpServletRequest req) {
		Usuario usuario = userService.findByUsername(req.getRemoteUser());
		if ( !"".equals(dto.getNome()) ) {
			modelMap.addAttribute("produtos", produtoServiceImpl.findByNome(dto.getNome(), usuario.getId()));
		} else {
			modelMap.addAttribute("produtos", produtoServiceImpl.findAllActiveByUser(usuario.getId()));
		}
	    modelMap.addAttribute("dto", new PesquisarProdutoDTO());
		return "estoque/listar";
	}

	@PostMapping("/entrada/consultar/produto/nome")
	public String consultaProdutoEntrada(@ModelAttribute("dto") PesquisarProdutoDTO dto, ModelMap modelMap, HttpServletRequest req) {
		Usuario usuario = userService.findByUsername(req.getRemoteUser());
		if ( !"".equals(dto.getNome()) ) {
			modelMap.addAttribute("produtos", produtoServiceImpl.findByNome(dto.getNome(), usuario.getId()));
		} else {
			modelMap.addAttribute("produtos", produtoServiceImpl.findAllActiveByUser(usuario.getId()));
		}
		modelMap.addAttribute("dto", new PesquisarProdutoDTO());
		return "estoque/listarEntrada";
	}


}
