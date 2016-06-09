package br.com.javaweb.service;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import DAO.CompraDAO;
import br.com.javaweb.model.Investidor;
import br.com.javaweb.transacoes.model.Compra;

public class CompraAcaoService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("ControleAcoes");
	
	CompraDAO compraAcaoDAO = new CompraDAO(emf);
	
	public void comprarAcao(List<Compra> compras,Investidor investidor,int quantidade){
		saldoInvestidorSuficiente(compras, quantidade, investidor);
	}

	private void saldoInvestidorSuficiente(List<Compra> compras,int quantidade,Investidor investidor){		
		if(valorCompraMenor5Mil(compras, quantidade)){
			if(investidor.getIdConta().getSaldo() <= calculoComum(compras, quantidade)){
				System.out.println("Saldo insuficiente");
				//nao é suficiente
			}			
			else{
				System.out.println("Pode Comprar");
			}
		}
		else{
			if(investidor.getIdConta().getSaldo() <=calculoAcrescido(compras, quantidade) ){
				System.out.println("Não Pode Comprar 2");
			}			
			else{
				System.out.println("Pode Comprar 2");
			}
		}
	}
		
	private boolean valorCompraMenor5Mil(List<Compra> compras,int quantidade){
		if(compras.get(0).getValorFinalAcao() * quantidade <= 5000){
			return true;
		}
		return false;			
	}
	
	private double calculoAcrescido(List<Compra> compras,int quantidade){
		double valorMinPagar = calculoComum(compras, quantidade);
		
		double totalPagar = valorMinPagar * 0.5 + valorMinPagar + 15.21; // fazer regra para validar se a taxa de 15.21 ja foi cobrada no dia
		return totalPagar;
	}	
	
	private double calculoComum(List<Compra> compras,int quantidade){
		double totalPagar = ((compras.get(0).getValorFinalAcao() * quantidade) + 20);
		return totalPagar;
	}
}
