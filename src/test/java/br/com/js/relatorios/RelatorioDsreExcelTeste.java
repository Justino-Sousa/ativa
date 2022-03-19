package br.com.js.relatorios;

import org.junit.jupiter.api.Test;

public class RelatorioDsreExcelTeste {
	
	@Test
	public void geraPlanilhaTeste() {
		 new RelatorioDsreExcel("C:\\Users\\justi\\Documents\\testes\\Excel(CSV)Ver3.0.csv","C:\\Users\\justi\\Documents\\testes\\JsPlan.xlsx").geraPlanilha();	
		 
		 
	}
}

