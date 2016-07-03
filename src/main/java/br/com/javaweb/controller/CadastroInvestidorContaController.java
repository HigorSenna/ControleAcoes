package br.com.javaweb.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import br.com.javaweb.model.ContaBancaria;
import br.com.javaweb.model.Investidor;
import br.com.javaweb.service.InvestidorService;
import br.com.javaweb.utils.MessagesAndRedirect;
import br.com.javaweb.utils.Session;

@ManagedBean
@ViewScoped
public class CadastroInvestidorContaController {
	private ContaBancaria contaBancaria = new ContaBancaria();
	private Investidor investidor = new Investidor();
	private InvestidorService investidorService = new InvestidorService();
	
	@PostConstruct
	public void init(){
		investidor = new Investidor();
		buscarInvestidorSessao();
	}
	
	private void buscarInvestidorSessao(){	
		try {
			investidor = (Investidor) Session.pegarSessao();			
		} catch (Exception e) {
			MessagesAndRedirect.redirecionarPara("login.xhtml");
		}
	}

	public void salvar() {
		try {
			investidor.setIdConta(contaBancaria);
			investidorService.salvar(investidor);
			MessagesAndRedirect.exibirMensagemSucessoRedirect("Registro inserido com sucesso!", "principal.xhtml");
		} catch (Exception ex) {
			ex.getMessage();// erro ao salvar ou ao redirecionar
			MessagesAndRedirect.exibirMensagemErroRedirect("Registro não Inserido!", "principal.xhtml");
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
