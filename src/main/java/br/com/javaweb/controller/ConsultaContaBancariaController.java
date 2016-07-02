package br.com.javaweb.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.javaweb.model.Investidor;
import br.com.javaweb.service.ContaBancariaService;
import br.com.javaweb.service.InvestidorService;
import br.com.javaweb.utils.MessagesAndRedirect;
import br.com.javaweb.utils.Session;

@ManagedBean
@ViewScoped
public class ConsultaContaBancariaController {
	
	private Investidor investidor;
	private Investidor investidorSessao;
	private InvestidorService investidorService;
	private ContaBancariaService contaBancariaService;
	private double valorDepositar;
	
	@PostConstruct
	public void init(){
		investidor = new Investidor();
		investidor = buscarInvestidorBanco();
	}
	
	public void depositar(){
		contaBancariaService = new ContaBancariaService();
		try {
			contaBancariaService.depositar(investidor.getIdConta(), valorDepositar);
			MessagesAndRedirect.exibirMensagemSucessoRedirect("Saldo atualizado com sucesso!!", "contaBancaria.xhtml");
		} catch (Exception e) {
			MessagesAndRedirect.exibirMensagemErroRedirect("Erro ao depositar", "contaBancaria.xhtml");
		}
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

	public double getValorDepositar() {
		return valorDepositar;
	}

	public void setValorDepositar(double valorDepositar) {
		this.valorDepositar = valorDepositar;
	}
}

