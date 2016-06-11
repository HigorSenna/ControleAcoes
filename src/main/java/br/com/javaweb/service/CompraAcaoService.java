package br.com.javaweb.service;

import java.io.Serializable;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import DAO.CompraDAO;
import br.com.javaweb.model.Investidor;
import br.com.javaweb.transacoes.model.Compra;

public class CompraAcaoService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("ControleAcoes");
	
	CompraDAO compraAcaoDAO = new CompraDAO(emf);
	
	public void comprarAcao(Compra compra,Investidor investidor,int quantidade){
		saldoInvestidorSuficiente(compra, quantidade, investidor);
	}

	private void saldoInvestidorSuficiente(Compra compra,int quantidade,Investidor investidor){		
		if(valorCompraMenor5Mil(compra, quantidade)){
			if(investidor.getIdConta().getSaldo() <= calculoComum(compra, quantidade)){
				System.out.println("Saldo insuficiente");
				//nao é suficiente
			}			
			else{
				System.out.println("Pode Comprar < 5 mil");
				//debitar saldo, if comprou, atualizo o saldo da conta
				compraAcaoDAO.inserirCompra(compra);	
				
			}
		}
		else{
			if(investidor.getIdConta().getSaldo() <=calculoAcrescido(compra, quantidade) ){
				System.out.println("Não Pode Comprar 2");
			}			
			else{
				System.out.println("Pode Comprar 2");
			}
		}
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
}
