package com.epsilon.handler;

import java.util.concurrent.Callable;

import com.epsilon.model.movimentacao.HistoricoItens;

import modulos.efdicms.manager.LeitorEfdIcms;


public class CallableHistItensECFs implements Callable<HistoricoItens >{

	private ImportaEfdIcms importacoes = new ImportaEfdIcms();
	private LeitorEfdIcms leitor;
	private int i; 
	private int z; 
	private int l; 
	private int m;
	private Long lote;
	private int dia;
	
	public CallableHistItensECFs(LeitorEfdIcms leitor,int i, int z, int l, int m,Long lote,int dia) {
		
		this.leitor = leitor;
		this.lote = lote;
		this.i = i;
		this.z = z;
		this.l = l;
		this.m = m;
		this.dia = dia;
	}
	
	@Override
	public HistoricoItens call() throws Exception {
		HistoricoItens retorno = null;
		if(importacoes.insereReducoes(leitor, lote, i, z, l, m).getDtDoc().getDayOfMonth() <= dia) {

			 retorno = importacoes.insereReducoes(leitor, lote, i, z, l, m);
		}
		return retorno;
	}

}
