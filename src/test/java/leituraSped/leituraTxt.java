package leituraSped;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.epsilon.dao.MetadadosDB;
import com.epsilon.dao.cadastro.LoteImportacaoSpedFiscalDao;
import com.epsilon.dao.cadastro.ProdutoDao;
import com.epsilon.dao.movimentacao.EquipamentoCFeDao;
import com.epsilon.dao.movimentacao.ItensMovDiarioCFeDao;
import com.epsilon.dao.movimentacao.NotaFiscalDao;
import com.epsilon.handler.ImportaEfdIcms;
import com.epsilon.model.cadastro.LoteImportacaoSpedFiscal;
import com.epsilon.model.cadastro.Produto;
import com.epsilon.model.movimentacao.EquipamentoCFe;
import com.epsilon.model.movimentacao.ItensMovDiarioCFe;
import com.epsilon.model.movimentacao.NotaFiscal;
import com.epsilon.model.movimentacao.ProdutoNotaFiscal;
import com.leetor4.handler.ParseDocXML;

import modulos.efdicms.entidades.Reg0200;
import modulos.efdicms.entidades.Reg0220;
import modulos.efdicms.manager.LeitorEfdIcms;

public class leituraTxt {

	public static String linha(Reg0200 prod) {
		String lin = prod.getCodItem();
		for(Reg0220 out : prod.getOutrasUndMedidas()){
		
			    lin += "|";
				lin += out.getFatConv();
		}
		
		return lin;
	}
	
	public static void main(String[] args) {

		ProdutoDao pDao = new ProdutoDao();
		String ano = "2019";
		String emp = "SELLENE";
		String estab = "MEGADIET";
		String cnpj  = "05329222000419";
		String anomes1 = ano.concat("01").concat(".txt");
		String anomes11 = ano.concat("11").concat(".txt");
		Path x1 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\jan"));
		Path p1 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes1));
			
		Path x11 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\nov"));
	    Path p11 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes11));
		
	    Path p = p1;
		//Path x = x1;
		
	    LeitorEfdIcms leitor = new LeitorEfdIcms();
		leitor.leitorSpedFiscal(p,0L,0L,0L,0L,0L,0L, 0L );
		
        String item = "33";
		
//		for(Reg0200 prod : leitor.getRegs0200()){
//			if(prod.getCodItem().equals(item) || prod.getCodItem().equals("370")) {
//				Produto pr = new Produto();      	 
//				pr.setIdEmp(1L);
//				pr.setIdEst(6L);
//				pr.setCodUtilizEstab(prod.getCodItem());
//				pr.setDescricao(prod.getDescrItem());
//				pr.setUnidadedeMedidaPadrao(prod.getUnidInv());
//				pr.setNcm(prod.getCodNcm());
//				pr.setCodigodeBarras(prod.getCodBarra());
//				
//				
//				
//				//pDao.listaTodos().contains(pr);
//					
//				
//				
////				String lin = prod.getCodItem();
////				for(Reg0220 out : prod.getOutrasUndMedidas()){
////				
////					    lin += "|";
////						lin += out.getFatConv();
////						
////
////				}
//		
//				if(pDao.listaTodos().contains(pr)){					
//					if(pDao.produtoJoinOutUnidadeMedida(1L,2L).contains(linha(prod)) == true){
//						System.out.println("Produto já cadastrado -> " + linha(prod));
//					}else {					
//						Produto getProd = pDao.buscaPorCodigo(prod.getCodItem());						
//						System.out.println("Alterando o produto -> " + getProd.getCodUtilizEstab());
//					}						
//				}else {
//					    System.out.println("Cadastrando produto -> " + linha(prod));
//				}
//			}	
//		}
		
//		for(String s : pDao.produtoJoinOutUnidadeMedida()){
//			System.out.println(s);
//		}
		     //pDao.produtoJoinOutUnidadeMedida().contains("20377|20.0");
		     

		     
	}
}
