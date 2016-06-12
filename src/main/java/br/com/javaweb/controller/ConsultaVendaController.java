package br.com.javaweb.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.javaweb.model.Investidor;
import br.com.javaweb.utils.Session;

@ManagedBean
@ViewScoped
public class ConsultaVendaController {
	
	private Investidor investidor;
	
	public Investidor exibirAcoesDisponiveis(){
		investidor = buscarInvestidorSessao();
		return investidor;
	}
	
	private Investidor buscarInvestidorSessao(){
		return (Investidor) Session.pegarSessao();
	}
}
