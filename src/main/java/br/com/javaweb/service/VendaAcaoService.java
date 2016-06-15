package br.com.javaweb.service;

import java.util.ArrayList;
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
	
	public void inserirVenda(Venda venda,int quantidadeVenda){
		vendaDAO.inserirVenda(venda);	
		atualizarContaInvestidor(venda.getIdInvestidor().getIdConta(),venda.getValorRecebido());
		HistoricoTransacao historicoAtualizar = buscarRegistroHistoricoParaAtualizar(venda.getIdInvestidor().getHistoricosTransacoesList(), venda.getNomeAcao());
		atualizarHistoricoInvestidor(historicoAtualizar, quantidadeVenda);		
	}
	
	private void atualizarHistoricoInvestidor(HistoricoTransacao historico,int quantidadeVenda){
		int quantidadeExistente = historico.getQuantidadeTotal();
		int quantidadeAtualizada = quantidadeExistente - quantidadeVenda;
		historico.setQuantidadeTotal(quantidadeAtualizada);
		
		vendaDAO.atualizarHistoricoInvestidor(historico);
	}
	
	private HistoricoTransacao buscarRegistroHistoricoParaAtualizar(List<HistoricoTransacao> historicos, String nomeAcao){
		HistoricoTransacao historicoAtualizar = null;
		for(HistoricoTransacao historico: historicos){
			if(historico.getNomeAcao().equals(nomeAcao)){
				historicoAtualizar = historico;
			}
		}
		return historicoAtualizar;
	}
	
	private void atualizarContaInvestidor(ContaBancaria conta,double valorDaVenda){
		vendaDAO = new VendaDAO(emf);
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
