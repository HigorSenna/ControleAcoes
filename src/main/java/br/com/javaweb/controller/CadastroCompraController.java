package br.com.javaweb.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.javaweb.model.Acao;
import br.com.javaweb.model.Investidor;
import br.com.javaweb.service.CompraAcaoService;
import br.com.javaweb.transacoes.model.Compra;
import br.com.javaweb.utils.ConnectorURL;
import br.com.javaweb.utils.Session;

@ManagedBean
@ViewScoped
public class CadastroCompraController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Compra compra;
	private Investidor investidor;
	private Acao acao;
	private CompraAcaoService compraAcaoService;	
	List<Acao> acoes = new ArrayList<>();
	
	@PostConstruct
	public void init(){
		try {
			acoes = ConnectorURL.conectarNaUrlPegandoValoresDoWebService("http://cotacao.davesmartins.com.br/webCotacao/?cod=VALE5;PETR4;ITSA3;BBDC4;ABEV3;");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public List<Acao> getAcoes() throws Exception {
		return acoes;
	}

	public void comprarAcao(Acao acao){
		compraAcaoService = new CompraAcaoService();
		compra = new Compra();
		investidor = buscarInvestidorSessao(); // buscar o investidor antes de colocar na sessao
		if(investidor.getComprasList() == null){
			List<Compra> compras = new ArrayList<>();
			compra.setNomeAcao(acao.getNomeAcao());			
			compra.setValorFinalAcao(Double.parseDouble(acao.getValorUltimaCotacao().replaceAll(",", ".")));
			compras.add(compra);
			compraAcaoService.comprarAcao(compras, investidor,acao.getQuantidade());		
		}
		else{
			compra.setNomeAcao(acao.getNomeAcao());			
			compra.setValorFinalAcao(Double.parseDouble(acao.getValorUltimaCotacao().replaceAll(",", ".")));
			investidor.getComprasList().add(compra);
			compraAcaoService.comprarAcao(investidor.getComprasList(), investidor,acao.getQuantidade());	
		}
	}
	
	public Investidor buscarInvestidorSessao(){
		 return (Investidor) Session.pegarSessao();
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
