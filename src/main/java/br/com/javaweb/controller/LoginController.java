
package br.com.javaweb.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.javaweb.model.Investidor;
import br.com.javaweb.service.InvestidorService;
import br.com.javaweb.utils.MessagesAndRedirect;
import br.com.javaweb.utils.Session;

@ManagedBean
@ViewScoped
public class LoginController {

	private Investidor investidor = new Investidor();
	private InvestidorService investidorService = new InvestidorService();
	
	public void fecharSessao(){
		Session.fecharSessao();
		MessagesAndRedirect.exibirMensagemErroRedirect("Você deslogou!", "../login.xhtml");;
	}
	public void validarSessao(String paginaRedirecionar){
		if (Session.existeSessao("user")) {			 
			MessagesAndRedirect.redirecionarPara(paginaRedirecionar);
		} else {
			MessagesAndRedirect.exibirMensagemErroRedirect("Você não esta logado", "login.xhtml");
		}
	}

	
	public void validarLogin() {
		try {
			investidorService.verificarLogin(investidor.getLogin(), investidor.getSenha());
			investidor = investidorService.buscarInvestidorLoginSenha(investidor.getLogin(), investidor.getSenha());
			Session.criarSessao("user", investidor);
			MessagesAndRedirect.redirecionarPara("pages/principal.xhtml");		

		} catch (Exception e) {
			e.getMessage();
			investidor = new Investidor();
			MessagesAndRedirect.exibirMensagemErroRedirect("Login Inválido", "login.xhtml");
		}
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
