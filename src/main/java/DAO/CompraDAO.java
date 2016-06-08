package DAO;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class CompraDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	public CompraDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

   
    public void inserirCompra(){}
    
}
