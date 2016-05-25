/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.javaweb.model.Investidor;
import br.com.javaweb.transacoes.model.Compra;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author hschaves
 */
public class CompraDAO implements Serializable {

    public CompraDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Compra compas) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Investidor idInvestidor = compas.getIdInvestidor();
            if (idInvestidor != null) {
                idInvestidor = em.getReference(idInvestidor.getClass(), idInvestidor.getIdInvestidor());
                compas.setIdInvestidor(idInvestidor);
            }
            em.persist(compas);
            if (idInvestidor != null) {
                idInvestidor.getCompasList().add(compas);
                idInvestidor = em.merge(idInvestidor);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCompas(compas.getIdCompra()) != null) {
                throw new PreexistingEntityException("Compas " + compas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Compra compas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Compra persistentCompas = em.find(Compra.class, compas.getIdCompra());
            Investidor idInvestidorOld = persistentCompas.getIdInvestidor();
            Investidor idInvestidorNew = compas.getIdInvestidor();
            if (idInvestidorNew != null) {
                idInvestidorNew = em.getReference(idInvestidorNew.getClass(), idInvestidorNew.getIdInvestidor());
                compas.setIdInvestidor(idInvestidorNew);
            }
            compas = em.merge(compas);
            if (idInvestidorOld != null && !idInvestidorOld.equals(idInvestidorNew)) {
                idInvestidorOld.getCompasList().remove(compas);
                idInvestidorOld = em.merge(idInvestidorOld);
            }
            if (idInvestidorNew != null && !idInvestidorNew.equals(idInvestidorOld)) {
                idInvestidorNew.getCompasList().add(compas);
                idInvestidorNew = em.merge(idInvestidorNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = compas.getIdCompra();
                if (findCompas(id) == null) {
                    throw new NonexistentEntityException("The compas with id " + id + " no longer exists.");
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
            Compra compas;
            try {
                compas = em.getReference(Compra.class, id);
                compas.getIdCompra();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The compas with id " + id + " no longer exists.", enfe);
            }
            Investidor idInvestidor = compas.getIdInvestidor();
            if (idInvestidor != null) {
                idInvestidor.getCompasList().remove(compas);
                idInvestidor = em.merge(idInvestidor);
            }
            em.remove(compas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Compra> findCompasEntities() {
        return findCompasEntities(true, -1, -1);
    }

    public List<Compra> findCompasEntities(int maxResults, int firstResult) {
        return findCompasEntities(false, maxResults, firstResult);
    }

    private List<Compra> findCompasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Compra.class));
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

    public Compra findCompas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Compra.class, id);
        } finally {
            em.close();
        }
    }

    public int getCompasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Compra> rt = cq.from(Compra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
