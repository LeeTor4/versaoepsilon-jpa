package txtsped;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class AlteraTxtSped {

	public static void main(String[] args) throws Exception {
		String ano = "2021";
		String emp = "SELLENE";
		String estab = "MEGADIET";
		String cnpj  = "05329222000419";
		
		String anomes1 = ano.concat("01").concat(".txt");
		String anomes2 = ano.concat("01").concat("_v2").concat(".txt");
	    Path p1 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes1));
	    Path p2 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes2));
	    
	    
	 
	   
	   
	   List<String> novasLinhas =  new ArrayList<String>();   
	   List<String> lines = Files.readAllLines(p1, StandardCharsets.ISO_8859_1);
	   for(int i=0; i < lines.size(); i++) {
		  
		   novasLinhas.add(lines.get(i));
		   
		   
		   if(lines.get(i).contains("|9999|")){
			   
			   
			   String novoConteudo = lines.get(i).replace("9999", "XXXX");	
			   
			   
			   
			   novasLinhas.remove(i);
			   novasLinhas.add(i, novoConteudo);
		   }
		  
	   }
	   
	   
	  
	   
	   Files.write(p2, novasLinhas, StandardOpenOption.CREATE);
	   
	    
	}
}
