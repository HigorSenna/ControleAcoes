package br.com.javaweb.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import DAO.exceptions.NonexistentEntityException;
import br.com.javaweb.model.Investidor;
import br.com.javaweb.service.InvestidorService;
import br.com.javaweb.utils.MessagesAndRedirect;
import br.com.javaweb.utils.Session;

@ManagedBean
@ViewScoped
public class ConsultaInvestidorController implements Serializable {

	private static final long serialVersionUID = 1L;
	private Investidor investidor = new Investidor();
	private InvestidorService investidorService = new InvestidorService();
	
	@PostConstruct
	public void init(){
		investidor = new Investidor();
		investidor = buscarInvestidorSessao();
		validarPermissao();
	}
	
	private void validarPermissao(){
		if(!(investidor.getLogin().equalsIgnoreCase("admin") || investidor.getLogin().equalsIgnoreCase(("adminisrtrador")))){
			MessagesAndRedirect.redirecionarPara("fail.xhtml");
		}
	}
	
	private Investidor buscarInvestidorSessao(){
		return (Investidor)Session.pegarSessao();
	}
	
	public void excluirInvestidor(Investidor investidor) {
		try {
			investidorService.excluir(investidor.getIdInvestidor());
			MessagesAndRedirect.exibirMensagemSucessoRedirect("Investidor Excluido com sucesso!","consultaInvestidorCadastrado.xhtml");
		} catch (NonexistentEntityException ne) {
			MessagesAndRedirect.exibirMensagemErroRedirect("Investidor Inexistente","consultaInvestidorCadastrado.xhtml");
		}
	}

	public List<Investidor> buscarInvestidores() {
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
