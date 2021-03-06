package com.epsilon.handler;

import java.io.File;
import java.util.concurrent.Callable;

import com.epsilon.model.movimentacao.HistoricoItens;
import com.leetor4.model.nfe.DocumentoFiscalEltronico;
import com.leetor4.model.nfe.Produtos;

import modulos.efdicms.entidades.RegC860;
import modulos.efdicms.manager.LeitorEfdIcms;


public class CallableHistItensCFes implements Callable<HistoricoItens>{

	private ImportaEfdIcms importacoes = new ImportaEfdIcms();
	private LeitorEfdIcms leitor;
	private Produtos p;
	private RegC860 regC860;
	private File f;
	private DocumentoFiscalEltronico doc;
	private Long lote;
	private int pDia;
	private int uDia;
	
	public CallableHistItensCFes(LeitorEfdIcms leitor, RegC860 regC860, File f,Produtos p, 
			DocumentoFiscalEltronico doc, Long lote,int pDia, int uDia) {
		
		this.leitor = leitor;
		this.p = p;
		this.regC860 = regC860;
		this.f = f;
		this.doc = doc;
		this.lote = lote;
		this.pDia = pDia;
		this.uDia = uDia;
	}
	
	@Override
	public HistoricoItens call() throws Exception {
		HistoricoItens retorno = null;
		if(importacoes.insereHistCFes(leitor,regC860 ,f, p, doc, lote).getDtDoc().getDayOfMonth() >= pDia
				&& importacoes.insereHistCFes(leitor,regC860 ,f, p, doc, lote).getDtDoc().getDayOfMonth() < uDia) {
			 retorno = importacoes.insereHistCFes(leitor,regC860 ,f, p, doc, lote);
		}
		//importacoes.insereNotasProprias(leitor,p, doc, lote);
		return retorno;
	}

}
