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
import br.com.javaweb.transacoes.model.HistoricoTransacao;
import br.com.javaweb.utils.ConnectorURL;
import br.com.javaweb.utils.MessagesAndRedirect;
import br.com.javaweb.utils.Session;

@ManagedBean
@ViewScoped
public class CadastroCompraController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Compra compra;
	private Investidor investidor;
	private Acao acao;
	private CompraAcaoService compraAcaoService;	
	private HistoricoTransacao historicoTransacao;
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
		historicoTransacao = new HistoricoTransacao();
		investidor = buscarInvestidorSessao(); // busca o investidor antes de colocar na sessao
		
//		existeAcaoParaInvestidor(acao, investidor,existeAcao);			
		
		compra.setNomeAcao(acao.getNomeAcao());			
		compra.setValorFinalAcao(Double.parseDouble(acao.getValorUltimaCotacao().replaceAll(",", ".")));
		compra.setIdInvestidor(investidor);
		historicoTransacao.setIdInvestidor(investidor);		
		
		try {
			compraAcaoService.comprarAcao(compra, investidor,acao.getQuantidade(),historicoTransacao);		
			MessagesAndRedirect.exibirMensagemSucessoRedirect("Compra Realizada com sucesso", "comprarAcoes.xhtml");
		} catch (Exception e) {
			MessagesAndRedirect.exibirMensagemErroRedirect("Falha ao comprar, verifique seu saldo ou entre em contato com a empresa!!", "comprarAcoes.xhtml");
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
