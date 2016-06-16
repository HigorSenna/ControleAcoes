package br.com.javaweb.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.javaweb.model.Investidor;
import br.com.javaweb.transacoes.model.Venda;
import br.com.javaweb.utils.Session;

@ManagedBean
@ViewScoped
public class ConsultaLucroPrejuizoInvestidorController {
	
	private Investidor investidor;
	
	public List<Venda> buscarVendasInvestidor(){
		investidor = buscarInvestidorSessao();
		return investidor.getVendasList();
	}
	
	private Investidor buscarInvestidorSessao(){
		return (Investidor) Session.pegarSessao();		
	}	
}
