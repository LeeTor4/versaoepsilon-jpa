package com.epsilon.dao.cadastro;

import java.util.List;

import javax.persistence.EntityManager;

import com.epsilon.dao.DAO;
import com.epsilon.model.cadastro.Empresa;
import com.epsilon.util.JPAUtil;

public class EmpresaDao {

	private EntityManager em = JPAUtil.getEntityManager();

	private DAO<Empresa> dao;

	public EmpresaDao() {
		this.dao = new DAO<Empresa>(em, Empresa.class);
	}

	public void adiciona(Empresa t) {
		dao.adiciona(t);
	}

	public void remove(Empresa t) {
		dao.remove(t);
	}

	public void atualiza(Empresa t) {
		dao.atualiza(t);
	}

	public List<Empresa> listaTodos() {
		return dao.listaTodos();
	}

	public Empresa buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public int contaTodos() {
		return dao.contaTodos();
	}
}
