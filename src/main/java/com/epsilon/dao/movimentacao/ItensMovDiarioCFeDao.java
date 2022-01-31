package com.epsilon.dao.movimentacao;

import java.util.List;

import javax.persistence.EntityManager;

import com.epsilon.dao.DAO;
import com.epsilon.model.movimentacao.ItensMovDiarioCFe;
import com.epsilon.util.JPAUtil;

public class ItensMovDiarioCFeDao {
	private EntityManager em = JPAUtil.getEntityManager();

	private DAO<ItensMovDiarioCFe> dao;
	
	
	public ItensMovDiarioCFeDao() {
		this.dao = new DAO<ItensMovDiarioCFe>(em, ItensMovDiarioCFe.class);
	}
	
	public void adiciona(ItensMovDiarioCFe t) {
		dao.adiciona(t);
	}

	public void remove(ItensMovDiarioCFe t) {
		dao.remove(t);
	}

	public void atualiza(ItensMovDiarioCFe t) {
		dao.atualiza(t);
	}

	public List<ItensMovDiarioCFe> listaTodos() {
		return dao.listaTodos();
	}

	public ItensMovDiarioCFe buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public int contaTodos() {
		return dao.contaTodos();
	}
}
