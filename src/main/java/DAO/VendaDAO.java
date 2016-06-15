package DAO;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import br.com.javaweb.model.ContaBancaria;
import br.com.javaweb.transacoes.model.HistoricoTransacao;
import br.com.javaweb.transacoes.model.Venda;

public class VendaDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	public VendaDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public void atualizarHistoricoInvestidor(HistoricoTransacao historico){
    	 EntityManager em = null;
    	 em = getEntityManager();
    	 
         em.getTransaction().begin();
         	em.merge(historico);
         em.getTransaction().commit();
         
         if (em != null) {
             em.close();
         }
    }
    
    public void atualizarContaBancaria(ContaBancaria conta){
    	 EntityManager em = null;
    	 em = getEntityManager();
    	 
         em.getTransaction().begin();
         	em.merge(conta);
         em.getTransaction().commit();
         
         if (em != null) {
             em.close();
         }
    }
   
    public void inserirVenda(Venda venda){
    	 EntityManager em = null;
    	 em = getEntityManager();
    	 
         em.getTransaction().begin();
         	em.persist(venda);
         em.getTransaction().commit();
         
         if (em != null) {
             em.close();
         }
    }
}
