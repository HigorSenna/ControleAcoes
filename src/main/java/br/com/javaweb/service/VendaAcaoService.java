package br.com.javaweb.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import DAO.VendaDAO;
import br.com.javaweb.model.Acao;
import br.com.javaweb.model.ContaBancaria;
import br.com.javaweb.model.Investidor;
import br.com.javaweb.transacoes.model.HistoricoTransacao;
import br.com.javaweb.transacoes.model.Venda;

public class VendaAcaoService {
	
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("ControleAcoes");
	
	private VendaDAO vendaDAO;
	
	private List<HistoricoTransacao> historico;
	
	public List<Venda> buscarNomesVendas(){
		vendaDAO = new VendaDAO(emf);
		return vendaDAO.buscarNomesVenda();
	}
	
	public List<Venda> pesquisarPorNome(String nomeAcao){
		vendaDAO = new VendaDAO(emf);
		return vendaDAO.buscarVendaPorNomeAcao(nomeAcao);
	}
	
	public void inserirVenda(Venda venda,int quantidadeVenda,HistoricoTransacao historicoAtualizar){
		vendaDAO = new VendaDAO(emf);
		
		montarInstanciaVenda(venda, historicoAtualizar, quantidadeVenda);		
		vendaDAO.inserirVenda(venda);			
		atualizarContaInvestidor(venda.getIdInvestidor().getIdConta(),historicoAtualizar.getValorVendaAcao());
	
		atualizarHistoricoInvestidor(historicoAtualizar, quantidadeVenda);		
	}
	
	private void montarInstanciaVenda(Venda venda,HistoricoTransacao historico,int quantidadeVenda){
		Date dataVenda = new Date();
		String nomeAcao = historico.getNomeAcao();
		venda.setNomeAcao(nomeAcao);
		venda.setQuantidade(quantidadeVenda);
		double valorVendaAcao = historico.getValorVendaAcao();
		double valorTotalVenda = valorVendaAcao * quantidadeVenda;		
		venda.setValorVendaAcao(valorTotalVenda);
		venda.setDtVenda(dataVenda);
		
		atribuirValorLucroOuPrejuizoVenda(historico.getLucroOuPrejuizo(), quantidadeVenda, venda);
	}
	
	private void atribuirValorLucroOuPrejuizoVenda(double valorLucroPrejuizoHistorico,int quantidadeVenda,Venda venda){
		double valorLucroPrejuizo =  valorLucroPrejuizoHistorico * quantidadeVenda;
		
		venda.setLucroPrejuizo(valorLucroPrejuizo);
	}
	
	private void atualizarHistoricoInvestidor(HistoricoTransacao historico,int quantidadeVenda){
		int quantidadeExistente = historico.getQuantidadeTotal();
		int quantidadeAtualizada = quantidadeExistente - quantidadeVenda;
		historico.setQuantidadeTotal(quantidadeAtualizada);
		
		vendaDAO.atualizarHistoricoInvestidor(historico);
	}
	
	private void atualizarContaInvestidor(ContaBancaria conta,double valorDaVenda){
		double saldoExistente = conta.getSaldo();
		double saldoAtualizado = saldoExistente + valorDaVenda;
		conta.setSaldo(saldoAtualizado);	
		vendaDAO.atualizarContaBancaria(conta);
	}
	
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
