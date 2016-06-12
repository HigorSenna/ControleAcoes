package br.com.javaweb.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.javaweb.model.Corretora;
import br.com.javaweb.service.CompraAcaoService;
import br.com.javaweb.transacoes.model.Compra;

@ManagedBean
@ViewScoped
public class ConsultaLucroCorretoraController {
	private Corretora corretora;
	private Compra compra;
	private CompraAcaoService compraAcaoService;
	
	@PostConstruct
	public void init(){
		corretora = new Corretora();
		corretora.setCompras(buscarTodasCompras());
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
