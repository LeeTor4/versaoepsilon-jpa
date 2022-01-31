package com.epsilon.dao.movimentacao;

import java.util.List;

import javax.persistence.EntityManager;

import com.epsilon.dao.DAO;
import com.epsilon.model.movimentacao.TotalizadorDiarioCuponsFiscais;
import com.epsilon.util.JPAUtil;


public class TotalizadorDiarioCuponsFiscaisDao {
	private EntityManager em = JPAUtil.getEntityManager();

	private DAO<TotalizadorDiarioCuponsFiscais> dao;
	
	
	public TotalizadorDiarioCuponsFiscaisDao() {
		this.dao = new DAO<TotalizadorDiarioCuponsFiscais>(em, TotalizadorDiarioCuponsFiscais.class);
	}
	
	public void adiciona(TotalizadorDiarioCuponsFiscais t) {
		dao.adiciona(t);
	}

	public void remove(TotalizadorDiarioCuponsFiscais t) {
		dao.remove(t);
	}

	public void atualiza(TotalizadorDiarioCuponsFiscais t) {
		dao.atualiza(t);
	}

	public List<TotalizadorDiarioCuponsFiscais> listaTodos() {
		return dao.listaTodos();
	}

	public TotalizadorDiarioCuponsFiscais buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public int contaTodos() {
		return dao.contaTodos();
	}
}
