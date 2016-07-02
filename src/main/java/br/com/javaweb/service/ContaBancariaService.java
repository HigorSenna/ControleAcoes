package br.com.javaweb.service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import DAO.ContaBancariaDAO;
import br.com.javaweb.model.ContaBancaria;

public class ContaBancariaService {
	
	private ContaBancariaDAO contaBancariaDAO;
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("ControleAcoes");
	
	public void depositar(ContaBancaria contaInvestidor,double valorDepositar) throws Exception{
		adicionarSaldoContaInvestidor(contaInvestidor, valorDepositar);	
		
		contaBancariaDAO = new ContaBancariaDAO(emf);	
		contaBancariaDAO.atualizarSaldo(contaInvestidor);		
	}
	
	private void adicionarSaldoContaInvestidor(ContaBancaria contaInvestidor,double valorDepositar){		
		double saldoAtual = contaInvestidor.getSaldo();
		double novoSaldo = saldoAtual + valorDepositar;
		contaInvestidor.setSaldo(novoSaldo);
	}
}
