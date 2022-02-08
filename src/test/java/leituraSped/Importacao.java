package leituraSped;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import com.epsilon.dao.MetadadosDB;
import com.epsilon.dao.cadastro.LoteImportacaoSpedFiscalDao;
import com.epsilon.dao.cadastro.ProdutoDao;
import com.epsilon.dao.movimentacao.EquipamentoCFeDao;
import com.epsilon.dao.movimentacao.ItensMovDiarioCFeDao;
import com.epsilon.dao.movimentacao.NotaFiscalDao;
import com.epsilon.dao.movimentacao.ReducaoZDao;
import com.epsilon.handler.ImportaEfdIcms;
import com.epsilon.model.cadastro.LoteImportacaoSpedFiscal;
import com.epsilon.model.cadastro.OutrasUnid;
import com.epsilon.model.cadastro.Produto;
import com.epsilon.model.movimentacao.EquipamentoCFe;
import com.epsilon.model.movimentacao.ItensMovDiarioCFe;
import com.epsilon.model.movimentacao.NotaFiscal;
import com.epsilon.model.movimentacao.ProdutoNotaFiscal;
import com.epsilon.model.movimentacao.ReducaoZ;

import modulos.efdicms.entidades.Reg0200;
import modulos.efdicms.entidades.Reg0220;
import modulos.efdicms.manager.LeitorEfdIcms;

public class Importacao {

	public static String linha(Produto prod) {
		String lin = prod.getCodUtilizEstab();
		for(OutrasUnid out : prod.getOutrasUnds()){
		
			    lin += "|";
				lin += out.getUndEquivPadrao();
		}
		
		return lin;
	}
	
