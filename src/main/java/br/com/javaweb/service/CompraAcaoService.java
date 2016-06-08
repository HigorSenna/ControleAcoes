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
	
	public void comprarAcao(List<Compra> compras,Investidor investidor){
		//salvar investidor
	}
		
	
	public void checarValorCompra(Compra compra){
		
	}

}
