package leituraSped;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import modulos.efdicms.entidades.RegC170;
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
		leitor.leitorSpedFiscal(p,0L,0L,0L,0L,0L, 0L );
		
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
		     
          
            List<Totalizador> lista = new ArrayList<Totalizador>();
            for(RegC170 c170 : leitor.getRegsC170() ){
            	lista.add(new Totalizador(c170.getCodItem(), c170.getQtd(), c170.getVlItem()));       	
            }
            
        	Map<String, List<Totalizador>> prodTotalizadosQtde = new HashMap<String, List<Totalizador>>();
        	
        	for(Totalizador totalizadorProd : lista){
        		String codigo = totalizadorProd.codigo;
        		List<Totalizador> prodEncontrado = prodTotalizadosQtde.get(codigo);
        		if(prodEncontrado == null) { 
        			prodEncontrado = new ArrayList<Totalizador>();
        			prodEncontrado.add(totalizadorProd);
        			prodTotalizadosQtde.put(codigo, prodEncontrado);
        			continue;
        		}
        		prodEncontrado.add(totalizadorProd);
        	}
        	
        	
        	for(String key : prodTotalizadosQtde.keySet()){       		
        		System.out.println(totais(key, prodTotalizadosQtde).codigo 
        				+"|"+ totais(key, prodTotalizadosQtde).quantidade
        				+"|"+ totais(key, prodTotalizadosQtde).valor);
        	}
        	
//            for(String key : prodTotalizadosQtde.keySet()){
//            	 System.out.println(prodTotalizadosQtde.get(key));            	            	
//            }
		     
	}
	
	public static Totalizador totais(String key,Map<String, List<Totalizador>> prodTotalizadosQtde) {
		Double qtde = 0.0;
		Double vl = 0.0;
		Totalizador retorno = new Totalizador();
		for(Totalizador z : prodTotalizadosQtde.get(key)){
			qtde += z.quantidade;
			vl += z.valor;	
		}
		retorno.codigo = key;
		retorno.quantidade = qtde;
		retorno.valor = vl;
		return retorno;
	}
}



class Totalizador{
	
	String codigo;
	Double quantidade;
	Double valor;
	
	public Totalizador() {
		
	}
	Totalizador(String codigo,Double quantidade,Double valor){
		this.codigo = codigo;
		this.quantidade = quantidade;
		this.valor = valor;
	}
	
	@Override
	public String toString() {
		
		return "Codigo " + this.codigo + " , Qtde : " + this.quantidade+ " , Valor : " + this.valor;
	}
}
