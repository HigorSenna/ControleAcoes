/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import DAO.exceptions.NonexistentEntityException;
import br.com.javaweb.model.ContaBancaria;
import br.com.javaweb.model.Investidor;
import br.com.javaweb.transacoes.model.Compra;
import br.com.javaweb.transacoes.model.HistoricoTransacao;
import br.com.javaweb.transacoes.model.Venda;

public class InvestidorDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	public InvestidorDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Investidor validarLoginSenha(String login, String senha) throws Exception {
        String jpql = "SELECT i FROM Investidor i WHERE i.login =:loginParam and i.senha =:senhaParam";
        Query q = getEntityManager().createQuery(jpql);
        q.setParameter("loginParam", login);
        q.setParameter("senhaParam", senha);
        
        return (Investidor) q.getSingleResult();

//        try {
//            return (Investidor) q.getSingleResult();
//        } catch (NonUniqueResultException no) {
//            return (Investidor) q.getResultList().get(0);//se retornar mais de um usuario, eu pego o primeiro
//        } catch (NoResultException e) {
//            return null; // caso a consulta nao ache nada, retorna null;
//        }

    }

    public void create(Investidor investidores) {
        if (investidores.getHistoricosTransacoesList() == null) {
            investidores.setHistoricosTransacoesList(new ArrayList<HistoricoTransacao>());
        }
        if (investidores.getVendasList() == null) {
            investidores.setVendasList(new ArrayList<Venda>());
        }
        if (investidores.getCompasList() == null) {
            investidores.setCompasList(new ArrayList<Compra>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
//            ContaBancaria idConta = investidores.getIdConta();
//            if (idConta != null) {
//                idConta = em.getReference(idConta.getClass(), idConta.getIdConta());
//                investidores.setIdConta(idConta);
//            }
            List<HistoricoTransacao> attachedHistoricosTransacoesList = new ArrayList<HistoricoTransacao>();
            for (HistoricoTransacao historicosTransacoesListHistoricosTransacoesToAttach : investidores.getHistoricosTransacoesList()) {
                historicosTransacoesListHistoricosTransacoesToAttach = em.getReference(historicosTransacoesListHistoricosTransacoesToAttach.getClass(), historicosTransacoesListHistoricosTransacoesToAttach.getIdHistorico());
                attachedHistoricosTransacoesList.add(historicosTransacoesListHistoricosTransacoesToAttach);
            }
            investidores.setHistoricosTransacoesList(attachedHistoricosTransacoesList);
            List<Venda> attachedVendasList = new ArrayList<Venda>();
            for (Venda vendasListVendasToAttach : investidores.getVendasList()) {
                vendasListVendasToAttach = em.getReference(vendasListVendasToAttach.getClass(), vendasListVendasToAttach.getIdVenda());
                attachedVendasList.add(vendasListVendasToAttach);
            }
            investidores.setVendasList(attachedVendasList);
            List<Compra> attachedCompasList = new ArrayList<Compra>();
            for (Compra compasListCompasToAttach : investidores.getCompasList()) {
                compasListCompasToAttach = em.getReference(compasListCompasToAttach.getClass(), compasListCompasToAttach.getIdCompra());
                attachedCompasList.add(compasListCompasToAttach);
            }
            investidores.setCompasList(attachedCompasList);
            em.persist(investidores);
//            if (idConta != null) {
//                idConta.getInvestidoresList().add(investidores);
//                idConta = em.merge(idConta);
//            }
            for (HistoricoTransacao historicosTransacoesListHistoricosTransacoes : investidores.getHistoricosTransacoesList()) {
                Investidor oldIdInvestidorOfHistoricosTransacoesListHistoricosTransacoes = historicosTransacoesListHistoricosTransacoes.getIdInvestidor();
                historicosTransacoesListHistoricosTransacoes.setIdInvestidor(investidores);
                historicosTransacoesListHistoricosTransacoes = em.merge(historicosTransacoesListHistoricosTransacoes);
                if (oldIdInvestidorOfHistoricosTransacoesListHistoricosTransacoes != null) {
                    oldIdInvestidorOfHistoricosTransacoesListHistoricosTransacoes.getHistoricosTransacoesList().remove(historicosTransacoesListHistoricosTransacoes);
                    oldIdInvestidorOfHistoricosTransacoesListHistoricosTransacoes = em.merge(oldIdInvestidorOfHistoricosTransacoesListHistoricosTransacoes);
                }
            }
            for (Venda vendasListVendas : investidores.getVendasList()) {
                Investidor oldIdInvestidorOfVendasListVendas = vendasListVendas.getIdInvestidor();
                vendasListVendas.setIdInvestidor(investidores);
                vendasListVendas = em.merge(vendasListVendas);
                if (oldIdInvestidorOfVendasListVendas != null) {
                    oldIdInvestidorOfVendasListVendas.getVendasList().remove(vendasListVendas);
                    oldIdInvestidorOfVendasListVendas = em.merge(oldIdInvestidorOfVendasListVendas);
                }
            }
            for (Compra compasListCompas : investidores.getCompasList()) {
                Investidor oldIdInvestidorOfCompasListCompas = compasListCompas.getIdInvestidor();
                compasListCompas.setIdInvestidor(investidores);
                compasListCompas = em.merge(compasListCompas);
                if (oldIdInvestidorOfCompasListCompas != null) {
                    oldIdInvestidorOfCompasListCompas.getCompasList().remove(compasListCompas);
                    oldIdInvestidorOfCompasListCompas = em.merge(oldIdInvestidorOfCompasListCompas);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Investidor investidores) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Investidor persistentInvestidores = em.find(Investidor.class, investidores.getIdInvestidor());
            ContaBancaria idContaOld = persistentInvestidores.getIdConta();
            ContaBancaria idContaNew = investidores.getIdConta();
            List<HistoricoTransacao> historicosTransacoesListOld = persistentInvestidores.getHistoricosTransacoesList();
            List<HistoricoTransacao> historicosTransacoesListNew = investidores.getHistoricosTransacoesList();
            List<Venda> vendasListOld = persistentInvestidores.getVendasList();
            List<Venda> vendasListNew = investidores.getVendasList();
            List<Compra> compasListOld = persistentInvestidores.getCompasList();
            List<Compra> compasListNew = investidores.getCompasList();
            if (idContaNew != null) {
                idContaNew = em.getReference(idContaNew.getClass(), idContaNew.getIdConta());
                investidores.setIdConta(idContaNew);
            }
            List<HistoricoTransacao> attachedHistoricosTransacoesListNew = new ArrayList<HistoricoTransacao>();
            for (HistoricoTransacao historicosTransacoesListNewHistoricosTransacoesToAttach : historicosTransacoesListNew) {
                historicosTransacoesListNewHistoricosTransacoesToAttach = em.getReference(historicosTransacoesListNewHistoricosTransacoesToAttach.getClass(), historicosTransacoesListNewHistoricosTransacoesToAttach.getIdHistorico());
                attachedHistoricosTransacoesListNew.add(historicosTransacoesListNewHistoricosTransacoesToAttach);
            }
            historicosTransacoesListNew = attachedHistoricosTransacoesListNew;
            investidores.setHistoricosTransacoesList(historicosTransacoesListNew);
            List<Venda> attachedVendasListNew = new ArrayList<Venda>();
            for (Venda vendasListNewVendasToAttach : vendasListNew) {
                vendasListNewVendasToAttach = em.getReference(vendasListNewVendasToAttach.getClass(), vendasListNewVendasToAttach.getIdVenda());
                attachedVendasListNew.add(vendasListNewVendasToAttach);
            }
            vendasListNew = attachedVendasListNew;
            investidores.setVendasList(vendasListNew);
            List<Compra> attachedCompasListNew = new ArrayList<Compra>();
            for (Compra compasListNewCompasToAttach : compasListNew) {
                compasListNewCompasToAttach = em.getReference(compasListNewCompasToAttach.getClass(), compasListNewCompasToAttach.getIdCompra());
                attachedCompasListNew.add(compasListNewCompasToAttach);
            }
            compasListNew = attachedCompasListNew;
            investidores.setCompasList(compasListNew);
            investidores = em.merge(investidores);
            if (idContaOld != null && !idContaOld.equals(idContaNew)) {
                idContaOld.getInvestidoresList().remove(investidores);
                idContaOld = em.merge(idContaOld);
            }
            if (idContaNew != null && !idContaNew.equals(idContaOld)) {
                idContaNew.getInvestidoresList().add(investidores);
                idContaNew = em.merge(idContaNew);
            }
            for (HistoricoTransacao historicosTransacoesListOldHistoricosTransacoes : historicosTransacoesListOld) {
                if (!historicosTransacoesListNew.contains(historicosTransacoesListOldHistoricosTransacoes)) {
                    historicosTransacoesListOldHistoricosTransacoes.setIdInvestidor(null);
                    historicosTransacoesListOldHistoricosTransacoes = em.merge(historicosTransacoesListOldHistoricosTransacoes);
                }
            }
            for (HistoricoTransacao historicosTransacoesListNewHistoricosTransacoes : historicosTransacoesListNew) {
                if (!historicosTransacoesListOld.contains(historicosTransacoesListNewHistoricosTransacoes)) {
                    Investidor oldIdInvestidorOfHistoricosTransacoesListNewHistoricosTransacoes = historicosTransacoesListNewHistoricosTransacoes.getIdInvestidor();
                    historicosTransacoesListNewHistoricosTransacoes.setIdInvestidor(investidores);
                    historicosTransacoesListNewHistoricosTransacoes = em.merge(historicosTransacoesListNewHistoricosTransacoes);
                    if (oldIdInvestidorOfHistoricosTransacoesListNewHistoricosTransacoes != null && !oldIdInvestidorOfHistoricosTransacoesListNewHistoricosTransacoes.equals(investidores)) {
                        oldIdInvestidorOfHistoricosTransacoesListNewHistoricosTransacoes.getHistoricosTransacoesList().remove(historicosTransacoesListNewHistoricosTransacoes);
                        oldIdInvestidorOfHistoricosTransacoesListNewHistoricosTransacoes = em.merge(oldIdInvestidorOfHistoricosTransacoesListNewHistoricosTransacoes);
                    }
                }
            }
            for (Venda vendasListOldVendas : vendasListOld) {
                if (!vendasListNew.contains(vendasListOldVendas)) {
                    vendasListOldVendas.setIdInvestidor(null);
                    vendasListOldVendas = em.merge(vendasListOldVendas);
                }
            }
            for (Venda vendasListNewVendas : vendasListNew) {
                if (!vendasListOld.contains(vendasListNewVendas)) {
                    Investidor oldIdInvestidorOfVendasListNewVendas = vendasListNewVendas.getIdInvestidor();
                    vendasListNewVendas.setIdInvestidor(investidores);
                    vendasListNewVendas = em.merge(vendasListNewVendas);
                    if (oldIdInvestidorOfVendasListNewVendas != null && !oldIdInvestidorOfVendasListNewVendas.equals(investidores)) {
                        oldIdInvestidorOfVendasListNewVendas.getVendasList().remove(vendasListNewVendas);
                        oldIdInvestidorOfVendasListNewVendas = em.merge(oldIdInvestidorOfVendasListNewVendas);
                    }
                }
            }
            for (Compra compasListOldCompas : compasListOld) {
                if (!compasListNew.contains(compasListOldCompas)) {
                    compasListOldCompas.setIdInvestidor(null);
                    compasListOldCompas = em.merge(compasListOldCompas);
                }
            }
            for (Compra compasListNewCompas : compasListNew) {
                if (!compasListOld.contains(compasListNewCompas)) {
                    Investidor oldIdInvestidorOfCompasListNewCompas = compasListNewCompas.getIdInvestidor();
                    compasListNewCompas.setIdInvestidor(investidores);
                    compasListNewCompas = em.merge(compasListNewCompas);
                    if (oldIdInvestidorOfCompasListNewCompas != null && !oldIdInvestidorOfCompasListNewCompas.equals(investidores)) {
                        oldIdInvestidorOfCompasListNewCompas.getCompasList().remove(compasListNewCompas);
                        oldIdInvestidorOfCompasListNewCompas = em.merge(oldIdInvestidorOfCompasListNewCompas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = investidores.getIdInvestidor();
                if (findInvestidores(id) == null) {
                    throw new NonexistentEntityException("The investidores with id " + id + " no longer exists.");
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
            Investidor investidores;
            try {
                investidores = em.getReference(Investidor.class, id);
                investidores.getIdInvestidor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The investidores with id " + id + " no longer exists.", enfe);
            }
            ContaBancaria idConta = investidores.getIdConta();
            if (idConta != null) {
                idConta.getInvestidoresList().remove(investidores);
                idConta = em.merge(idConta);
            }
            List<HistoricoTransacao> historicosTransacoesList = investidores.getHistoricosTransacoesList();
            for (HistoricoTransacao historicosTransacoesListHistoricosTransacoes : historicosTransacoesList) {
                historicosTransacoesListHistoricosTransacoes.setIdInvestidor(null);
                historicosTransacoesListHistoricosTransacoes = em.merge(historicosTransacoesListHistoricosTransacoes);
            }
            List<Venda> vendasList = investidores.getVendasList();
            for (Venda vendasListVendas : vendasList) {
                vendasListVendas.setIdInvestidor(null);
                vendasListVendas = em.merge(vendasListVendas);
            }
            List<Compra> compasList = investidores.getCompasList();
            for (Compra compasListCompas : compasList) {
                compasListCompas.setIdInvestidor(null);
                compasListCompas = em.merge(compasListCompas);
            }
            em.remove(investidores);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Investidor> findInvestidoresEntities() {
        return findInvestidoresEntities(true, -1, -1);
    }

    public List<Investidor> findInvestidoresEntities(int maxResults, int firstResult) {
        return findInvestidoresEntities(false, maxResults, firstResult);
    }

    private List<Investidor> findInvestidoresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Investidor.class));
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

    public Investidor findInvestidores(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Investidor.class, id);
        } finally {
            em.close();
        }
    }

    public int getInvestidoresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Investidor> rt = cq.from(Investidor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
