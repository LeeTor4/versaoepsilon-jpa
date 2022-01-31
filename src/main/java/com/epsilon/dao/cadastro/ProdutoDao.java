package com.epsilon.dao.cadastro;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.epsilon.dao.DAO;
import com.epsilon.model.cadastro.Produto;
import com.epsilon.util.JPAUtil;


public class ProdutoDao {

    private EntityManager em = JPAUtil.getEntityManager();
	
	private DAO<Produto> dao;
	
	public ProdutoDao() {
		this.dao = new DAO<Produto>(em, Produto.class);
	}
	
	public void adiciona(Produto t) {
		dao.adiciona(t);
	}

	public void remove(Produto t) {
		dao.remove(t);
	}

	public void atualiza(Produto t) {
		dao.atualiza(t);
	}

	public List<Produto> listaTodos() {
		return dao.listaTodos();
	}

	public Produto buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public int contaTodos() {
		return dao.contaTodos();
	}
	
	public Produto buscaPorCodigo(String codigo) {
		EntityManager em = JPAUtil.getEntityManager();
		String sql = "SELECT * FROM tb_produto where codUtilizEstab = " + "'"+ codigo + "'";
		Query query =  em.createNativeQuery(sql, Produto.class);
		Produto singleResult = (Produto) query.getSingleResult();
		return singleResult;
	}
}
