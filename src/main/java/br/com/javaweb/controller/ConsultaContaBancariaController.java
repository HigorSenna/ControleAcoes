package br.com.javaweb.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.javaweb.model.Investidor;
import br.com.javaweb.service.InvestidorService;
import br.com.javaweb.utils.Session;

@ManagedBean
@ViewScoped
public class ConsultaContaBancariaController {
	
	private Investidor investidor;
	private Investidor investidorSessao;
	private InvestidorService investidorService;
	
	@PostConstruct
	public void init(){
		investidor = new Investidor();
		investidor = buscarInvestidorBanco();
	}
	
	private Investidor buscarInvestidorBanco(){		
		investidorSessao = buscarInvestidorSessao();
		investidorService = new InvestidorService();
		return investidorService.recuperarObjetoParaEdicao(investidorSessao.getIdInvestidor());
	}
	
	private Investidor buscarInvestidorSessao(){
		return (Investidor) Session.pegarSessao();
	}

	public Investidor getInvestidor() {
		return investidor;
	}

	public void setInvestidor(Investidor investidor) {
		this.investidor = investidor;
	}
}

