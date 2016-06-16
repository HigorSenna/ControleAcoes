package br.com.javaweb.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.javaweb.model.Investidor;
import br.com.javaweb.service.InvestidorService;
import br.com.javaweb.service.VendaAcaoService;
import br.com.javaweb.transacoes.model.Venda;
import br.com.javaweb.utils.Session;
import br.com.javaweb.vo.FiltroLucroPrejuizoVO;

@ManagedBean
@ViewScoped
public class ConsultaLucroPrejuizoInvestidorController {
	
	private Investidor investidor;
	private InvestidorService investidorService;
	private VendaAcaoService vendaService;
	private FiltroLucroPrejuizoVO filtroLucroPrejuizoVO;
	private List<Venda> vendas;
	private List<Venda> vendasPesquisadas = new ArrayList<>();
	
	@PostConstruct
	public void init(){		
		investidor = buscarInvestidor();
		vendas = buscarNomesAcao();
		filtroLucroPrejuizoVO = new FiltroLucroPrejuizoVO();
	}
	
	private List<Venda> buscarNomesAcao(){
		vendaService = new VendaAcaoService();
		return vendaService.buscarNomesVendas();
	}
	
	public List<Venda> pesquisarPorNome(){
		vendaService = new VendaAcaoService();
		vendasPesquisadas = vendaService.pesquisarPorNome(filtroLucroPrejuizoVO.getNomeAcao());
		return vendasPesquisadas;
	}
	
	private Investidor buscarInvestidor(){
		investidorService = new InvestidorService();
		investidor = buscarInvestidorSessao();
		return investidorService.buscarInvestidorLoginSenha(investidor.getLogin(), investidor.getSenha());
	}
	
	public List<Venda> buscarVendasInvestidor(){
		if(vendasPesquisadas.isEmpty() || vendasPesquisadas == null){
			return investidor.getVendasList();
		}
		return vendasPesquisadas;
	}
	
	private Investidor buscarInvestidorSessao(){
		return (Investidor) Session.pegarSessao();		
	}

	public FiltroLucroPrejuizoVO getFiltroLucroPrejuizoVO() {
		return filtroLucroPrejuizoVO;
	}

	public void setFiltroLucroPrejuizoVO(FiltroLucroPrejuizoVO filtroLucroPrejuizoVO) {
		this.filtroLucroPrejuizoVO = filtroLucroPrejuizoVO;
	}

	public List<Venda> getVendas() {
		return vendas;
	}

	public void setVendas(List<Venda> vendas) {
		this.vendas = vendas;
	}	
}
