package br.com.javaweb.controller;

import java.io.Serializable;

import br.com.javaweb.model.Acao;
import br.com.javaweb.model.Investidor;
import br.com.javaweb.transacoes.model.Compra;

public class CadastroCompraController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Compra compra;
	private Investidor investidor;
	private Acao acao;

	public void comprarAcao(){
		
	}
	
	public Compra getCompra() {
		return compra;
	}
	public void setCompra(Compra compra) {
		this.compra = compra;
	}
	public Investidor getInvestidor() {
		return investidor;
	}
	public void setInvestidor(Investidor investidor) {
		this.investidor = investidor;
	}
	public Acao getAcao() {
		return acao;
	}
	public void setAcao(Acao acao) {
		this.acao = acao;
	}
}
