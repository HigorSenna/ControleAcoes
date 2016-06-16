package br.com.javaweb.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.javaweb.model.Acao;
import br.com.javaweb.model.Investidor;
import br.com.javaweb.service.VendaAcaoService;
import br.com.javaweb.transacoes.model.HistoricoTransacao;
import br.com.javaweb.transacoes.model.Venda;
import br.com.javaweb.utils.ConnectorURL;
import br.com.javaweb.utils.MessagesAndRedirect;
import br.com.javaweb.utils.Session;

@ManagedBean
@ViewScoped
public class ConsultaVendaController {
	
	private Investidor investidor;
	private List<Acao> acoes;
	private VendaAcaoService vendaAcaoService;
	private List<HistoricoTransacao> historico;
	private int quantidadeVenda;
	private Venda venda;
	
	@PostConstruct
	public void init(){
		historico = buscarHistoricosComValoresVenda();
	}
	
	public void realizarVenda(HistoricoTransacao historico){
		vendaAcaoService = new VendaAcaoService();
		investidor = new Investidor();
		investidor = buscarInvestidorSessao();
		venda = new Venda();
		venda.setIdInvestidor(investidor);	
		
		try {
			vendaAcaoService.inserirVenda(venda, quantidadeVenda,historico);
			MessagesAndRedirect.exibirMensagemSucessoRedirect("Venda realizada com sucesso!!", "venderAcoes.xhtml");
		} catch (Exception e) {
			MessagesAndRedirect.exibirMensagemErroRedirect("Erro ao vender!", "venderAcoes.xhtml");
		}
	}
	
	public List<HistoricoTransacao> buscarHistoricosComValoresVenda(){
		historico = new ArrayList<>();
		investidor = new Investidor();
		acoes = new ArrayList<>();
		vendaAcaoService = new VendaAcaoService();		
		
		investidor = buscarInvestidorSessao();
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

	public int getQuantidadeVenda() {
		return quantidadeVenda;
	}

	public void setQuantidadeVenda(int quantidadeVenda) {
		this.quantidadeVenda = quantidadeVenda;
	}
}
