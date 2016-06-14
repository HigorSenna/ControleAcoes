package br.com.javaweb.service;

import java.util.ArrayList;
import java.util.List;

import br.com.javaweb.model.Acao;
import br.com.javaweb.model.Investidor;
import br.com.javaweb.transacoes.model.HistoricoTransacao;

public class VendaAcaoService {
	private List<HistoricoTransacao> historico;
	
	public List<HistoricoTransacao> verificarValorVendaCadaAcaoInvestidor(Investidor investidor,List<Acao> acoes){
		historico = new ArrayList<>();
		
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
