package com.epsilon.dao.movimentacao;

import java.util.List;

import javax.persistence.EntityManager;

import com.epsilon.dao.DAO;
import com.epsilon.model.movimentacao.NotaFiscal;
import com.epsilon.util.JPAUtil;


public class NotaFiscalDao {
	private EntityManager em = JPAUtil.getEntityManager();

	private DAO<NotaFiscal> dao;
	
	
	public NotaFiscalDao() {
		this.dao = new DAO<NotaFiscal>(em, NotaFiscal.class);
	}
	
	public void adiciona(NotaFiscal t) {
		//dao.adicionarBatch(t);
		dao.adiciona(t);
	}

	public void remove(NotaFiscal t) {
		dao.remove(t);
	}

	public void atualiza(NotaFiscal t) {
		dao.atualiza(t);
	}

	public List<NotaFiscal> listaTodos() {
		return dao.listaTodos();
	}

	public NotaFiscal buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public int contaTodos() {
		return dao.contaTodos();
	}
}
