
package br.com.javaweb.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import br.com.javaweb.model.Investidor;
import br.com.javaweb.service.InvestidorService;
import br.com.javaweb.utils.Session;

@ManagedBean
@ViewScoped
public class LoginController {

	private Investidor investidor = new Investidor();
	private InvestidorService investidorService = new InvestidorService();

	public String validarLogin() {
		try {
			investidorService.verificarLogin(investidor.getLogin(), investidor.getSenha());
			Session.criarSessao("user", investidor.getLogin());
			return "/pages/principal.xhtml";
			/* criar sessao */
			// FacesContext context = FacesContext.getCurrentInstance();
			// HttpSession session = (HttpSession)
			// context.getExternalContext().getSession(true);
			// session.setAttribute("user", investidor.getLogin());

		} catch (Exception e) {
			// e.printStackTrace();
			investidor = new Investidor();
			return "login.xhtml";
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
