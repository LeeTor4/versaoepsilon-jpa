package leituraXml;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.bind.JAXBException;

import com.leetor4.handler.ParseDocXML;
import com.leetor4.model.nfe.DocumentoFiscalEltronico;
import com.leetor4.model.nfe.Produtos;

import modulos.efdicms.entidades.RegC100;
import modulos.efdicms.manager.LeitorEfdIcms;

public class leituras {

	public static void main(String[] args) throws IOException, JAXBException {
		
		String ano = "2021";
		String emp = "SELLENE";
		String estab = "MEGADIET";
		String cnpj  = "05329222000419";
		String anomes11 = ano.concat("11").concat(".txt");
	    Path x11 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\nov"));
	    Path p11 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes11));
	    ParseDocXML parseDocXML = new ParseDocXML();
		File f = new File(x11.toString());
		LeitorEfdIcms leitor = new LeitorEfdIcms();
		leitor.leitorSpedFiscal(p11,0L,0L,0L,0L,0L, 0L );
		
		
		
		for (DocumentoFiscalEltronico doc : parseDocXML.validaTipoDeParseNFE(f)) {
			if (doc.getIdent().getModeloDoc().equals("55")) {
			
					

						//System.out.println(nota.getNumDoc() + "|" + nota.getChvNfe());

						for (Produtos p : doc.getProds()) {

							System.out.println( doc.getIdent().getDataEmissao() + "|" + p.getCodItem() + "|" + p.getCfop() + "|" +  doc.getIdent().getChaveeletronica());
						}

				

				

			}
		}

	}

}
