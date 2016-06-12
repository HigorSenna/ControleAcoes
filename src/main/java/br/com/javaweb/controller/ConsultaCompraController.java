package br.com.javaweb.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.javaweb.model.Investidor;
import br.com.javaweb.transacoes.model.Compra;
import br.com.javaweb.utils.Session;

@ManagedBean
@ViewScoped
public class ConsultaCompraController {

	private Investidor investidor;
	
	public List<Compra> buscarComprasInvestidor(){
		investidor = buscarInvestidorSessao();
		return investidor.getComprasList();
	}
	
	private Investidor buscarInvestidorSessao(){
		return (Investidor) Session.pegarSessao();		
	}
}