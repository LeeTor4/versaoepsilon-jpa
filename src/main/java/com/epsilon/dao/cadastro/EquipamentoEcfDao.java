package com.epsilon.dao.cadastro;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.epsilon.dao.DAO;
import com.epsilon.model.cadastro.EquipamentoECF;
import com.epsilon.util.JPAUtil;

public class EquipamentoEcfDao {

private EntityManager em = JPAUtil.getEntityManager();
	
	private DAO<EquipamentoECF> dao;
	
	public EquipamentoEcfDao() {
		this.dao = new DAO<EquipamentoECF>(em, EquipamentoECF.class);
	}
	
	public void adiciona(EquipamentoECF t) {
		dao.adiciona(t);
	}

	public void remove(EquipamentoECF t) {
		dao.remove(t);
	}

	public void atualiza(EquipamentoECF t) {
		dao.atualiza(t);
	}

	public List<EquipamentoECF> listaTodos() {
		return dao.listaTodos();
	}

	public EquipamentoECF buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public int contaTodos() {
		return dao.contaTodos();
	}
	
	public EquipamentoECF buscaPorNumFab(String nome) {
		
		String jpql = ("select e from EquipamentoECF e where e.numSerieFabECF =  :pNome");
		Query query = em.createQuery(jpql);
		query.setParameter("pNome", nome);
		EquipamentoECF equipamento = (EquipamentoECF) query.getSingleResult();
		return equipamento;
	}
}
