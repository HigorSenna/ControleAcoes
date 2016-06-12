package DAO;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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
