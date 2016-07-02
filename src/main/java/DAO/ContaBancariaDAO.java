package DAO;

import DAO.exceptions.NonexistentEntityException;
import br.com.javaweb.model.ContaBancaria;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.javaweb.model.Investidor;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class ContaBancariaDAO implements Serializable {

    public ContaBancariaDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ContaBancaria contasBancarias) {
        if (contasBancarias.getInvestidoresList() == null) {
            contasBancarias.setInvestidoresList(new ArrayList<Investidor>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Investidor> attachedInvestidoresList = new ArrayList<Investidor>();
            for (Investidor investidoresListInvestidoresToAttach : contasBancarias.getInvestidoresList()) {
                investidoresListInvestidoresToAttach = em.getReference(investidoresListInvestidoresToAttach.getClass(), investidoresListInvestidoresToAttach.getIdInvestidor());
                attachedInvestidoresList.add(investidoresListInvestidoresToAttach);
            }
            contasBancarias.setInvestidoresList(attachedInvestidoresList);
            em.persist(contasBancarias);
            for (Investidor investidoresListInvestidores : contasBancarias.getInvestidoresList()) {
                ContaBancaria oldIdContaOfInvestidoresListInvestidores = investidoresListInvestidores.getIdConta();
                investidoresListInvestidores.setIdConta(contasBancarias);
                investidoresListInvestidores = em.merge(investidoresListInvestidores);
                if (oldIdContaOfInvestidoresListInvestidores != null) {
                    oldIdContaOfInvestidoresListInvestidores.getInvestidoresList().remove(investidoresListInvestidores);
                    oldIdContaOfInvestidoresListInvestidores = em.merge(oldIdContaOfInvestidoresListInvestidores);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ContaBancaria contasBancarias) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ContaBancaria persistentContasBancarias = em.find(ContaBancaria.class, contasBancarias.getIdConta());
            List<Investidor> investidoresListOld = persistentContasBancarias.getInvestidoresList();
            List<Investidor> investidoresListNew = contasBancarias.getInvestidoresList();
            List<Investidor> attachedInvestidoresListNew = new ArrayList<Investidor>();
            for (Investidor investidoresListNewInvestidoresToAttach : investidoresListNew) {
                investidoresListNewInvestidoresToAttach = em.getReference(investidoresListNewInvestidoresToAttach.getClass(), investidoresListNewInvestidoresToAttach.getIdInvestidor());
                attachedInvestidoresListNew.add(investidoresListNewInvestidoresToAttach);
            }
            investidoresListNew = attachedInvestidoresListNew;
            contasBancarias.setInvestidoresList(investidoresListNew);
            contasBancarias = em.merge(contasBancarias);
            for (Investidor investidoresListOldInvestidores : investidoresListOld) {
                if (!investidoresListNew.contains(investidoresListOldInvestidores)) {
                    investidoresListOldInvestidores.setIdConta(null);
                    investidoresListOldInvestidores = em.merge(investidoresListOldInvestidores);
                }
            }
            for (Investidor investidoresListNewInvestidores : investidoresListNew) {
                if (!investidoresListOld.contains(investidoresListNewInvestidores)) {
                    ContaBancaria oldIdContaOfInvestidoresListNewInvestidores = investidoresListNewInvestidores.getIdConta();
                    investidoresListNewInvestidores.setIdConta(contasBancarias);
                    investidoresListNewInvestidores = em.merge(investidoresListNewInvestidores);
                    if (oldIdContaOfInvestidoresListNewInvestidores != null && !oldIdContaOfInvestidoresListNewInvestidores.equals(contasBancarias)) {
                        oldIdContaOfInvestidoresListNewInvestidores.getInvestidoresList().remove(investidoresListNewInvestidores);
                        oldIdContaOfInvestidoresListNewInvestidores = em.merge(oldIdContaOfInvestidoresListNewInvestidores);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = contasBancarias.getIdConta();
                if (findContasBancarias(id) == null) {
                    throw new NonexistentEntityException("The contasBancarias with id " + id + " no longer exists.");
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
            ContaBancaria contasBancarias;
            try {
                contasBancarias = em.getReference(ContaBancaria.class, id);
                contasBancarias.getIdConta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contasBancarias with id " + id + " no longer exists.", enfe);
            }
            List<Investidor> investidoresList = contasBancarias.getInvestidoresList();
            for (Investidor investidoresListInvestidores : investidoresList) {
                investidoresListInvestidores.setIdConta(null);
                investidoresListInvestidores = em.merge(investidoresListInvestidores);
            }
            em.remove(contasBancarias);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ContaBancaria> findContasBancariasEntities() {
        return findContasBancariasEntities(true, -1, -1);
    }

    public List<ContaBancaria> findContasBancariasEntities(int maxResults, int firstResult) {
        return findContasBancariasEntities(false, maxResults, firstResult);
    }

    private List<ContaBancaria> findContasBancariasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ContaBancaria.class));
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

    public ContaBancaria findContasBancarias(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ContaBancaria.class, id);
        } finally {
            em.close();
        }
    }

    public int getContasBancariasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ContaBancaria> rt = cq.from(ContaBancaria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
