package com.epsilon.handler;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.xml.bind.JAXBException;

import com.epsilon.dao.MetadadosDB;
import com.epsilon.dao.cadastro.EquipamentoEcfDao;
import com.epsilon.dao.cadastro.OutrasUnidDao;
import com.epsilon.dao.cadastro.ProdutoDao;
import com.epsilon.model.cadastro.EquipamentoECF;
import com.epsilon.model.cadastro.LoteImportacaoSpedFiscal;
import com.epsilon.model.cadastro.OutrasUnid;
import com.epsilon.model.cadastro.Produto;
import com.epsilon.model.movimentacao.EquipamentoCFe;
import com.epsilon.model.movimentacao.HistoricoItens;
import com.epsilon.model.movimentacao.ItensMovDiario;
import com.epsilon.model.movimentacao.ItensMovDiarioCFe;
import com.epsilon.model.movimentacao.NotaFiscal;
import com.epsilon.model.movimentacao.ProdutoNotaFiscal;
import com.epsilon.model.movimentacao.ReducaoZ;
import com.epsilon.model.movimentacao.SaldoItensTotalizadoPorLote;
import com.epsilon.model.movimentacao.TotParciaisRDZ;
import com.epsilon.model.movimentacao.TotalizadorDiarioCuponsFiscais;
import com.epsilon.util.UtilsEConverters;
import com.leetor4.handler.ParseDocXML;
import com.leetor4.model.nfe.DocumentoFiscalEltronico;
import com.leetor4.model.nfe.Produtos;


import modulos.efdicms.entidades.Reg0000;
import modulos.efdicms.entidades.Reg0200;
import modulos.efdicms.entidades.Reg0220;
import modulos.efdicms.entidades.RegC100;
import modulos.efdicms.entidades.RegC170;
import modulos.efdicms.entidades.RegC860;
import modulos.efdicms.manager.LeitorEfdIcms;

public class ImportaEfdIcms {

	private Set<String> listaProdutos = new LinkedHashSet<String>();
	private List<Produto> produtos = new ArrayList<Produto>();
	private Map<String,Produto> mpProdXmlProprio = new HashMap<String, Produto>();
	private ProdutoDao daoProd = new ProdutoDao();
	
	
	public LoteImportacaoSpedFiscal getLoteImportacao(LeitorEfdIcms leitor, Long idEmp, Long idEst) {
		LoteImportacaoSpedFiscal importacao = new LoteImportacaoSpedFiscal();

		for (Reg0000 lote : leitor.getRegs0000()) {
			
			importacao.setIdEmp(idEmp);
			importacao.setIdEst(idEst);
			importacao.setCodVersao(lote.getCodVer());
			importacao.setCodFinalidade(lote.getCodFin());
			importacao.setDtIni(lote.getDtIni());
			importacao.setDtFin(lote.getDtFin());
			importacao.setNome(lote.getNome());
			importacao.setCnpj(lote.getCnpj());
			importacao.setCpf(lote.getCpf());
			importacao.setUf(lote.getUf());
			importacao.setIe(lote.getIe());
			importacao.setCodMun(lote.getCodMun());
			importacao.setIM(lote.getIm());
			importacao.setSuframa(lote.getSuframa());
			importacao.setIndPerfil(lote.getIndPerfil());
			importacao.setIndAtiv(lote.getIndAtiv());
		}

		return importacao;
	}
	
	
	public List<Produto> getProdutosSped(LeitorEfdIcms leitor, Long idEmp, Long idEst) {
		
		List<Produto> retorno = new ArrayList<Produto>();
        for(Reg0200 prod :  leitor.getRegs0200()){
        	Produto p = new Produto();      	 
			p.setIdEmp(idEmp);
			p.setIdEst(idEst);
			p.setCodUtilizEstab(prod.getCodItem());
			p.setDescricao(prod.getDescrItem());
			p.setUnidadedeMedidaPadrao(prod.getUnidInv());
			p.setNcm(prod.getCodNcm());
			p.setCodigodeBarras(prod.getCodBarra());
			
			for(Reg0220 out : prod.getOutrasUndMedidas()){
				OutrasUnid outUnd = new OutrasUnid();
			    outUnd.setIdPaiEmp(idEmp);
			    outUnd.setIdPaiEst(idEst);
			    
			    //outUnd.setIdPai(idPaiReg0200(prod.getCodItem(),idEmp,idEst));
			    outUnd.setCodProd(prod.getCodItem());
			    outUnd.setUndMed(out.getUndConv());
			    outUnd.setUndEquivPadrao(out.getFatConv());
			    
			    p.adicionaOutrasUnd(outUnd);
			}
			
			
				retorno.add(p);	
			
			
        }
        return retorno;
	}
	
	public List<NotaFiscal> getNotasFiscaisTerceiros(LeitorEfdIcms leitor,Long idEmp,
			Long idEst,Long lote) {
		List<NotaFiscal> retorno = new ArrayList<NotaFiscal>();
		MetadadosDB banco = new MetadadosDB();
			for (RegC100 nota : leitor.getRegsC100()) {
				
				if(nota.getIndEmit().equals("1")) {
					NotaFiscal nf = new NotaFiscal();
            		
					nf.setIdPai(lote);
					nf.setIdEmp(idEmp);
					nf.setIdEst(idEst);

					nf.setTipoOperacao("E");
					

					if (nota.getIndEmit().equals("0")) {
						nf.setIndDocProprio("S");
					} else {
						nf.setIndDocProprio("N");
					}
					nf.setSituacaoDocumento(nota.getCodSit());
					nf.setCodRemetenteDestinatario(nota.getCodPart());
					nf.setEspecie(nota.getCodMod());
					nf.setSerie(nota.getSer());
					nf.setChaveEletronica(nota.getChvNfe());
					nf.setNumDoc(nota.getNumDoc());
					nf.setDataEmissao(nota.getDtDoc());
					nf.setDataEntradaSaida(nota.getDtEntSai());

					if (nota.getVlMerc() != null) {
						nf.setValorTotalProdutos(BigDecimal.valueOf(nota.getVlMerc()));
					} else {
						nf.setValorTotalProdutos(BigDecimal.valueOf(0.0));
					}

					if (nota.getVlFrt() != null) {
						nf.setValorFrete(BigDecimal.valueOf(nota.getVlFrt()));
					} else {
						nf.setValorFrete(BigDecimal.valueOf(0.0));
					}

					for (RegC170 pNF : nota.getProdutosNota()) {
						ProdutoNotaFiscal prod = new ProdutoNotaFiscal();
						prod.setIdPai(banco.getIncremento("tb_notafiscal"));
						prod.setNumItem(pNF.getNumItem());
						prod.setCodProduto(pNF.getCodItem());
						prod.setCfop(pNF.getCfop());
						prod.setCstA(pNF.getCstIcms().substring(0, 1));
						prod.setCstB(pNF.getCstIcms().substring(1, 3));
						prod.setQuantidade(BigDecimal.valueOf(pNF.getQtd()));
						prod.setUnidadeMedida(pNF.getUnid());
						prod.setValorBruto(BigDecimal.valueOf(pNF.getVlItem()));

									
						
						nf.adicionaProdutoNota(prod);

					}
					
					
					retorno.add(nf);
				}

			}



		return retorno;
	}
	
