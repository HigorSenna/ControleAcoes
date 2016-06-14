package br.com.javaweb.service;

import java.util.ArrayList;
import java.util.List;

import br.com.javaweb.model.Acao;
import br.com.javaweb.model.Investidor;
import br.com.javaweb.transacoes.model.HistoricoTransacao;
import br.com.javaweb.transacoes.model.Venda;

public class VendaAcaoService {
	private List<Venda> vendas;
	private List<HistoricoTransacao> historico;
	
	public List<HistoricoTransacao> verificarValorVendaCadaAcaoInvestidor(Investidor investidor,List<Acao> acoes){
		historico = new ArrayList<>();
		vendas = new ArrayList<>();
		Venda venda = new Venda();
		
		historico = investidor.getHistoricosTransacoesList();		
		
		for(HistoricoTransacao cadaHistorico: historico){
			for(Acao cadaAcao : acoes){
				if(cadaHistorico.getNomeAcao().equals(cadaAcao.getNomeAcao())){
					cadaHistorico.setValorVendaAcao(Double.parseDouble(cadaAcao.getValorUltimaCotacao()));					
				}
			}
		}
		return historico;
	}
}
