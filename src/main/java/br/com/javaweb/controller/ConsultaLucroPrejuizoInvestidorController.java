package br.com.javaweb.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.javaweb.model.Investidor;
import br.com.javaweb.service.InvestidorService;
import br.com.javaweb.transacoes.model.Venda;
import br.com.javaweb.utils.Session;

@ManagedBean
@ViewScoped
public class ConsultaLucroPrejuizoInvestidorController {
	
	private Investidor investidor;
	private InvestidorService investidorService;
	
	@PostConstruct
	public void init(){
		investidor = buscarInvestidor();
	}
	
	private Investidor buscarInvestidor(){
		investidorService = new InvestidorService();
		investidor = buscarInvestidorSessao();
		return investidorService.buscarInvestidorLoginSenha(investidor.getLogin(), investidor.getSenha());
	}
	
	public List<Venda> buscarVendasInvestidor(){
		return investidor.getVendasList();
	}
	
	private Investidor buscarInvestidorSessao(){
		return (Investidor) Session.pegarSessao();		
	}	
}
