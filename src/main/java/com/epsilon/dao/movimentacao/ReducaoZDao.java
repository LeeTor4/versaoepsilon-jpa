package com.epsilon.dao.movimentacao;

import java.util.List;

import javax.persistence.EntityManager;

import com.epsilon.dao.DAO;
import com.epsilon.model.movimentacao.ReducaoZ;
import com.epsilon.util.JPAUtil;


public class ReducaoZDao {
	private EntityManager em = JPAUtil.getEntityManager();

	private DAO<ReducaoZ> dao;
	
	
	public ReducaoZDao() {
		this.dao = new DAO<ReducaoZ>(em, ReducaoZ.class);
	}
	
	public void adiciona(ReducaoZ t) {
		dao.adiciona(t);
	}

	public void remove(ReducaoZ t) {
		dao.remove(t);
	}

	public void atualiza(ReducaoZ t) {
		dao.atualiza(t);
	}

	public List<ReducaoZ> listaTodos() {
		return dao.listaTodos();
	}

	public ReducaoZ buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public int contaTodos() {
		return dao.contaTodos();
	}
}
