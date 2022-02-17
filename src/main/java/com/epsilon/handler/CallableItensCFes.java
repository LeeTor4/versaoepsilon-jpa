package com.epsilon.handler;

import java.io.File;
import java.util.concurrent.Callable;

import com.epsilon.model.movimentacao.HistoricoItens;
import com.epsilon.model.movimentacao.ItensMovDiarioCFe;
import com.leetor4.model.nfe.DocumentoFiscalEltronico;
import com.leetor4.model.nfe.Produtos;

import modulos.efdicms.entidades.RegC860;
import modulos.efdicms.manager.LeitorEfdIcms;


public class CallableItensCFes implements Callable<ItensMovDiarioCFe>{

	private ImportaEfdIcms importacoes = new ImportaEfdIcms();
	private LeitorEfdIcms leitor;
	private Produtos p;
	private RegC860 regC860;
	private File f;
	private DocumentoFiscalEltronico doc;
	private Long lote;
	private int dia;
	
	public CallableItensCFes(LeitorEfdIcms leitor, RegC860 regC860, File f,Produtos p, 
			DocumentoFiscalEltronico doc, Long lote,int dia) {
		
		this.leitor = leitor;
		this.p = p;
		this.regC860 = regC860;
		this.f = f;
		this.doc = doc;
		this.lote = lote;
		this.dia = dia;
	}
	
	@Override
	public ItensMovDiarioCFe call() throws Exception {
		ItensMovDiarioCFe retorno = null;
//		if(importacoes.insereHistCFes(leitor,regC860 ,f, p, doc, lote).getDtDoc().getDayOfMonth() <= dia) {
//			 retorno = importacoes.insereHistCFes(leitor,regC860 ,f, p, doc, lote);
//		}	
		return retorno;
	}

}
