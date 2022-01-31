package com.epsilon.dao.cadastro;

import java.util.List;

import javax.persistence.EntityManager;

import com.epsilon.dao.DAO;
import com.epsilon.model.cadastro.Endereco;
import com.epsilon.util.JPAUtil;


public class EnderecoDao {

private EntityManager em = JPAUtil.getEntityManager();
	
	private DAO<Endereco> dao;
	
	public EnderecoDao() {
		this.dao = new DAO<Endereco>(em, Endereco.class);
	}
	
	public void adiciona(Endereco t) {
		dao.adiciona(t);
	}

	public void remove(Endereco t) {
		dao.remove(t);
	}

	public void atualiza(Endereco t) {
		dao.atualiza(t);
	}

	public List<Endereco> listaTodos() {
		return dao.listaTodos();
	}

	public Endereco buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public int contaTodos() {
		return dao.contaTodos();
	}
}
