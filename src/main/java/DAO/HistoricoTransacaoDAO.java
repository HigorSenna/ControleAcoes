package DAO;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import br.com.javaweb.transacoes.model.HistoricoTransacao;

public class HistoricoTransacaoDAO implements Serializable {

	private static final long serialVersionUID = -5123729124267018673L;

	public HistoricoTransacaoDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public HistoricoTransacao buscarAcaoExistente(String nome,int idInvestidor){
    	String jpql = "SELECT h FROM HistoricoTransacao WHERE h.nomeAcao =:nomeParam and h.idInvestidor =:idParam";
    	Query q = getEntityManager().createQuery(jpql);
        q.setParameter("nomeParam", nome);
        q.setParameter("idParam", idInvestidor);
         
        return (HistoricoTransacao) q.getSingleResult();
    }
}
