package br.com.js.relatorios;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class RelatorioDsreExcel {

	private String arquivoDre;
	private String pathPlanilha;
	
	public RelatorioDsreExcel(String arquivoDre,String pathPlanilha) {
		this.arquivoDre = arquivoDre;
		this.pathPlanilha = pathPlanilha; 
	}
	
	public void geraPlanilha() {
		
		try {
			InputStream is = new FileInputStream(arquivoDre);
			InputStreamReader isr = new InputStreamReader(is,"ISO-8859-1");
			BufferedReader bf = new BufferedReader(isr);
				
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet();
			
			int contadorLinha  = 0;		
			
			String dsreLinha = bf.readLine();
			
			while (dsreLinha != null) {
			
				String dsreArray[] = dsreLinha.split("[;]");
				
				Row row = sheet.createRow(contadorLinha++);
				
				int contadorCell = 0;		
				Cell cellNome1 = row.createCell(contadorCell++);
				cellNome1.setCellValue(dsreArray[0]);
				
				Cell cellNome2 = row.createCell(contadorCell++);
				cellNome2.setCellValue(dsreArray[1]);
				
				Cell cellNome3 = row.createCell(contadorCell++);
				cellNome3.setCellValue(dsreArray[2]);
				
				Cell cellNome4 = row.createCell(contadorCell++);
				cellNome4.setCellValue(dsreArray[3]);
				
				dsreLinha = bf.readLine();
			}
			
			bf.close();

			workbook.write(new FileOutputStream(this.pathPlanilha));
			workbook.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
