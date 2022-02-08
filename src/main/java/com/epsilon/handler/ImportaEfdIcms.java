package com.epsilon.handler;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
import com.epsilon.model.movimentacao.ItensMovDiario;
import com.epsilon.model.movimentacao.ItensMovDiarioCFe;
import com.epsilon.model.movimentacao.NotaFiscal;
import com.epsilon.model.movimentacao.ProdutoNotaFiscal;
import com.epsilon.model.movimentacao.ReducaoZ;
import com.epsilon.model.movimentacao.TotParciaisRDZ;
import com.epsilon.model.movimentacao.TotalizadorDiarioCuponsFiscais;
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


	public List<Produto> getProdutos() {
		  List<Produto> novoRetorno = produtos.stream().distinct().collect(Collectors.toList());
		return novoRetorno;
	}
	 
	 
	 
}
