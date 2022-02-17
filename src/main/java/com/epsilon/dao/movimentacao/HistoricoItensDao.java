package com.epsilon.dao.movimentacao;

import java.util.List;

import javax.persistence.EntityManager;

import com.epsilon.dao.DAO;
import com.epsilon.model.movimentacao.HistoricoItens;
import com.epsilon.util.JPAUtil;


public class HistoricoItensDao {
	private EntityManager em = JPAUtil.getEntityManager();

	private DAO<HistoricoItens> dao;
	
	
	public HistoricoItensDao() {
		this.dao = new DAO<HistoricoItens>(em, HistoricoItens.class);
	}
	
	
	public void adiciona(HistoricoItens t) {
		dao.adiciona(t);
		//dao.adicionarBatch(t);
	}

	public void remove(HistoricoItens t) {
		dao.remove(t);
	}

	public void atualiza(HistoricoItens t) {
		dao.atualiza(t);
	}

	public List<HistoricoItens> listaTodos() {
		return dao.listaTodos();
	}

	public HistoricoItens buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public int contaTodos() {
		return dao.contaTodos();
	}
}
