package leituraSped;

import com.epsilon.dao.MetadadosDB;

public class DadosBanco {

	public static void main(String[] args) {
	
		MetadadosDB banco = new MetadadosDB();
		//banco.getIncremento("tb_importspedfiscal");
		
		System.out.println((banco.getIncremento("tb_importspedfiscal")==1 ? 0 : banco.getIncremento("tb_importspedfiscal")));

	}

}
