package com.epsilon.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.poi.ss.formula.functions.T;

import com.epsilon.util.JPAUtil;


@SuppressWarnings("hiding")
public class DAO<T> {

    private final Class<T> classe;
	
	private EntityManager em = JPAUtil.getEntityManager();
	
	public DAO(EntityManager em, Class<T> classe) {
        this.em = em;
        this.classe = classe;
    }

	public void adicionarBatch(T t) {
		int entityCount = 10000_000;
		int batchSize = 40;
		
		try {
			 em.getTransaction().begin();
		 
		    for (int i = 0; i < entityCount; i++) {
		        if (i > 0 && i % batchSize == 0) {
		           
		        	em.flush();
		 
		            em.clear();
		        }
		         
		       
		    }
		    em.persist(t);
		    em.getTransaction().commit();
		} catch (RuntimeException e) {
		    if ( em.getTransaction().isActive()) {
		    	 em.getTransaction().rollback();
		    }
		    throw e;
		} finally {
		   // em.close();
		}
	}
	public void adiciona(T t) {
		// persiste o objeto
		em.getTransaction().begin();
		em.persist(t);		
		em.getTransaction().commit();
	}

	public void remove(T t) {
		em.getTransaction().begin();
		em.remove(em.merge(t));	
		em.getTransaction().commit();
	}

	public void atualiza(T t) {	
		em.getTransaction().begin();
		em.merge(t);
		em.getTransaction().commit();
	}

	public List<T> listaTodos() {
		
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));

		List<T> lista = em.createQuery(query).getResultList();

	
		return lista;
	}

	public T buscaPorId(Long id) {
		
		T instancia = em.find(classe, id);
		
		return instancia;
	}

	public int contaTodos() {
		
		long result = (Long) em.createQuery("select count(n) from LoteImportacaoSpedFiscal n")
				.getSingleResult();
		
		
		em.close();

		return (int) result;
	}

	public List<T> listaTodosPaginada(int firstResult, int maxResults) {
	
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));

		List<T> lista = em.createQuery(query).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();

	
		return lista;
	}
}
