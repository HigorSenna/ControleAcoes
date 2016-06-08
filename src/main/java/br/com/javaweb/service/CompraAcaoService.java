package br.com.javaweb.service;

import java.io.Serializable;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import DAO.CompraDAO;
import br.com.javaweb.model.Acao;
import br.com.javaweb.model.Investidor;

public class CompraAcaoService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("ControleAcoes");
	
	CompraDAO compraAcaoDAO = new CompraDAO(emf);
	
	public void comprarAcao(Investidor investidor,Acao acao){
//		if(investidor.getIdConta().getSaldo() >= acao.get)
	}

}
