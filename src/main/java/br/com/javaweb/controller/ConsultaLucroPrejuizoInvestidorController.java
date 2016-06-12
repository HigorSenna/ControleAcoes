package br.com.javaweb.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.javaweb.model.Investidor;
import br.com.javaweb.service.InvestidorService;

@ManagedBean
@ViewScoped
public class ConsultaLucroPrejuizoInvestidorController {
	private InvestidorService investidorService;
	
	public List<Investidor> buscarInvestidores(){
		investidorService = new InvestidorService();
		return investidorService.buscarInvestidores();
	}
}
