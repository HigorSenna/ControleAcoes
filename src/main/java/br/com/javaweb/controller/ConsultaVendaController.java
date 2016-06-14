package br.com.javaweb.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.javaweb.model.Acao;
import br.com.javaweb.model.Investidor;
import br.com.javaweb.service.VendaAcaoService;
import br.com.javaweb.transacoes.model.HistoricoTransacao;
import br.com.javaweb.utils.ConnectorURL;
import br.com.javaweb.utils.Session;

@ManagedBean
@ViewScoped
public class ConsultaVendaController {
	
	private Investidor investidor;
	private List<Acao> acoes;
	private VendaAcaoService vendaAcaoService;
	private List<HistoricoTransacao> historico;
	
	@PostConstruct
	public void init(){
		historico = buscarHistoricosComValoresVenda();
	}
	
	public List<HistoricoTransacao> buscarHistoricosComValoresVenda(){
		investidor = buscarInvestidorSessao();
		vendaAcaoService = new VendaAcaoService();
		acoes = buscarTodasAcoesWebService();
		return vendaAcaoService.verificarValorVendaCadaAcaoInvestidor(investidor, acoes);
	}
	
	private Investidor buscarInvestidorSessao(){
		return (Investidor) Session.pegarSessao();
	}
	
	private List<Acao> buscarTodasAcoesWebService(){
		try {
			return ConnectorURL.conectarNaUrlPegandoValoresDoWebService("http://cotacao.davesmartins.com.br/webCotacao/?cod=VALE5;PETR4;ITSA3;BBDC4;ABEV3;");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public List<Acao> getAcoes() {
		return acoes;
	}

	public List<HistoricoTransacao> getHistorico() {
		return historico;
	}
}
