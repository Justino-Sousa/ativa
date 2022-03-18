package br.com.js.java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MontarArquivoFornecedores {
	
	private String codigoEmpresa;
	private String fileInput;
	private String fileOutput;
	
	public String getCodigoEmpresa() {
		return codigoEmpresa;
	}

	public void setCodigoEmpresa(String codigoEmpresa) {
		this.codigoEmpresa = codigoEmpresa;
	}

	public String getFileInput() {
		return fileInput;
	}

	public void setFileInput(String fileInput) {
		this.fileInput = fileInput;
	}

	public void processar() {
		montarFornecedores();
	}
	
	
	public String getFileOutput() {
		return fileOutput;
	}

	public void setFileOutput(String fileOutput) {
		this.fileOutput = fileOutput;
	}

	private void montarFornecedores() {
		
		try {

			InputStream is = new FileInputStream(fileInput);
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String linha = br.readLine();

			while (linha != null) {
				linha = FuncoesSistema.removeAcentos(linha);
				String[] split = linha.split("[;]");

				String campo1 = "11";
				String campo2 = FuncoesSistema.mascaraNumero(7, codigoEmpresa);
				String campo3 = split[7];
				String campo4 = FuncoesSistema.completarComEspacos(7);
				String campo5 = FuncoesSistema.completarComEspacos(7);
				String campo6 = split[3];
				campo6 = FuncoesSistema.mascaraTexto(10, campo6);

				String campo7 = split[3];
				campo7 = FuncoesSistema.mascaraTexto(40, campo7);

				String campo8 = FuncoesSistema.completarComEspacos(40);
				String campo9 = FuncoesSistema.completarComEspacos(7);
				String campo10 = FuncoesSistema.completarComEspacos(30);
				String campo11 = FuncoesSistema.completarComEspacos(8);
				String campo12p = split[13].replace(".", "").replace("-", "").replace("/", "");
				String campo12 = FuncoesSistema.mascaraNumero(14, campo12p);
				
				String campo13 = FuncoesSistema.completarComEspacos(20);
				String campo14 = FuncoesSistema.completarComEspacos(14);
				String campo15 = FuncoesSistema.completarComEspacos(14);
				String campo16 = "N";
				String campo17 = "N";
				String campo18 = null;
				if(campo12p.length() == 14) {
					campo18 = "1";
				}else {
					campo18 = "2";
				}

				String campo19 = FuncoesSistema.completarComEspacos(20);
				String campo20 = FuncoesSistema.completarComEspacos(20);
				String campo21 = FuncoesSistema.completarComZeros(4);
				String campo22 = FuncoesSistema.completarComZeros(7);
				String campo23 = FuncoesSistema.completarComEspacos(11);
				
				String campo24 = FuncoesSistema.completarComEspacos(11);
				String campo25 = FuncoesSistema.completarComEspacos(40);
				String campo26 = FuncoesSistema.completarComEspacos(48);
				
				String linhaGravacao = campo1 + campo2 + campo3 + campo4 + campo5 + campo6 + campo7 + campo8 + campo9
						+ campo10 + campo11 + campo12 + campo13 + campo14 + campo15 + campo16 + campo17 + campo18
						+ campo19 + campo20 + campo21 + campo22 + campo23 + campo24 + campo25 + campo26;
				gravarNoArquivo(linhaGravacao);
				linha = br.readLine();
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void gravarNoArquivo(String linhaGravacao) {

		try {
			OutputStream os;
			os = new FileOutputStream(fileOutput, true);
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter bfw = new BufferedWriter(osw);
			bfw.write(linhaGravacao + "\n");
			bfw.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
