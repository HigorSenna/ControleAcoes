package DAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

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
    
    @SuppressWarnings("unchecked")
	public List<String> buscarNomesVenda(){
    	EntityManager em = null;
   	 	em = getEntityManager();
   	 	
   	 	String jpql = "SELECT DISTINCT v.nomeAcao FROM Venda v";
   	 	Query q = em.createQuery(jpql);
   	 	
   	 	return  q.getResultList();
    }
    
    @SuppressWarnings("unchecked")
	public List<Venda> buscarVendaPorNomeAcao(String nomeAcao){
    	EntityManager em = null;
   	 	em = getEntityManager();
    	String jpql = "SELECT v FROM Venda v WHERE v.nomeAcao =:acaoParam";
    	Query q = em.createQuery(jpql);
        q.setParameter("acaoParam", nomeAcao);
        
        return (List<Venda>) q.getResultList();
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
