/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.javaweb.model.Investidor;
import br.com.javaweb.transacoes.model.Venda;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author hschaves
 */
public class VendaDAO implements Serializable {

    public VendaDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Venda vendas) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Investidor idInvestidor = vendas.getIdInvestidor();
            if (idInvestidor != null) {
                idInvestidor = em.getReference(idInvestidor.getClass(), idInvestidor.getIdInvestidor());
                vendas.setIdInvestidor(idInvestidor);
            }
            em.persist(vendas);
            if (idInvestidor != null) {
                idInvestidor.getVendasList().add(vendas);
                idInvestidor = em.merge(idInvestidor);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Venda vendas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Venda persistentVendas = em.find(Venda.class, vendas.getIdVenda());
            Investidor idInvestidorOld = persistentVendas.getIdInvestidor();
            Investidor idInvestidorNew = vendas.getIdInvestidor();
            if (idInvestidorNew != null) {
                idInvestidorNew = em.getReference(idInvestidorNew.getClass(), idInvestidorNew.getIdInvestidor());
                vendas.setIdInvestidor(idInvestidorNew);
            }
            vendas = em.merge(vendas);
            if (idInvestidorOld != null && !idInvestidorOld.equals(idInvestidorNew)) {
                idInvestidorOld.getVendasList().remove(vendas);
                idInvestidorOld = em.merge(idInvestidorOld);
            }
            if (idInvestidorNew != null && !idInvestidorNew.equals(idInvestidorOld)) {
                idInvestidorNew.getVendasList().add(vendas);
                idInvestidorNew = em.merge(idInvestidorNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = vendas.getIdVenda();
                if (findVendas(id) == null) {
                    throw new NonexistentEntityException("The vendas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Venda vendas;
            try {
                vendas = em.getReference(Venda.class, id);
                vendas.getIdVenda();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vendas with id " + id + " no longer exists.", enfe);
            }
            Investidor idInvestidor = vendas.getIdInvestidor();
            if (idInvestidor != null) {
                idInvestidor.getVendasList().remove(vendas);
                idInvestidor = em.merge(idInvestidor);
            }
            em.remove(vendas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Venda> findVendasEntities() {
        return findVendasEntities(true, -1, -1);
    }

    public List<Venda> findVendasEntities(int maxResults, int firstResult) {
        return findVendasEntities(false, maxResults, firstResult);
    }

    private List<Venda> findVendasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Venda.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Venda findVendas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Venda.class, id);
        } finally {
            em.close();
        }
    }

    public int getVendasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Venda> rt = cq.from(Venda.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
