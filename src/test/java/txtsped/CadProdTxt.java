package txtsped;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CadProdTxt {

	public static void main(String[] args) throws Exception{
		
		String ano = "2021";
		String emp = "SELLENE";
		String estab = "MEGADIET";
		String anomes1 = ano.concat("01").concat(".txt");
		Path p1 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\").concat("prods.csv"));
		
		File file = new File(p1.toString());
		
		Scanner leitor = new Scanner(file);
	    leitor.nextLine();
		while(leitor.hasNext()){
			String linhaDoArquivo;
			linhaDoArquivo = leitor.nextLine();
			String[] valoresEntreVirgulas = linhaDoArquivo.split("\\,");
			List<String> lista = Arrays.asList(valoresEntreVirgulas);
			
			System.out.println(lista.get(0));
		}
		leitor.close();
	}

}
