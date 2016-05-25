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
import br.com.javaweb.transacoes.model.HistoricoTransacao;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author hschaves
 */
public class HistoricoTransacaoDAO implements Serializable {

    public HistoricoTransacaoDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HistoricoTransacao historicosTransacoes) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Investidor idInvestidor = historicosTransacoes.getIdInvestidor();
            if (idInvestidor != null) {
                idInvestidor = em.getReference(idInvestidor.getClass(), idInvestidor.getIdInvestidor());
                historicosTransacoes.setIdInvestidor(idInvestidor);
            }
            em.persist(historicosTransacoes);
            if (idInvestidor != null) {
                idInvestidor.getHistoricosTransacoesList().add(historicosTransacoes);
                idInvestidor = em.merge(idInvestidor);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(HistoricoTransacao historicosTransacoes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HistoricoTransacao persistentHistoricosTransacoes = em.find(HistoricoTransacao.class, historicosTransacoes.getIdHistorico());
            Investidor idInvestidorOld = persistentHistoricosTransacoes.getIdInvestidor();
            Investidor idInvestidorNew = historicosTransacoes.getIdInvestidor();
            if (idInvestidorNew != null) {
                idInvestidorNew = em.getReference(idInvestidorNew.getClass(), idInvestidorNew.getIdInvestidor());
                historicosTransacoes.setIdInvestidor(idInvestidorNew);
            }
            historicosTransacoes = em.merge(historicosTransacoes);
            if (idInvestidorOld != null && !idInvestidorOld.equals(idInvestidorNew)) {
                idInvestidorOld.getHistoricosTransacoesList().remove(historicosTransacoes);
                idInvestidorOld = em.merge(idInvestidorOld);
            }
            if (idInvestidorNew != null && !idInvestidorNew.equals(idInvestidorOld)) {
                idInvestidorNew.getHistoricosTransacoesList().add(historicosTransacoes);
                idInvestidorNew = em.merge(idInvestidorNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = historicosTransacoes.getIdHistorico();
                if (findHistoricosTransacoes(id) == null) {
                    throw new NonexistentEntityException("The historicosTransacoes with id " + id + " no longer exists.");
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
            HistoricoTransacao historicosTransacoes;
            try {
                historicosTransacoes = em.getReference(HistoricoTransacao.class, id);
                historicosTransacoes.getIdHistorico();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historicosTransacoes with id " + id + " no longer exists.", enfe);
            }
            Investidor idInvestidor = historicosTransacoes.getIdInvestidor();
            if (idInvestidor != null) {
                idInvestidor.getHistoricosTransacoesList().remove(historicosTransacoes);
                idInvestidor = em.merge(idInvestidor);
            }
            em.remove(historicosTransacoes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<HistoricoTransacao> findHistoricosTransacoesEntities() {
        return findHistoricosTransacoesEntities(true, -1, -1);
    }

    public List<HistoricoTransacao> findHistoricosTransacoesEntities(int maxResults, int firstResult) {
        return findHistoricosTransacoesEntities(false, maxResults, firstResult);
    }

    private List<HistoricoTransacao> findHistoricosTransacoesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HistoricoTransacao.class));
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

    public HistoricoTransacao findHistoricosTransacoes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HistoricoTransacao.class, id);
        } finally {
            em.close();
        }
    }

    public int getHistoricosTransacoesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HistoricoTransacao> rt = cq.from(HistoricoTransacao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
