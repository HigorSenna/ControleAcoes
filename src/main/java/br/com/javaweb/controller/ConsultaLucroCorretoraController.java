package br.com.javaweb.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.javaweb.model.Corretora;
import br.com.javaweb.model.Investidor;
import br.com.javaweb.service.CompraAcaoService;
import br.com.javaweb.transacoes.model.Compra;
import br.com.javaweb.utils.MessagesAndRedirect;
import br.com.javaweb.utils.Session;

@ManagedBean
@ViewScoped
public class ConsultaLucroCorretoraController {
	private Corretora corretora;
	private Compra compra;
	private CompraAcaoService compraAcaoService;
	private Investidor investidor;
	
	@PostConstruct
	public void init(){
		investidor = new Investidor();
		investidor = buscarInvestidorSessao();
		validarPermissao();
		corretora = new Corretora();
		corretora.setCompras(buscarTodasCompras());
		
	}
	
	private void validarPermissao(){
		if(!(investidor.getLogin().equalsIgnoreCase("admin") || investidor.getLogin().equalsIgnoreCase(("adminisrtrador")))){
			MessagesAndRedirect.redirecionarPara("fail.xhtml");
		}
	}
	
	private Investidor buscarInvestidorSessao(){
		return (Investidor) Session.pegarSessao();
	}
			
	private List<Compra> buscarTodasCompras(){
		compraAcaoService = new CompraAcaoService();
		return compraAcaoService.buscarTodasCompras();
	}
		
	public Corretora getCorretora() {
		return corretora;
	}
	public void setCorretora(Corretora corretora) {
		this.corretora = corretora;
	}
	public Compra getCompra() {
		return compra;
	}
	public void setCompra(Compra compra) {
		this.compra = compra;
	}
	public CompraAcaoService getCompraAcaoService() {
		return compraAcaoService;
	}
	public void setCompraAcaoService(CompraAcaoService compraAcaoService) {
		this.compraAcaoService = compraAcaoService;
	}
	
}
