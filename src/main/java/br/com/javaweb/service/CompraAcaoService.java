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
	
	public void comprarAcao(Compra compra,Investidor investidor,int quantidade,HistoricoTransacao historico) throws Exception{
		saldoInvestidorSuficiente(compra, quantidade, investidor,historico);
	}

	private void saldoInvestidorSuficiente(Compra compra,int quantidade,Investidor investidor,HistoricoTransacao historico) throws Exception{		
		if(valorCompraMenor5Mil(compra, quantidade)){
			if(investidor.getIdConta().getSaldo() <= calculoComum(compra, quantidade)){
				System.out.println("Saldo insuficiente < 5 mil");				
				throw new Exception();
			}			
			else{
				System.out.println("Pode Comprar < 5 mil");
				try {
					if(existeAcaoHistoricoInvestidor(investidor, compra)){
						
						HistoricoTransacao historicoInv = investidor.getHistoricosTransacoesList().get(i);
						int quantidadeExistente = historicoInv.getQuantidadeTotal();
						int quantidadeAtualizada = quantidadeExistente + quantidade;
						historicoInv.setQuantidadeTotal(quantidadeAtualizada);
						historicoInv.setValorDeCompra(compra.getValorFinalAcao());
						
						compra.setQuantidade(quantidade);
						compra.setValorPago(calculoComum(compra, quantidade) -20);//valor pago sem taxas
						compra.setTotalPago(calculoComum(compra, quantidade));
						compra.setTaxa(20);
						
						compraAcaoDAO.inserirCompra(compra);	
						compraAcaoDAO.atualizarHistorico(historicoInv);
						
						atualizarDadosCompraComumInvestidor(investidor,compra,quantidade);	
					}
					else{			
						historico.setQuantidadeTotal(quantidade);
						historico.setNomeAcao(compra.getNomeAcao());
						historico.setValorDeCompra(compra.getValorFinalAcao());
						
						compra.setQuantidade(quantidade);
						compra.setValorPago(calculoComum(compra, quantidade) -20);//valor pago sem taxas
						compra.setTotalPago(calculoComum(compra, quantidade));
						compra.setTaxa(20);
						
						compraAcaoDAO.inserirCompra(compra);
						compraAcaoDAO.inserirHistorico(historico);
						
						atualizarDadosCompraComumInvestidor(investidor,compra,quantidade);	
					}
					
				} catch (Exception e) {
					System.out.println(e.getMessage());
					throw new Exception();
				}
			}
		}
		else{
			if(investidor.getIdConta().getSaldo() <=calculoAcrescido(compra, quantidade) ){
				System.out.println("Não Pode Comprar 2");
			}			
			else{
				try {
					System.out.println("Pode Comprar 2 > 5mil");
					double valorComAcrescimo20 = calculoComum(compra,quantidade);
					compra.setValorPago(calculoAcrescido(compra, quantidade)-15.21 -0.5 * valorComAcrescimo20);//valor pago sem taxas				
					compra.setTaxa(20 + 0.5 * calculoComum(compra,quantidade) + 15.21);
					compra.setTotalPago(calculoAcrescido(compra, quantidade));
					compra.setQuantidade(quantidade);				
					compraAcaoDAO.inserirCompra(compra);
					atualizarDadosCompraComTaxasInvestidor(investidor,compra,quantidade);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					throw new Exception();
				}
			}
		}
	}
	int i =0;
	private boolean existeAcaoHistoricoInvestidor(Investidor investidor,Compra compra){
		
		for (HistoricoTransacao hist: investidor.getHistoricosTransacoesList()) {
			if(hist.getNomeAcao().equals(compra.getNomeAcao())){
				i++;
				return true; //METODO NAO ESTÁ  funcionando
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
