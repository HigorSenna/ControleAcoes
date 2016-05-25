
package br.com.javaweb.service;

import DAO.InvestidorDAO;
import br.com.javaweb.model.Investidor;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class InvestidorService {

    public InvestidorService(){}
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ControleAcoes");
    InvestidorDAO investidorDAO = new InvestidorDAO(emf);

    public Investidor verificarLogin(String login, String senha) throws Exception {
        return investidorDAO.validarLoginSenha(login, senha);
    }
}
