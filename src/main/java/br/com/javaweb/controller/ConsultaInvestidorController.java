package br.com.javaweb.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import br.com.javaweb.model.Investidor;
import br.com.javaweb.service.InvestidorService;

@ManagedBean
@ViewScoped
public class ConsultaInvestidorController implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Investidor investidor = new Investidor();
	private InvestidorService investidorService = new InvestidorService();	
		
	public List<Investidor> buscarInvestidores(){
		return investidorService.buscarInvestidores();
	}
	
	public Investidor getInvestidor() {
		return investidor;
	}
	public void setInvestidor(Investidor investidor) {
		this.investidor = investidor;
	}
	public InvestidorService getInvestidorService() {
		return investidorService;
	}
	public void setInvestidorService(InvestidorService investidorService) {
		this.investidorService = investidorService;
	}
}
