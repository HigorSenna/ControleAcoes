package br.com.javaweb.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.javaweb.model.Investidor;
import br.com.javaweb.service.InvestidorService;
import br.com.javaweb.transacoes.model.Compra;
import br.com.javaweb.utils.Session;

@ManagedBean
@ViewScoped
public class ConsultaCompraController {

	private Investidor investidor;
	private InvestidorService investidorService;
	
	@PostConstruct
	public void init(){
		investidor = buscarInvestidor();
	}
	
	public List<Compra> buscarComprasInvestidor(){		
		return investidor.getComprasList();
	}
	
	private Investidor buscarInvestidorSessao(){
		return (Investidor) Session.pegarSessao();		
	}
	
	private Investidor buscarInvestidor(){
		investidor = buscarInvestidorSessao();
		investidorService = new InvestidorService();
		return investidorService.buscarInvestidorLoginSenha(investidor.getLogin(), investidor.getSenha());
	}
}