	public static void main(String[] args) {
		
		MetadadosDB banco = new MetadadosDB();
		LoteImportacaoSpedFiscalDao loteDao = new LoteImportacaoSpedFiscalDao();
		ProdutoDao daoProd = new ProdutoDao();
		NotaFiscalDao nfDao = new NotaFiscalDao();
		ReducaoZDao rdzDao = new ReducaoZDao();
		EquipamentoCFeDao cfeDao = new EquipamentoCFeDao();
		ItensMovDiarioCFeDao itensCfeDao = new  ItensMovDiarioCFeDao();
		
		String ano = "2019";
		String emp = "SELLENE";
		String estab = "MEGADIET";
		String cnpj  = "05329222000419";
		
		String anomes1 = ano.concat("01").concat(".txt");
		String anomes2 = ano.concat("02").concat(".txt");
		String anomes3 = ano.concat("03").concat(".txt");
		String anomes4 = ano.concat("04").concat(".txt");
		String anomes5 = ano.concat("05").concat(".txt");
		String anomes6 = ano.concat("06").concat(".txt");
		String anomes7 = ano.concat("07").concat(".txt");
		String anomes8 = ano.concat("08").concat(".txt");
		String anomes9 = ano.concat("09").concat(".txt");
		String anomes10 = ano.concat("10").concat(".txt");
		String anomes11 = ano.concat("11").concat(".txt");
		String anomes12 = ano.concat("12").concat(".txt");
		
        Path x1 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\jan"));
	    Path p1 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes1));
		
	    Path x2 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\fev"));
	    Path p2 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes2));
		
	    Path x3 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\mar"));
	    Path p3 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes3));
	    
	    Path x4 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\abr"));
	    Path p4 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes4));
	    
	    Path x5 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\mai"));
	    Path p5 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes5));
	    
	    Path x6 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\jun"));
	    Path p6 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes6));
	    
	    Path x7 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\jul"));
	    Path p7 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes7));
	    
	    Path x8 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\ago"));
	    Path p8 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes8));
	    
	    Path x9 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\set"));
	    Path p9 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes9));
	    
	    Path x10 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\out"));
	    Path p10 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes10));
	    
	    Path x11 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\nov"));
	    Path p11 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes11));
	    
	    Path x12 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\dez"));
	    Path p12 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes12));
	    

	    Path p = p2;
		Path x = x2;
		
		LeitorEfdIcms leitor = new LeitorEfdIcms();
		
		ImportaEfdIcms imp = new ImportaEfdIcms();
		
		Long id0000 = 0L;
		Long id0200 = 0L;
		Long idC100 = 0L;
		Long idC405 = 0L;
		Long idC420 = 0L;
		Long idC860 = 0L;
		Long idH005 = 0L;
		
		id0000 = banco.getIncremento("tb_importspedfiscal");
		id0200 = banco.getIncremento( "tb_produto");
		idC100 = banco.getIncremento("tb_notafiscal");
		idC405 = banco.getIncremento("tb_reducaoz");
		idC420 = banco.getIncremento("tb_totaisparcrdz");
		idC860 = banco.getIncremento("tb_equipamentocfe");
		//idH005 = banco.getIncremento(em, "tb_reducaoz");

		leitor.leitorSpedFiscal(p,leitor.incLoteImportacao(id0000),
				leitor.incProd(id0200),leitor.incNFe(idC100),leitor.incRDZ(idC405),leitor.incTotParcRdz(idC420),
				leitor.incTotEquipCFe(idC860), 0L );

		LoteImportacaoSpedFiscal loteImportacao = imp.getLoteImportacao(leitor, 1L, 2L);

		
		
		List<Produto> produtosSped = imp.getProdutosSped(leitor,1L,2L);
		produtosSped.addAll(imp.getProdutos());
		List<Produto> collectProdutos = produtosSped.stream().distinct().collect(Collectors.toList());
		
		List<NotaFiscal>   notas1 =   imp.getNotasFiscaisTerceiros(leitor,1L,2L, leitor.incLoteImportacao(id0000));
		List<NotaFiscal>   notas2 =   imp.getNotasFiscaisProprios(leitor,x.toString(),1L,2L, leitor.incLoteImportacao(id0000));
		List<EquipamentoCFe> equipCfes = imp.getEquipamentosCFe(leitor, 1L,2L, leitor.incLoteImportacao(id0000));
		List<ReducaoZ> reducoes = imp.getReducoes(leitor, 1L,2L, leitor.incLoteImportacao(id0000));
		List<ItensMovDiarioCFe> itensCfes =   imp.getItensCFe(leitor, x.toString(), 1L,2L, leitor.incLoteImportacao(id0000));
		
		
		
		if(!loteDao.listaTodos().contains(loteImportacao)){
			loteDao.adiciona(loteImportacao);
			
			for(Produto prod :  collectProdutos){	
				
				if(daoProd.buscaPorCodigo(prod.getCodUtilizEstab()) == null) {
					 daoProd.adiciona(prod);
					 System.out.println("Cadastrando produto -> " + prod.getCodUtilizEstab());
				}else if(linha(prod).equals(daoProd.produtoJoinOutUnidadeMedida(1L,2L,prod.getCodUtilizEstab())) == false
						&&  daoProd.produtoJoinOutUnidadeMedida(1L,2L,prod.getCodUtilizEstab()).contains("NULL") == true){
					
					Produto buscaPorCodigo = daoProd.buscaPorCodigo(prod.getCodUtilizEstab());
			    	daoProd.remove(buscaPorCodigo);
			    	daoProd.atualiza(prod);
			    	System.out.println("Alterando o produto -> " + prod.getCodUtilizEstab());
				}

			}
		
			for(NotaFiscal nf :  notas1){			
				nfDao.adiciona(nf);
			}		
	        for(NotaFiscal nf :  notas2){			
				nfDao.adiciona(nf);
			}
	        for(ReducaoZ rdz : reducoes){
	        	rdzDao.adiciona(rdz);
	        }
			for(EquipamentoCFe equipCfe : equipCfes) {
				cfeDao.adiciona(equipCfe);
			}
			for(ItensMovDiarioCFe cfe : itensCfes){
				itensCfeDao.adiciona(cfe);
			}
			
		}else {
			System.out.println("Lote já importado!!!");
		}

		
	}
}
