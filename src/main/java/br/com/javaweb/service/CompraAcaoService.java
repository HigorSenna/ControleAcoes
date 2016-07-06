package br.com.javaweb.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import DAO.CompraDAO;
import br.com.javaweb.model.Investidor;
import br.com.javaweb.transacoes.model.Compra;
import br.com.javaweb.transacoes.model.HistoricoTransacao;

public class CompraAcaoService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("ControleAcoes");
	
	CompraDAO compraAcaoDAO = new CompraDAO(emf);
	
	public void comprarAcao(Compra compra,Investidor investidor,int quantidadeDeCompra,HistoricoTransacao historico) throws Exception{
		validarSaldoInvestidor(compra, quantidadeDeCompra, investidor);
		
		if(valorCompraMenor5Mil(compra, quantidadeDeCompra)){
		
			preencherCompraComum(quantidadeDeCompra, compra, investidor);
			
			if(existeAcaoHistoricoInvestidor(investidor, compra)){
				realizarOperacoesComHistoricoExistente(compra, investidor, quantidadeDeCompra, historico);
			}
			else{
				realizarOperacoesParaNovoRegistroNoHistorico(compra, investidor, quantidadeDeCompra, historico);
			}
		}
		else{
		
			preencherCompraComTaxas(quantidadeDeCompra, compra, investidor);
			
			if(existeAcaoHistoricoInvestidor(investidor, compra)){
				realizarOperacoesComHistoricoExistente(compra, investidor, quantidadeDeCompra, historico);
			}
			else{
				realizarOperacoesParaNovoRegistroNoHistorico(compra, investidor, quantidadeDeCompra, historico);
			}
		}
	}
	
	private void validarSaldoInvestidor(Compra compra,int quantidadeDeCompra,Investidor investidor) throws Exception{
		if(investidor.getIdConta().getSaldo() <= calculoComum(compra, quantidadeDeCompra) ||
				investidor.getIdConta().getSaldo() <= calculoAcrescido(compra, quantidadeDeCompra) ){
			throw new Exception();
		}	
	}
	
	private void realizarOperacoesComHistoricoExistente(Compra compra,Investidor investidor,int quantidadeDeCompra,HistoricoTransacao historico){	
		
		if(investidor.getHistoricosTransacoesList().size() == 1){
			historico = investidor.getHistoricosTransacoesList().get(0);
		}
		else{
			historico = investidor.getHistoricosTransacoesList().get(i);
		}
		
		historico.setValorDeCompra(compra.getTotalPago()/quantidadeDeCompra);
		int quantidadeExistente = historico.getQuantidadeTotal();
		int quantidadeAtualizada = quantidadeExistente + quantidadeDeCompra;
		historico.setQuantidadeTotal(quantidadeAtualizada);
			
		compraAcaoDAO.atualizarHistorico(historico);
	}
	
	private void realizarOperacoesParaNovoRegistroNoHistorico(Compra compra,Investidor investidor,int quantidadeDeCompra,HistoricoTransacao historico){
		historico.setQuantidadeTotal(quantidadeDeCompra);
		historico.setNomeAcao(compra.getNomeAcao());	
		historico.setValorDeCompra(compra.getTotalPago()/quantidadeDeCompra);					
		
		compraAcaoDAO.inserirHistorico(historico);
	}
	
	private void preencherCompraComTaxas(int quantidade,Compra compra, Investidor investidor){
		double valorComAcrescimo20 = calculoComum(compra,quantidade);
		compra.setValorPago(calculoAcrescido(compra, quantidade)-15.21 -0.5 * valorComAcrescimo20);//valor pago sem taxas				
		compra.setTaxa(20 + 0.5 * calculoComum(compra,quantidade) + 15.21);
		compra.setTotalPago(calculoAcrescido(compra, quantidade));
		compra.setQuantidade(quantidade);
		
		compraAcaoDAO.inserirCompra(compra);
		atualizarDadosCompraComTaxasInvestidor(investidor,compra,quantidade);
	}
	
	private void preencherCompraComum(int quantidade, Compra compra, Investidor investidor){
		compra.setQuantidade(quantidade);
		compra.setValorPago(calculoComum(compra, quantidade) -20);//valor pago sem taxas
		compra.setTotalPago(calculoComum(compra, quantidade));
		compra.setTaxa(20);
		
		compraAcaoDAO.inserirCompra(compra);
		atualizarDadosCompraComumInvestidor(investidor,compra,quantidade);	
	}
	
	int i =0;
	private boolean existeAcaoHistoricoInvestidor(Investidor investidor,Compra compra){
		if(investidor.getHistoricosTransacoesList().size() > 0){
			for (HistoricoTransacao hist: investidor.getHistoricosTransacoesList()) {			
				if(hist.getNomeAcao().equals(compra.getNomeAcao())){					
					return true; 
				}
				i++;
			}
		}
		
		return false;
	}
	
		
	private boolean valorCompraMenor5Mil(Compra compra,int quantidade){
		if(compra.getValorFinalAcao() * quantidade <= 5000){
			return true;
		}
		
		return false;			
	}
	
	private double calculoAcrescido(Compra compra,int quantidade){
		double valorMinPagar = calculoComum(compra, quantidade);
		double totalPagar = valorMinPagar * 0.5 + valorMinPagar + 15.21; // fazer regra para validar se a taxa de 15.21 ja foi cobrada no dia
		
		return totalPagar;
	}	
	
	private double calculoComum(Compra compra,int quantidade){
		double totalPagar = ((compra.getValorFinalAcao() * quantidade) + 20);
		
		return totalPagar;
	}
	
	private void atualizarDadosCompraComumInvestidor(Investidor investidor,Compra compra,int quantidade){		
		double saldoFinal = investidor.getIdConta().getSaldo() - calculoComum(compra, quantidade);
		investidor.getIdConta().setSaldo(saldoFinal);	
		
		compraAcaoDAO.atualizarCompraInvestidor(investidor);
	}
	private void atualizarDadosCompraComTaxasInvestidor(Investidor investidor,Compra compra,int quantidade){
		double saldoFinal = investidor.getIdConta().getSaldo() - calculoAcrescido(compra, quantidade);
		investidor.getIdConta().setSaldo(saldoFinal);	
		
		compraAcaoDAO.atualizarCompraInvestidor(investidor);
	}	
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Compra> buscarTodasCompras(){
		return compraAcaoDAO.buscarTodasCompras();
	}
}
