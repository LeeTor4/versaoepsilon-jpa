package com.epsilon.dao.movimentacao;

import java.util.List;

import javax.persistence.EntityManager;

import com.epsilon.dao.DAO;
import com.epsilon.model.movimentacao.TotParciaisRDZ;
import com.epsilon.util.JPAUtil;


public class TotParciaisRDZDao {

	private EntityManager em = JPAUtil.getEntityManager();

	private DAO<TotParciaisRDZ> dao;
	
	
	public TotParciaisRDZDao() {
		this.dao = new DAO<TotParciaisRDZ>(em, TotParciaisRDZ.class);
	}
	
	public void adiciona(TotParciaisRDZ t) {
		dao.adiciona(t);
	}

	public void remove(TotParciaisRDZ t) {
		dao.remove(t);
	}

	public void atualiza(TotParciaisRDZ t) {
		dao.atualiza(t);
	}

	public List<TotParciaisRDZ> listaTodos() {
		return dao.listaTodos();
	}

	public TotParciaisRDZ buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public int contaTodos() {
		return dao.contaTodos();
	}
}
