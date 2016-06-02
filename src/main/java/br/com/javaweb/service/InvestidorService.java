
package br.com.javaweb.service;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import DAO.InvestidorDAO;
import DAO.exceptions.NonexistentEntityException;
import br.com.javaweb.model.Investidor;

public class InvestidorService {

    public InvestidorService(){}
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ControleAcoes");
    InvestidorDAO investidorDAO = new InvestidorDAO(emf);
    
    public void editar(Integer id) throws NonexistentEntityException, Exception{
    	Investidor investidor = investidorDAO.findInvestidores(id);
    	investidorDAO.edit(investidor);
    }

    public Investidor verificarLogin(String login, String senha) throws Exception {
        return investidorDAO.validarLoginSenha(login, senha);
    }
    
    public void salvar(Investidor investidor) throws Exception{
    	investidorDAO.create(investidor);    	
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Investidor> buscarInvestidores(){
    	return investidorDAO.buscarTodos();
    	
    }
}
