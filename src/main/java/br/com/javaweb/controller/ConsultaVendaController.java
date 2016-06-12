package br.com.javaweb.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.javaweb.model.Investidor;
import br.com.javaweb.transacoes.model.HistoricoTransacao;
import br.com.javaweb.utils.Session;

@ManagedBean
@ViewScoped
public class ConsultaVendaController {
	
	private Investidor investidor;
	
	public List<HistoricoTransacao> exibirAcoesDisponiveisHistorico(){
		investidor = buscarInvestidorSessao();
		return investidor.getHistoricosTransacoesList();
	}
	
	private Investidor buscarInvestidorSessao(){
		return (Investidor) Session.pegarSessao();
	}
}
