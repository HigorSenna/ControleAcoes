
package br.com.javaweb.service;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import DAO.InvestidorDAO;
import br.com.javaweb.model.Investidor;

public class InvestidorService {

    public InvestidorService(){}
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ControleAcoes");
    InvestidorDAO investidorDAO = new InvestidorDAO(emf);

    public Investidor verificarLogin(String login, String senha) throws Exception {
        return investidorDAO.validarLoginSenha(login, senha);
    }
    
    public void salvar(Investidor investidor) throws Exception{
    	investidorDAO.create(investidor);    	
    }
    
    public List<Investidor> buscarInvestidores(){
    	return investidorDAO.buscarTodos();
    	
    }
}
