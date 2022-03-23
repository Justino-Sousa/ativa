package br.com.js.relatorios;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
			
			int contadorLinha  = 1;		
			String dsreLinha = bf.readLine();
			
			
			// Cabeçalho da planilha
			Row headerRow = sheet.createRow(0);
			headerRow.createCell(0).setCellValue("Emissão");headerRow.createCell(1).setCellValue("Recebido");headerRow.createCell(2).setCellValue("Data pgto");headerRow.createCell(3).setCellValue("Fornecedor");
			headerRow.createCell(4).setCellValue("NF");headerRow.createCell(5).setCellValue("Município");headerRow.createCell(6).setCellValue("Codigo Município - IBGE");headerRow.createCell(7).setCellValue("UF");
			headerRow.createCell(8).setCellValue("NF-e");headerRow.createCell(9).setCellValue("CPOM");headerRow.createCell(10).setCellValue("DS?");headerRow.createCell(11).setCellValue("PF / PJ");
			headerRow.createCell(12).setCellValue("PIS");headerRow.createCell(13).setCellValue("CNPJ/CPF");headerRow.createCell(14).setCellValue("Serviços");headerRow.createCell(15).setCellValue("Histórico");
			headerRow.createCell(16).setCellValue("Valor Bruto");headerRow.createCell(17).setCellValue("INSS (11%)");headerRow.createCell(18).setCellValue("INSS (20%");headerRow.createCell(19).setCellValue("Outras Entidades");
			headerRow.createCell(20).setCellValue("Percentual de redução");headerRow.createCell(21).setCellValue("Base de Cálculo");headerRow.createCell(22).setCellValue("Aliquota");headerRow.createCell(23).setCellValue("Total INSS");
			headerRow.createCell(24).setCellValue("INSS Vencto");headerRow.createCell(25).setCellValue("Obs");headerRow.createCell(26).setCellValue("Percentual de redução");headerRow.createCell(27).setCellValue("Base de Cálculo");
			headerRow.createCell(28).setCellValue("Aliquota");headerRow.createCell(29).setCellValue("ISS (5%)");headerRow.createCell(30).setCellValue("ISS Vencto");headerRow.createCell(31).setCellValue("obs");
			headerRow.createCell(32).setCellValue("Percentual de redução");headerRow.createCell(33).setCellValue("Base de Cálculo");headerRow.createCell(34).setCellValue("Aliquota");headerRow.createCell(35).setCellValue("IRRF (1 a 1,5%)");
			headerRow.createCell(36).setCellValue("IRRF Vencto");headerRow.createCell(37).setCellValue("obs");headerRow.createCell(38).setCellValue("Percentual de redução");headerRow.createCell(39).setCellValue("Base de Cálculo");
			headerRow.createCell(40).setCellValue("Aliquota");headerRow.createCell(41).setCellValue("PCC (4,65%)");headerRow.createCell(42).setCellValue("PCC Vencto");headerRow.createCell(43).setCellValue("Obs");
			headerRow.createCell(44).setCellValue("Valor Líquido");headerRow.createCell(45).setCellValue("Data do envio");
			
			while (dsreLinha != null) {
				
				String dsreArray[] = dsreLinha.split("[;]");
				
				if((dsreArray[0] != null && dsreArray[0] != "" && dsreArray[0] != "Tipo de Registro") && (dsreArray[3] != null && dsreArray[3] != "")) {
					
					Row row = sheet.createRow(contadorLinha++);
					
					int contadorCell = 0;
									
					ArrayList<Cell> celulas = new ArrayList<Cell>(46);
					
					for (int i = 0; i < 46; i++) {
						Cell celula = row.createCell(contadorCell++);
						celulas.add(celula);
					}
					
					celulas.get(0).setCellValue(dsreArray[63]); ///
					celulas.get(1).setCellValue("");
					celulas.get(2).setCellValue("");
					celulas.get(3).setCellValue(dsreArray[13]); ///
					celulas.get(4).setCellValue(dsreArray[7]);  ///
					celulas.get(5).setCellValue(dsreArray[20]); ///
					celulas.get(6).setCellValue("");
					celulas.get(7).setCellValue("");
					celulas.get(8).setCellValue("");
					celulas.get(9).setCellValue("");
					celulas.get(11).setCellValue("");
					celulas.get(12).setCellValue("");
					celulas.get(10).setCellValue("");
					celulas.get(13).setCellValue(dsreArray[10]); ///
					celulas.get(14).setCellValue(dsreArray[46]); ///
					celulas.get(15).setCellValue(dsreArray[47]); ///
					celulas.get(16).setCellValue(dsreArray[49]); ///
					celulas.get(17).setCellValue("");
					celulas.get(18).setCellValue("");
					celulas.get(19).setCellValue("");
					celulas.get(20).setCellValue("");
					celulas.get(21).setCellValue("");
					celulas.get(22).setCellValue("");
					celulas.get(23).setCellValue("");
					celulas.get(24).setCellValue("");
					celulas.get(25).setCellValue("");
					celulas.get(26).setCellValue("");
					celulas.get(27).setCellValue("");
					celulas.get(28).setCellValue("");
					celulas.get(29).setCellValue("");
					celulas.get(30).setCellValue("");
					celulas.get(31).setCellValue("");
					celulas.get(32).setCellValue("");
					celulas.get(33).setCellValue("");
					celulas.get(34).setCellValue("");
					celulas.get(35).setCellValue("");
					celulas.get(36).setCellValue("");
					celulas.get(37).setCellValue("");
					celulas.get(38).setCellValue("");
					celulas.get(39).setCellValue("");
					celulas.get(40).setCellValue("");
					celulas.get(41).setCellValue("");
					celulas.get(42).setCellValue("");
					celulas.get(42).setCellValue("");
					celulas.get(43).setCellValue("");
					celulas.get(44).setCellValue("");
					
				}
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