	public List<NotaFiscal> getNotasFiscaisProprios(LeitorEfdIcms leitor, String file,Long idEmp,
			Long idEst,Long lote) {
        MetadadosDB banco = new MetadadosDB();
        ParseDocXML parseDocXML = new ParseDocXML();
		File f = new File(file);
		List<NotaFiscal> retorno = new ArrayList<NotaFiscal>();
		
		try {
			for (DocumentoFiscalEltronico doc : parseDocXML.validaTipoDeParseNFE(f)) {
				
				if (doc.getIdent().getModeloDoc().equals("55")) {
					if (leitor.getMpNFporChave().get(doc.getIdent().getChaveeletronica()) != null) {

							if (leitor.getMpNFporChave().get(doc.getIdent().getChaveeletronica()).getIndEmit()
									.equals("0")) {
								NotaFiscal nf = new NotaFiscal();
								nf.setIdPai(lote);
								nf.setIdEmp(idEmp);
								nf.setIdEst(idEst);

								if (leitor.getMpNFporChave().get(doc.getIdent().getChaveeletronica()).getIndEmit()
										.equals("1")) {
									nf.setTipoOperacao("E");
								} else {
									nf.setTipoOperacao("S");
								}

								if (leitor.getMpNFporChave().get(doc.getIdent().getChaveeletronica()).getIndOper()
										.equals("0")) {
									nf.setIndDocProprio("S");
								} else {
									nf.setIndDocProprio("N");
								}

								nf.setSituacaoDocumento(
										leitor.getMpNFporChave().get(doc.getIdent().getChaveeletronica()).getCodSit());
								nf.setCodRemetenteDestinatario(
										leitor.getMpNFporChave().get(doc.getIdent().getChaveeletronica()).getCodPart());
								nf.setEspecie(
										leitor.getMpNFporChave().get(doc.getIdent().getChaveeletronica()).getCodMod());
								nf.setSerie(leitor.getMpNFporChave().get(doc.getIdent().getChaveeletronica()).getSer());
								nf.setChaveEletronica(
										leitor.getMpNFporChave().get(doc.getIdent().getChaveeletronica()).getChvNfe());
								nf.setNumDoc(
										leitor.getMpNFporChave().get(doc.getIdent().getChaveeletronica()).getNumDoc());
								nf.setDataEmissao(
										leitor.getMpNFporChave().get(doc.getIdent().getChaveeletronica()).getDtDoc());
								nf.setDataEntradaSaida(leitor.getMpNFporChave().get(doc.getIdent().getChaveeletronica())
										.getDtEntSai());

								if (leitor.getMpNFporChave().get(doc.getIdent().getChaveeletronica())
										.getVlMerc() != null) {
									nf.setValorTotalProdutos(BigDecimal.valueOf(leitor.getMpNFporChave()
											.get(doc.getIdent().getChaveeletronica()).getVlMerc()));
								} else {
									nf.setValorTotalProdutos(BigDecimal.valueOf(0.0));
								}

								if (leitor.getMpNFporChave().get(doc.getIdent().getChaveeletronica())
										.getVlFrt() != null) {
									nf.setValorFrete(BigDecimal.valueOf(leitor.getMpNFporChave()
											.get(doc.getIdent().getChaveeletronica()).getVlFrt()));
								} else {
									nf.setValorFrete(BigDecimal.valueOf(0.0));
								}

								for (Produtos pNF : doc.getProds()) {

									ProdutoNotaFiscal prod = new ProdutoNotaFiscal();
									prod.setIdPai(banco.getIncremento("tb_notafiscal"));
									prod.setNumItem(pNF.getNumItem());
									prod.setCodProduto(pNF.getCodItem());
									prod.setCfop(pNF.getCfop());
									prod.setCstA(pNF.getOrig());
									prod.setCstB(pNF.getCst());
									prod.setQuantidade(BigDecimal.valueOf(Double.valueOf(pNF.getQtdComercial())));
									prod.setUnidadeMedida(pNF.getUndComercial());

									if (pNF.getVlItem() != null) {
										prod.setValorBruto(BigDecimal.valueOf(Double.valueOf(pNF.getVlItem())));
									}

									//System.out.println(pNF.getCodItem() + "|" + pNF.getCfop());
									nf.adicionaProdutoNota(prod);
									if(!daoProd.listaTodos().contains(insereProdutosProprios(pNF, idEmp, idEst))){
										produtos.add(insereProdutosProprios(pNF, idEmp, idEst));
									}
									
								}
								retorno.add(nf);
							}
					}
				
		
				}	
			}
		} catch (IOException e) {
		
			e.printStackTrace();
		} catch (JAXBException e) {
			
			e.printStackTrace();
		}
		
		return retorno;
	}

