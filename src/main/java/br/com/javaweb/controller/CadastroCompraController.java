package br.com.javaweb.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.javaweb.model.Acao;
import br.com.javaweb.model.Investidor;
import br.com.javaweb.service.CompraAcaoService;
import br.com.javaweb.transacoes.model.Compra;

@ManagedBean
@ViewScoped
public class CadastroCompraController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Compra compra;
	private Investidor investidor;
	private Acao acao;
	private CompraAcaoService compraAcaoService;

	public void comprarAcao(Acao acao){
		compraAcaoService = new CompraAcaoService();
		compra = new Compra();
		investidor = new Investidor();// pegar da sessao
		if(investidor.getComprasList() == null){
			List<Compra> compras = new ArrayList<>();
			compra.setNomeAcao(acao.getNomeAcao());			
			compra.setValorFinalAcao(Double.parseDouble(acao.getValorUltimaCotacao().replaceAll(",", ".")));
			compraAcaoService.comprarAcao(compras, investidor,acao.getQuantidade());		
		}
		else{
			investidor.getComprasList().add(compra);
			compraAcaoService.comprarAcao(investidor.getComprasList(), investidor,acao.getQuantidade());	
		}
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
