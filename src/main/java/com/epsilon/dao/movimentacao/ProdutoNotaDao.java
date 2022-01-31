package com.epsilon.dao.movimentacao;

import java.util.List;

import javax.persistence.EntityManager;

import com.epsilon.dao.DAO;
import com.epsilon.model.movimentacao.ProdutoNotaFiscal;
import com.epsilon.util.JPAUtil;


public class ProdutoNotaDao {

	private EntityManager em = JPAUtil.getEntityManager();

	private DAO<ProdutoNotaFiscal> dao;
	
	
	public ProdutoNotaDao() {
		this.dao = new DAO<ProdutoNotaFiscal>(em, ProdutoNotaFiscal.class);
	}
	
	public void adiciona(ProdutoNotaFiscal t) {
		//dao.adicionarBatch(t);
		dao.adiciona(t);
	}

	public void remove(ProdutoNotaFiscal t) {
		dao.remove(t);
	}

	public void atualiza(ProdutoNotaFiscal t) {
		dao.atualiza(t);
	}

	public List<ProdutoNotaFiscal> listaTodos() {
		return dao.listaTodos();
	}

	public ProdutoNotaFiscal buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public int contaTodos() {
		return dao.contaTodos();
	}
}
