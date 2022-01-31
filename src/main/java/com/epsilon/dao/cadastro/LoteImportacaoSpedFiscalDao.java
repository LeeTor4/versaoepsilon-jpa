package com.epsilon.dao.cadastro;

import java.util.List;

import javax.persistence.EntityManager;

import com.epsilon.dao.DAO;
import com.epsilon.model.cadastro.LoteImportacaoSpedFiscal;
import com.epsilon.util.JPAUtil;

public class LoteImportacaoSpedFiscalDao {
	
	
	 private EntityManager em = JPAUtil.getEntityManager();
	 
	 private DAO<LoteImportacaoSpedFiscal> dao;
	 
	 public LoteImportacaoSpedFiscalDao() {
		this.dao = new DAO<LoteImportacaoSpedFiscal>(em, LoteImportacaoSpedFiscal.class);
	}
	 
	 public void adiciona(LoteImportacaoSpedFiscal t) {
			dao.adiciona(t);
		}

		public void remove(LoteImportacaoSpedFiscal t) {
			dao.remove(t);
		}

		public void atualiza(LoteImportacaoSpedFiscal t) {
			dao.atualiza(t);
		}

		public List<LoteImportacaoSpedFiscal> listaTodos() {
			return dao.listaTodos();
		}

		public LoteImportacaoSpedFiscal buscaPorId(Long id) {
			return dao.buscaPorId(id);
		}

		public int contaTodos() {
			return dao.contaTodos();
		}

}
