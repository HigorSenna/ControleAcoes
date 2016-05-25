package br.com.javaweb.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import br.com.javaweb.model.ContaBancaria;
import br.com.javaweb.model.Investidor;
import br.com.javaweb.service.InvestidorService;

@ManagedBean
@ViewScoped
public class CadastroInvestidorContaController {
	private ContaBancaria contaBancaria = new ContaBancaria();
	private Investidor investidor = new Investidor();
	private InvestidorService investidorService = new InvestidorService();
	
	public void salvar(){
		try{
//			investidor.setIdConta(contaBancaria);
			investidorService.salvar(investidor);	
		}
		catch(Exception ex){			
			ex.getMessage();
		}
			
	}

	public ContaBancaria getContaBancaria() {
		return contaBancaria;
	}

	public void setContaBancaria(ContaBancaria contaBancaria) {
		this.contaBancaria = contaBancaria;
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
