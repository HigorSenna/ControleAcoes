package DAO;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import br.com.javaweb.model.Investidor;
import br.com.javaweb.transacoes.model.Compra;

public class CompraDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	public CompraDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public void atualizarCompraInvestidor(Investidor investidor){
    	EntityManager em = null;
    	em = getEntityManager();
        
        em.getTransaction().begin(); 
        
        	em.merge(investidor);   
        	
        em.getTransaction().commit(); 
    }

   
    public void inserirCompra(Compra compra){
    	Date atual = new Date();	
    	compra.setDtCompra(atual);
    	EntityManager em = null;     
        em = getEntityManager();
        
        em.getTransaction().begin(); 
        
        	em.persist(compra);   
        	
        em.getTransaction().commit();     
        
        
        if(em !=null){
        	em.close();
        }
    }   
}
