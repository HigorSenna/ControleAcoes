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
		System.out.println("aaa");
		//salvar investidor
	}

	private void saldoInvestidorSuficiente(List<Compra> compras,int quantidade,Investidor investidor){		
		if(valorCompraMenor5Mil(compras, quantidade)){
			if(investidor.getIdConta().getSaldo() <= calculoComum(compras, quantidade)){
				//nao é suficiente
			}			
			else{
				//é suficiente
			}
		}
		else{
			if(investidor.getIdConta().getSaldo() <=calculoAcrescido(compras, quantidade) ){
				//nao é suficiente
			}			
			else{
				//é suficiete
			}
		}
	}
		
	private boolean valorCompraMenor5Mil(List<Compra> compras,int quantidade){
		if(compras.get(compras.size()-1).getValorFinalAcao() * quantidade <= 5000){
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
		double totalPagar = ((compras.get(compras.size()-1).getValorFinalAcao() * quantidade) + 20);
		return totalPagar;
	}
}