	public List<ReducaoZ> getReducoes(LeitorEfdIcms leitor, Long idEmp, Long idEst,Long lote) {
		EquipamentoEcfDao dao = new EquipamentoEcfDao();
		List<ReducaoZ> retorno = new ArrayList<ReducaoZ>();

		for (int i = 0; i < leitor.getRegsC400().size(); i++) {
			EquipamentoECF equip = new EquipamentoECF();
			
			equip.setNumSerieFabECF(leitor.getRegsC400().get(i).getNumSerieFabECF());
			
			for (int z = 0; z < leitor.getRegsC400().get(i).getRegsC405().size(); z++) {
				ReducaoZ redz = new ReducaoZ();
				
				redz.setIdEmp(idEmp);
				redz.setIdEst(idEst);
				redz.setIdPai(lote);
				//redz.setId_ecf(dao.buscaPorNumFab(equip.getNumSerieFabECF()).getId());
				redz.setNumCOO(leitor.getRegsC400().get(i).getRegsC405().get(z).getNumCOOFin());
				redz.setPosicaoCRO(leitor.getRegsC400().get(i).getRegsC405().get(z).getPosicaoCRO());
				redz.setPosicaoRDZ(leitor.getRegsC400().get(i).getRegsC405().get(z).getPosicaoRDZ());
				redz.setDtReducaoZ(leitor.getRegsC400().get(i).getRegsC405().get(z).getDtDoc());
				redz.setVlGrandeTotal(BigDecimal.valueOf(leitor.getRegsC400().get(i).getRegsC405().get(z).getVlGrandeTotalFinal()));
				redz.setVlVendaBruta(BigDecimal.valueOf(leitor.getRegsC400().get(i).getRegsC405().get(z).getVlVendaBruta()));
				
				for (int l = 0; l < leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().size() ; l++) {
					TotParciaisRDZ totParcRdz = new TotParciaisRDZ();
					
					//totParcRdz.setId(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getId());
					//totParcRdz.setIdPai(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getIdPai());
					totParcRdz.setCodTotalizador(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getCodTotPar());
					totParcRdz.setDescNumTotalizador(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getDescrNrTot());
					totParcRdz.setNumTotalizador(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getNrTot());
					totParcRdz.setVlAcumuladoTotRedZ(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getVlAcumTot());
					
					
					for (int m = 0; m < leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().size() ; m++) {
						ItensMovDiario item = new ItensMovDiario();
						
						//item.setIdPai(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getIdPai());
						item.setIdPaiRedZ(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getIdPaiRedZ()-1);
						item.setCodItem(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getCodItem());
						item.setQtde(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getQtd());
						item.setUnd(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getUnd());
						item.setVlItem(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getVlItem());
						item.setVlPis(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getVlPis());
						item.setVlCofins(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getVlCofins());

						listaProdutos.add(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getCodItem());
					
						totParcRdz.adicionaItensMovDiario(item);
					}
					
					redz.adicionaTotParcRedZ(totParcRdz);
					
				}
				
				
				for (int n = 0; n < leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC490().size() ; n++) {
					TotalizadorDiarioCuponsFiscais totCF = new TotalizadorDiarioCuponsFiscais();					
					
					//totCF.setIdPai(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC490().get(n).getIdPai());
					totCF.setCfop(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC490().get(n).getCfop());
					totCF.setCst(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC490().get(n).getCstIcms());
					totCF.setVlOperacao(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC490().get(n).getVlOperacao());
					
					
					redz.adicionaTotalCuponsFiscais(totCF);
					
				}
				
				equip.adicionaReducoes(redz);	
				retorno.add(redz);
			}

			
		}

		return retorno;
	}
	
	
	public List<EquipamentoCFe> getEquipamentosCFe(LeitorEfdIcms leitor,  Long idEmp,
			Long idEst,Long lote) {
		List<EquipamentoCFe> retorno = new ArrayList<EquipamentoCFe>();
		
		for (RegC860 regC860 : leitor.getRegsC860()) {
			EquipamentoCFe equip = new EquipamentoCFe();
			equip.setCodModDocFiscal(regC860.getCodModDocFiscal());
			equip.setIdPai(lote);
			equip.setDocInicial(regC860.getDocInicial());
			equip.setDocFinal(regC860.getDocFinal());
			equip.setDtEmissao(regC860.getDtEmissao());
			equip.setNumSerieEquipSat(regC860.getNumSerieEquipSat());
			
			retorno.add(equip);
		}
		
		return retorno;
	}
	public List<ItensMovDiarioCFe> getItensCFe( LeitorEfdIcms leitor, String file, Long idEmp,
			Long idEst,Long lote) {
		
		ParseDocXML parseDocXML = new ParseDocXML();
		File f = new File(file);
		List<ItensMovDiarioCFe> retorno = new ArrayList<ItensMovDiarioCFe>();
			
		try {
			
			for (DocumentoFiscalEltronico doc : parseDocXML.validaTipoDeParseNFE(f)) {
				
				if (doc.getIdent().getModeloDoc().equals("59")) {

					for (Produtos p : doc.getProds()) {
						ItensMovDiarioCFe item = new ItensMovDiarioCFe();

						item.setIdPai(idPaiEquipCFe(doc.getIdent().getNumDoc(), leitor));
						//System.out.println( " CFe idEquip " + regC860.getId() +  " CFe idPai " + (idPaiEquipCFe(doc.getIdent().getNumDoc(), leitor)));

						item.setIdPaiEmp(idEmp);
						item.setIdPaiEst(idEst);
						item.setNumCFe(doc.getIdent().getNumDoc());
						item.setNumItem(p.getNumItem());
						item.setChaveCFe(doc.getIdent().getChaveeletronica());
						item.setCodItem(p.getCodItem());
						item.setCfop(p.getCfop());
						item.setCstIcms(p.getOrig().concat(p.getCst()));
						item.setUnd(p.getUndComercial());

						item.setQtde(Double.valueOf(p.getQtdComercial()));
						item.setVlItem(Double.valueOf(p.getVlItem()));
						if (p.getvDesc() != null) {
							item.setVlDesc(Double.valueOf(p.getvDesc()));
						} else {
							item.setVlDesc(0.0);
						}

						item.setVlProd(Double.valueOf(p.getVlProduto()));
						item.setVlUnit(Double.valueOf(p.getVlUnComerial()));

						listaProdutos.add(p.getCodItem());
						retorno.add(item);
						if(!daoProd.listaTodos().contains(insereProdutosProprios(p, idEmp, idEst))){
							produtos.add(insereProdutosProprios(p, idEmp, idEst));
						}
					}

				}
				
				
			}
		
		
		
		
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (JAXBException e) {
		
			e.printStackTrace();
		}
		
		
		return retorno;
	}
	
	
	public Long idPaiEquipCFe(String numDOc, LeitorEfdIcms leitor) {
		Long id = 0L;

		int num = Integer.valueOf(numDOc);
		for (Long key : leitor.getMpC860().keySet()) {

			if (num >= Integer.valueOf(leitor.getMpC860().get(key).getDocInicial())
					&& num <= Integer.valueOf(leitor.getMpC860().get(key).getDocFinal())) {

					id = leitor.getMpC860().get(key).getId();
				

			}
		}

		return id;
	}
	
	 public Produto insereProdutosSped(Reg0200 prod , Long idEmp, Long idEst) {
	    	Produto p = new Produto();
	 
			p.setIdEmp(idEmp);
			p.setIdEst(idEst);
			p.setCodUtilizEstab(prod.getCodItem());
			p.setDescricao(prod.getDescrItem());
			p.setUnidadedeMedidaPadrao(prod.getUnidInv());
			p.setNcm(prod.getCodNcm());
			p.setCodigodeBarras(prod.getCodBarra());
            
			
			listaProdutos.add(prod.getCodItem());
				
			
			
	    	return p;
	}
	 
	private OutrasUnid insereOutUndMedidas(LeitorEfdIcms leitor, OutrasUnid outUnd,int i,int x ,Long idPaiEmp, Long idPaiEst) {
		
	    outUnd.setIdPaiEmp(idPaiEmp);
	    outUnd.setIdPaiEst(idPaiEst);
	    
	    outUnd.setIdPai(idPaiReg0200(leitor.getRegs0200().get(i).getOutrasUndMedidas().get(x).getCodItem(),idPaiEmp,idPaiEst));
	    
	    outUnd.setUndMed(leitor.getRegs0200().get(i).getOutrasUndMedidas().get(x).getUndConv());
	    outUnd.setUndEquivPadrao(leitor.getRegs0200().get(i).getOutrasUndMedidas().get(x).getFatConv());
		
		return outUnd;
	}
	
	public Long idPaiReg0200(String codItem,Long idPaiEmp,Long idPaiEst)  {
		Long id = 0L;
		try {
			if(daoProd.getMpProdutos(idPaiEmp, idPaiEst).get(codItem) != null) {
				id = daoProd.getMpProdutos(idPaiEmp,idPaiEst).get(codItem).getId();
			}
		}catch (Exception e) {
			
		}		
		return id;
	}


	
	 
	 public Produto insereProdutosProprios(com.leetor4.model.nfe.Produtos prod , Long idEmp, Long idEst) {
	    	Produto p = new Produto();
	 
			p.setIdEmp(idEmp);
			p.setIdEst(idEst);
			p.setCodUtilizEstab(prod.getCodItem());
			p.setDescricao(prod.getDescricao());
			p.setUnidadedeMedidaPadrao(prod.getUndComercial());
			p.setNcm(prod.getNcm());
			p.setCodigodeBarras(prod.getCodEanTrib());

			listaProdutos.add(prod.getCodItem());
				
			
			
	    	return p;
	 }


	public List<HistoricoItens> getHistoricoItens1(LeitorEfdIcms leitor, String file, Long idEmp,
			Long idEst,Long lote){
		List<HistoricoItens>  retorno = new ArrayList<HistoricoItens>();
		ParseDocXML parseDocXML = new ParseDocXML();
		File f = new File(file);
		
		for (RegC100 nota : leitor.getRegsC100()) {
			for (RegC170 pNF : nota.getProdutosNota()) {
				if (insereNotasTerceiros(leitor, nota, pNF, file, lote).getChaveDoc() != null) {
					retorno.add(insereNotasTerceiros(leitor, nota, pNF, file, lote));
				}

			}
		}
		
		try {
			for (DocumentoFiscalEltronico doc : parseDocXML.validaTipoDeParseNFE(f)) {

				for (Produtos p : doc.getProds()) {
					if (insereNotasProprias(leitor, p, doc, lote).getChaveDoc() != null) {
						retorno.add(insereNotasProprias(leitor, p, doc, lote));
					}

				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		int dia10 = 10;
		ExecutorService ex1 = Executors.newCachedThreadPool();
		for (int i = 0; i < leitor.getRegsC400().size(); i++) {
            leituraEcf_ate_dia_10(ex1, leitor, idEmp, idEst, lote, i,dia10, retorno);
		}

		for (RegC860 regC860 : leitor.getRegsC860()) {			
			
			 leituraCFe_ate_dia_10(leitor, lote, parseDocXML, f, ex1, regC860,dia10,retorno);
		}
		ex1.shutdown();
		return retorno;
	}
	
	public List<HistoricoItens> getHistoricoItens2(LeitorEfdIcms leitor, String file, Long idEmp,
			Long idEst,Long lote){
	
		ParseDocXML parseDocXML = new ParseDocXML();
		File f = new File(file);
		List<HistoricoItens>  retorno = new ArrayList<HistoricoItens>();
		int dia20 = 20;
		ExecutorService ex2 = Executors.newCachedThreadPool();
		
		for (int i = 0; i < leitor.getRegsC400().size(); i++) {
            leituraEcf_entre_dia_10_a_20(ex2, leitor, idEmp, idEst, lote, i,dia20, retorno);
		}
		
		for (RegC860 regC860 : leitor.getRegsC860()) {	
			 leituraCFe_entre_dia_10_a_20(leitor, lote, parseDocXML, f, ex2, regC860,dia20,retorno);			
		}		
		ex2.shutdown();
		return retorno;
	}
	
	
	public List<HistoricoItens> getHistoricoItens3(LeitorEfdIcms leitor, String file, Long idEmp,
			Long idEst,Long lote){
		
		ParseDocXML parseDocXML = new ParseDocXML();
		File f = new File(file);
		List<HistoricoItens>  retorno = new ArrayList<HistoricoItens>();
		int dia30 = 31;
		ExecutorService ex3 = Executors.newCachedThreadPool();
		for (int i = 0; i < leitor.getRegsC400().size(); i++) {
            leituraEcf_entre_dia_20_a_31(ex3, leitor, idEmp, idEst, lote, i,dia30, retorno);
		}
		for (RegC860 regC860 : leitor.getRegsC860()) {				
			  leituraCFe_entre_dia_20_a_31(leitor, lote, parseDocXML, f, ex3, regC860,dia30,retorno);
		}
		ex3.shutdown();
		return retorno;
	}
	
	private void leituraEcf_ate_dia_10(ExecutorService ex, LeitorEfdIcms leitor,Long idEmp,
			Long idEst,Long lote,int i,int dia,List<HistoricoItens>  retorno) {
		
		for (int z = 0; z < leitor.getRegsC400().get(i).getRegsC405().size(); z++) {
			
			if(leitor.getRegsC400().get(i).getRegsC405().get(z).getDtDoc().getDayOfMonth() <= dia) {
				for (int l = 0; l < leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().size(); l++) {
					for (int m = 0; m < leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l)
							.getRegsC425().size(); m++) {
	
						if (insereReducoes(leitor, lote, i, z, l, m) != null) {
							
							CallableHistItensECFs hist = new CallableHistItensECFs(leitor, i, z, l, m, lote, dia);
							Future<HistoricoItens> submit = ex.submit(hist);
							try {
								retorno.add(submit.get());
							    System.out.println(submit.get().getDtDoc().getDayOfMonth() + "|" + leitor.getRegsC400().get(i).getRegsC405().get(z).getPosicaoRDZ());

							} catch (InterruptedException e) {
								
								e.printStackTrace();
							} catch (ExecutionException e) {
								
								e.printStackTrace();
							}
						}
	
					}
				}
			}

		}
		
	}

	private void leituraEcf_entre_dia_10_a_20(ExecutorService ex, LeitorEfdIcms leitor,Long idEmp,
			Long idEst,Long lote,int i,int dia,List<HistoricoItens>  retorno) {
		
		for (int z = 0; z < leitor.getRegsC400().get(i).getRegsC405().size(); z++) {
			
			if(leitor.getRegsC400().get(i).getRegsC405().get(z).getDtDoc().getDayOfMonth() > 10
					&& leitor.getRegsC400().get(i).getRegsC405().get(z).getDtDoc().getDayOfMonth() <= dia) {
				
				for (int l = 0; l < leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().size(); l++) {
					for (int m = 0; m < leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l)
							.getRegsC425().size(); m++) {
	
						if (insereReducoes(leitor, lote, i, z, l, m) != null) {
							
							CallableHistItensECFs hist = new CallableHistItensECFs(leitor, i, z, l, m, lote, dia);
							Future<HistoricoItens> submit = ex.submit(hist);
							try {
								retorno.add(submit.get());
							    System.out.println(submit.get().getDtDoc().getDayOfMonth() + "|" + leitor.getRegsC400().get(i).getRegsC405().get(z).getPosicaoRDZ());

							} catch (InterruptedException e) {
								
								e.printStackTrace();
							} catch (ExecutionException e) {
								
								e.printStackTrace();
							}
						}
	
					}
				}
			}

		}
		
	}
	
	private void leituraEcf_entre_dia_20_a_31(ExecutorService ex, LeitorEfdIcms leitor,Long idEmp,
			Long idEst,Long lote,int i,int dia,List<HistoricoItens>  retorno) {
		
		for (int z = 0; z < leitor.getRegsC400().get(i).getRegsC405().size(); z++) {
			
			if(leitor.getRegsC400().get(i).getRegsC405().get(z).getDtDoc().getDayOfMonth() > 20
					&& leitor.getRegsC400().get(i).getRegsC405().get(z).getDtDoc().getDayOfMonth() <= dia) {
				
				for (int l = 0; l < leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().size(); l++) {
					for (int m = 0; m < leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l)
							.getRegsC425().size(); m++) {
	
						if (insereReducoes(leitor, lote, i, z, l, m) != null) {
							
							CallableHistItensECFs hist = new CallableHistItensECFs(leitor, i, z, l, m, lote, dia);
							Future<HistoricoItens> submit = ex.submit(hist);
							try {
								retorno.add(submit.get());
							    System.out.println(submit.get().getDtDoc().getDayOfMonth() + "|" + leitor.getRegsC400().get(i).getRegsC405().get(z).getPosicaoRDZ());

							} catch (InterruptedException e) {
								
								e.printStackTrace();
							} catch (ExecutionException e) {
								
								e.printStackTrace();
							}
						}
	
					}
				}
			}

		}
		
	}
	
	private void leituraCFe_ate_dia_10(LeitorEfdIcms leitor, Long lote, ParseDocXML parseDocXML, File f,
			ExecutorService ex, RegC860 regC860, int dia, List<HistoricoItens> retorno) {
		if (regC860.getDtEmissao().getDayOfMonth() <= dia) {

			try {
				for (DocumentoFiscalEltronico doc : parseDocXML.validaTipoDeParseNFE(f)) {
					if (regC860.getId() == idPaiEquipCFe(doc.getIdent().getNumDoc(), leitor)) {

						for (Produtos p : doc.getProds()) {
							if (doc.getIdent().getModeloDoc().equals("59")) {

								if (insereCFes(leitor, regC860, f, p, doc, lote).getDtDoc().getDayOfMonth() <= dia) {
									// retorno.add(insereCFes(leitor,regC860, file, p, doc, lote));
									CallableHistItensCFes hist = new CallableHistItensCFes(leitor, regC860, f, p, doc,
											lote, dia);
									// System.out.println(insereCFes(leitor,regC860 ,f, p, doc,
									// lote).getDtDoc().getDayOfMonth());
									Future<HistoricoItens> submit = ex.submit(hist);
									try {
										retorno.add(submit.get());
										System.out.println(submit.get().getDtDoc().getDayOfMonth() + "|"
												+ regC860.getNumSerieEquipSat());

									} catch (InterruptedException e) {

										e.printStackTrace();
									} catch (ExecutionException e) {

										e.printStackTrace();
									}
								}

							}
						}
					}
				}

			} catch (IOException e) {

				e.printStackTrace();
			} catch (JAXBException e) {

				e.printStackTrace();
			}

		}
	}
	
	
	
	private void leituraCFe_entre_dia_10_a_20(LeitorEfdIcms leitor, Long lote, ParseDocXML parseDocXML, File f,
			ExecutorService ex, RegC860 regC860,int dia,List<HistoricoItens>  retorno) {
		if(regC860.getDtEmissao().getDayOfMonth()  > 10 && regC860.getDtEmissao().getDayOfMonth() <= dia) {
				try {
					for (DocumentoFiscalEltronico doc : parseDocXML.validaTipoDeParseNFE(f)) {
						if (regC860.getId() == idPaiEquipCFe(doc.getIdent().getNumDoc(), leitor)) {
							
							for (Produtos p : doc.getProds()) {
								if (doc.getIdent().getModeloDoc().equals("59")) {
									
									if(insereCFes(leitor,regC860 ,f, p, doc, lote).getDtDoc().getDayOfMonth() > 10
											 && insereCFes(leitor,regC860 ,f, p, doc, lote).getDtDoc().getDayOfMonth() <= dia) {
										//retorno.add(insereCFes(leitor,regC860, file, p, doc, lote));
										CallableHistItensCFes hist = new CallableHistItensCFes(leitor,regC860 ,f, p, doc, lote, dia);
										//System.out.println(insereCFes(leitor,regC860 ,f, p, doc, lote).getDtDoc().getDayOfMonth());
										Future<HistoricoItens> submit = ex.submit(hist);
										try {
											retorno.add(submit.get());
										    System.out.println(submit.get().getDtDoc().getDayOfMonth() + "|" + regC860.getNumSerieEquipSat());

										} catch (InterruptedException e) {
											
											e.printStackTrace();
										} catch (ExecutionException e) {
											
											e.printStackTrace();
										}
									}

									
								}
							}
						}
					}

				} catch (IOException e) {
				
					e.printStackTrace();
				} catch (JAXBException e) {
					
					e.printStackTrace();
				}
		  }
	}
	
	private void leituraCFe_entre_dia_20_a_31(LeitorEfdIcms leitor, Long lote, ParseDocXML parseDocXML, File f,
			ExecutorService ex, RegC860 regC860,int dia,List<HistoricoItens>  retorno) {
		if(regC860.getDtEmissao().getDayOfMonth()  > 20 && regC860.getDtEmissao().getDayOfMonth() <= dia) {
				try {
					for (DocumentoFiscalEltronico doc : parseDocXML.validaTipoDeParseNFE(f)) {
						if (regC860.getId() == idPaiEquipCFe(doc.getIdent().getNumDoc(), leitor)) {
							
							for (Produtos p : doc.getProds()) {
								if (doc.getIdent().getModeloDoc().equals("59")) {
									
									if(insereCFes(leitor,regC860 ,f, p, doc, lote).getDtDoc().getDayOfMonth() > 20
											 && insereCFes(leitor,regC860 ,f, p, doc, lote).getDtDoc().getDayOfMonth() <= dia) {
										//retorno.add(insereCFes(leitor,regC860, file, p, doc, lote));
										CallableHistItensCFes hist = new CallableHistItensCFes(leitor,regC860 ,f, p, doc, lote, dia);
										//System.out.println(insereCFes(leitor,regC860 ,f, p, doc, lote).getDtDoc().getDayOfMonth());
										Future<HistoricoItens> submit = ex.submit(hist);
										try {
											retorno.add(submit.get());
										    System.out.println(submit.get().getDtDoc().getDayOfMonth() + "|" + regC860.getNumSerieEquipSat());

										} catch (InterruptedException e) {
											
											e.printStackTrace();
										} catch (ExecutionException e) {
											
											e.printStackTrace();
										}
									}

									
								}
							}
						}
					}

				} catch (IOException e) {
				
					e.printStackTrace();
				} catch (JAXBException e) {
					
					e.printStackTrace();
				}
		  }
	}
	
	private HistoricoItens insereNotasTerceiros(LeitorEfdIcms leitor,RegC100 nota,RegC170 pNF , String file,Long lote) {
		
	    HistoricoItens retorno = new HistoricoItens();
		retorno.setIdPaiLote(lote);
		retorno.setIdPai(pNF.getIdPai());

		retorno.setEmpresa(leitor.getRegs0000().get(0).getCnpj());

		if (nota.getIndOper().equals("0")) {
			retorno.setOperacao("E");
		} else {
			retorno.setOperacao("S");
		}
		
		
		retorno.setEcfCx("");
		retorno.setDtDoc(nota.getDtDoc());
		retorno.setNumItem(pNF.getNumItem());
		retorno.setCodItem(pNF.getCodItem());
		retorno.setQtde(BigDecimal.valueOf(pNF.getQtd()));
		retorno.setUnd(pNF.getUnid());
		
		
		retorno.setVlUnit(BigDecimal.valueOf(pNF.getVlItem()/pNF.getQtd()));
		retorno.setVlBruto(BigDecimal.valueOf(pNF.getVlItem()));
		
		//Verificar esse set do Desconto
		retorno.setDesconto(BigDecimal.valueOf(0.0));
		
		retorno.setVlLiq(BigDecimal.valueOf(pNF.getVlItem()));
		
		retorno.setCfop(pNF.getCfop());
		retorno.setCst(pNF.getCstIcms());
		
		if((pNF.getAliqIcms() != null)) {
			retorno.setAliqIcms(BigDecimal.valueOf(pNF.getAliqIcms()));
		}else {
			retorno.setAliqIcms(BigDecimal.valueOf(0.0));
		}
		
		retorno.setCodSitDoc(nota.getCodSit());
		retorno.setCodMod(nota.getCodMod());
		
		if(leitor.getMpProdTerc().get(pNF.getCodItem()) != null) {
			retorno.setDescricao(leitor.getMpProdTerc().get(pNF.getCodItem()).getDescrItem());
		}
		
		
		retorno.setNumDoc(nota.getNumDoc());
		retorno.setChaveDoc(nota.getChvNfe());
		
		if(leitor.getMpParticipante().get(nota.getCodPart()) != null) {
			retorno.setNome(leitor.getMpParticipante().get(nota.getCodPart()).getNome());
		}
		
		String doc = "";
		
        if(leitor.getMpParticipante().get(nota.getCodPart()) != null) {
        	doc += leitor.getMpParticipante().get(nota.getCodPart()).getCnpj();            	
		}
		
        if(leitor.getMpParticipante().get(nota.getCodPart()) != null) {
        	doc += leitor.getMpParticipante().get(nota.getCodPart()).getCpf();           	
		}
        
        if(doc != null) {
        	retorno.setCpfCnpj(doc);
        }

	 return retorno;	

   }
	
	public HistoricoItens insereNotasProprias(LeitorEfdIcms leitor,Produtos p, DocumentoFiscalEltronico doc,Long lote) {
		 HistoricoItens retorno = new HistoricoItens();
		 
		 
	 if(leitor.getMpNFporChave().get(doc.getIdent().getChaveeletronica()) != null) {
		 
		 if (!leitor.getMpNFporChave().get(doc.getIdent().getChaveeletronica()).getCodSit().equals("02")) {
				
			 if (doc.getIdent().getModeloDoc().equals("55")) {
				 
			 if(leitor.getMpNFporChave().get(doc.getIdent().getChaveeletronica()) != null){
				 

						retorno.setIdPaiLote(lote);

						if (leitor.getMpNFporChave().get(doc.getIdent().getChaveeletronica()) != null) {
							retorno.setIdPai(leitor.getMpNFporChave().get(doc.getIdent().getChaveeletronica()).getId()-1);
						}
						if (leitor.getMpNFporChave().get(doc.getIdent().getChaveeletronica()) != null) {
							retorno.setCodSitDoc(
									leitor.getMpNFporChave().get(doc.getIdent().getChaveeletronica()).getCodSit());
							
						}

						retorno.setEmpresa(leitor.getRegs0000().get(0).getCnpj());

						if (p.getCfop().startsWith("1") || p.getCfop().startsWith("2")) {
							retorno.setOperacao("E");
						} else {
							retorno.setOperacao("S");
						}

						retorno.setEcfCx("");
						retorno.setDtDoc(UtilsEConverters.getStringParaData(doc.getIdent().getDataEmissao()));
						retorno.setNumItem(p.getNumItem());
						retorno.setCodItem(p.getCodItem());
						retorno.setQtde(BigDecimal.valueOf(Double.valueOf(p.getQtdComercial())));
						retorno.setUnd(p.getUndComercial());

						retorno.setVlUnit(BigDecimal.valueOf(Double.valueOf(p.getVlUnComerial())));
						retorno.setVlBruto(BigDecimal.valueOf(Double.valueOf(p.getVlProduto())));
						retorno.setDesconto(null);

						if (p.getVlItem() != null) {
							retorno.setVlLiq(BigDecimal.valueOf(Double.valueOf(p.getVlItem())));
						}else {
							retorno.setVlLiq(BigDecimal.valueOf(Double.valueOf(p.getVlProduto())));
						}

						retorno.setCfop(p.getCfop());

						if (p.getOrig() != null && p.getCst() != null) {
							retorno.setCst(p.getOrig().concat(p.getCst()));
						}

						if ((p.getAliqIcms() != null)) {
							retorno.setAliqIcms(BigDecimal.valueOf(Double.valueOf(p.getAliqIcms())));
						} else {
							retorno.setAliqIcms(BigDecimal.valueOf(0.0));
						}

						retorno.setCodMod(doc.getIdent().getModeloDoc());

						retorno.setDescricao(p.getDescricao());

						retorno.setNumDoc(doc.getIdent().getNumDoc());
						retorno.setChaveDoc(doc.getIdent().getChaveeletronica());

						retorno.setNome(doc.getDestinatario().getNome());

						String cpfCnpj = "";
						if(doc.getDestinatario().getCnpj() != null) {
							 cpfCnpj += doc.getDestinatario().getCnpj();
						}
						if(doc.getDestinatario().getCpf() != null) {
							 cpfCnpj += doc.getDestinatario().getCpf();
						}
						if(cpfCnpj != null) {
							retorno.setCpfCnpj(cpfCnpj);
						}
						

					}
				 }
			 }
	 }
		return retorno;
	}
	
	public HistoricoItens insereReducoes(LeitorEfdIcms leitor,Long lote, int i, int z, int l, int m) {
		EquipamentoEcfDao dao = new EquipamentoEcfDao();
		HistoricoItens retorno = new HistoricoItens();

							
		retorno.setIdPaiLote(lote);
		retorno.setCodMod(leitor.getRegsC400().get(i).getCodModelo());
		retorno.setIdPai(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getIdPai()-1);
		retorno.setEmpresa(leitor.getRegs0000().get(0).getCnpj());
		retorno.setOperacao("S");
		retorno.setCodSitDoc("");
		retorno.setNumItem("");
		retorno.setEcfCx(dao.buscaPorNumFab(leitor.getRegsC400().get(i).getNumSerieFabECF()).getNumECF());
		retorno.setDtDoc(leitor.getRegsC400().get(i).getRegsC405().get(z).getDtDoc());
		retorno.setCodItem(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getCodItem());
		retorno.setQtde(BigDecimal.valueOf(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getQtd()));
		retorno.setUnd(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getUnd());
		retorno.setVlUnit(BigDecimal.valueOf(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getVlItem()/leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getQtd()));
		retorno.setVlLiq(BigDecimal.valueOf(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getVlItem()));
		retorno.setVlBruto(BigDecimal.valueOf(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getVlItem()));
		retorno.setCfop(leitor.getMpC490().get(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getIdPaiRedZ()).getCfop());
		retorno.setCst(leitor.getMpC490().get(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getIdPaiRedZ()).getCstIcms());
		if(leitor.getMpProdTerc().get(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getCodItem()) != null) {
			retorno.setDescricao(leitor.getMpProdTerc().get(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getCodItem()).getDescrItem());
		}
		retorno.setNumDoc(leitor.getRegsC400().get(i).getRegsC405().get(z).getNumCOOFin());
		retorno.setAliqIcms(BigDecimal.valueOf(0.0));
		retorno.setDesconto(BigDecimal.valueOf(0.0));
		retorno.setChaveDoc("");
		retorno.setNome("");
		retorno.setCpfCnpj("");
										
		return retorno;
	}
	public HistoricoItens insereCFes(LeitorEfdIcms leitor,RegC860 regC860,File file,Produtos p,DocumentoFiscalEltronico doc, Long lote) {
		
		HistoricoItens retorno = new HistoricoItens();
			
		retorno.setIdPaiLote(lote);
		//retorno.setIdPai(idPaiEquipCFe(doc.getIdent().getNumDoc(), leitor));
		retorno.setEmpresa(leitor.getRegs0000().get(0).getCnpj());
		retorno.setOperacao("S");
		retorno.setEcfCx(regC860.getNumSerieEquipSat());
		retorno.setDtDoc(regC860.getDtEmissao());
		retorno.setCodItem(p.getCodItem());
		retorno.setQtde(BigDecimal.valueOf(Double.valueOf(p.getQtdComercial())));
		retorno.setUnd(p.getVlUnComerial());
		
		retorno.setVlUnit(BigDecimal.valueOf(Double.valueOf(p.getVlUnComerial())));
		
		retorno.setVlBruto(BigDecimal.valueOf(Double.valueOf(p.getVlProduto())));
		
		if(p.getvDesc() != null) {
			retorno.setDesconto(BigDecimal.valueOf(Double.valueOf(p.getvDesc())));
		}else {
			retorno.setDesconto(BigDecimal.valueOf(0.0));
		}
		
		
		if(p.getVlItem() != null) {
			retorno.setVlLiq(BigDecimal.valueOf(Double.valueOf(p.getVlItem())));	
		}
		
		
		retorno.setCfop(p.getCfop());
		retorno.setCst(p.getOrig().concat(p.getCst()));
		retorno.setCodSitDoc("");
		if(p.getAliqIcms() != null) {
			retorno.setAliqIcms(BigDecimal.valueOf(Double.valueOf(p.getAliqIcms())));
		}else {
			
			retorno.setAliqIcms(BigDecimal.valueOf(0.0));
		}
		
		
		retorno.setCodMod(regC860.getCodModDocFiscal());
		retorno.setDescricao(p.getDescricao());
		retorno.setNumDoc(doc.getIdent().getNumDoc());
		retorno.setNumItem(p.getNumItem());
		retorno.setChaveDoc(doc.getIdent().getChaveeletronica());
		retorno.setNome(doc.getDestinatario().getNome());
		
		String cnpjCpf = "";
		if(doc.getDestinatario().getCnpj() != null) {
			cnpjCpf += doc.getDestinatario().getCnpj();
		}
		
		if(doc.getDestinatario().getCpf() != null) {
			cnpjCpf += doc.getDestinatario().getCnpj();
		}
		
		if(cnpjCpf != null) {
			retorno.setCpfCnpj(cnpjCpf);
		}
		
		
		
		return retorno;
	}
	
	 public List<SaldoItensTotalizadoPorLote> getSaldoItensPorLote(LeitorEfdIcms leitor,Set<String> listaProdutos,List<HistoricoItens> histItem,
				Long lote){
			List<SaldoItensTotalizadoPorLote> retorno = new ArrayList<SaldoItensTotalizadoPorLote>();
			Double qtdeEntSum = 0.0;
			Double vlEntTotalSum = 0.0;
			Double qtdeSaiSum = 0.0;
			Double vlSaiTotalSum = 0.0;
			DecimalFormat df = new DecimalFormat("#,###.00");
			for(String codigo : listaProdutos){
				
				SaldoItensTotalizadoPorLote saldo = new SaldoItensTotalizadoPorLote();
			
				qtdeEntSum = histItem.stream()
						          .filter(c -> c.getCodItem().equals(codigo))
				                  .filter(c -> (c.getOperacao().equals("E")))
				                  .filter(c -> (c.getVlLiq() != null))
				                  .map(HistoricoItens:: getQtde).mapToDouble(BigDecimal::doubleValue).sum();		
				
				vlEntTotalSum = histItem.stream()
						.filter(c -> c.getCodItem().equals(codigo))
						.filter(c -> (c.getOperacao().equals("E")))
						.filter(c -> (c.getVlLiq() != null))
						.map(HistoricoItens:: getVlLiq).mapToDouble(BigDecimal::doubleValue).sum();

				
				qtdeSaiSum = histItem.stream().filter(c -> c.getCodItem().equals(codigo))
						.filter(c -> (c.getOperacao().equals("S")))
						.filter(c -> (c.getVlLiq() != null))
						.map(HistoricoItens:: getQtde).mapToDouble(BigDecimal::doubleValue).sum();
				vlSaiTotalSum = histItem.stream().filter(c -> c.getCodItem().equals(codigo))
						.filter(c -> (c.getOperacao().equals("S")))
						.filter(c -> (c.getVlLiq() != null))
						.map(HistoricoItens::getVlLiq).mapToDouble(BigDecimal::doubleValue).sum();
				
				
				saldo.setCnpj(leitor.getRegs0000().get(0).getCnpj());
				saldo.setAno(String.valueOf(leitor.getRegs0000().get(0).getDtIni().getYear()));
				saldo.setMes(String.valueOf(leitor.getRegs0000().get(0).getDtIni().getMonthValue()));
				
				if(leitor.getMpProdTerc().get(codigo) != null) {
					saldo.setIdCodItem(leitor.getMpProdTerc().get(codigo).getId());
				}
				
				String descr = "";
				if(leitor.getMpProdTerc().get(codigo) != null) {
					descr += leitor.getMpProdTerc().get(codigo).getDescrItem();
				}else if(mpProdXmlProprio.get(codigo) != null) {
					descr += mpProdXmlProprio.get(codigo).getDescricao();
				}
				
				if(descr != null){
					saldo.setDescricao(descr);
				}
				
				saldo.setIdPaiLote(lote);
				saldo.setCodItem(codigo);
				
//				BigDecimal saldoInicial = BigDecimal.valueOf(getSaldoIncial(
//						leitor.getRegs0000().get(0).getCnpj(),
//						codigo,
//						leitor.getRegs0000().get(0).getDtIni().getMonthValue(), 
//						leitor.getRegs0000().get(0).getDtIni().getYear()));
				
				
				saldo.setSaldoIni(BigDecimal.valueOf(0));
				
				saldo.setTotQtdeEnt(BigDecimal.valueOf(qtdeEntSum));
				saldo.setTotVlEnt(BigDecimal.valueOf(vlEntTotalSum));
				
				saldo.setTotQtdeSai(BigDecimal.valueOf(qtdeSaiSum));
				saldo.setTotVlSai(BigDecimal.valueOf(vlSaiTotalSum));
				
				
				
				if(saldo != null) {
					retorno.add(saldo);	
				}
				
			}
			
			return retorno;
		}
	 
	public List<Produto> getProdutos() {
		  List<Produto> novoRetorno = produtos.stream().distinct().collect(Collectors.toList());
		return novoRetorno;
	}
	 
	 
	 
}
