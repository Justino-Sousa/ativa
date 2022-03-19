package br.com.js.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import br.com.js.storage.StorageProperties;

public class FuncoesSistema {
	
	private String fullPath;
	
	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	StorageProperties storage = new StorageProperties();
	
	/*
	 * retorna o caminho de entrada do arquivo
	 */
	public String returnPathinput() {

		Date c = Calendar.getInstance().getTime();
		String time = String.valueOf(c).replace(" ", "").replace("/", "-").replace(":", "-");
		;
		String path = storage.getLocation() + "/"+ time + Constantes.return_Extensao_TXT;

		return path;
	}

	/*
	 * retorna o caminho de saida do arquivo
	 * 
	 */
	public String returnPathOutput() {

		Date c = Calendar.getInstance().getTime();
		String time = String.valueOf(c).replace(" ", "").replace("/", "-").replace(":", "-");
		;
		String path = storage.getLocation() + "/JS-" + time + Constantes.return_Extensao_TXT;

		return path;
	}

	/*
	 * retorna o caminho de saida do arquivo de fornecedores
	 * 
	 */
	public String returnPathOutputFornecedores() {

		String outputFornecedores = returnPathOutput();
		outputFornecedores = outputFornecedores.replace("JS-", "JS-Fornecedores-");
		return outputFornecedores;
	}
	
	/*
	 * retorna o caminho de saida do arquivo de fornecedores
	 * 
	 */
	public String returnPathOutputNfePlanilha() {

		Date c = Calendar.getInstance().getTime();
		String time = String.valueOf(c).replace(" ", "").replace("/", "-").replace(":", "-");
		;
		String outputNfePlanilha = storage.getLocation() + "/JS-NFSe-" + time + Constantes.return_Extensao_XLSX;
		return outputNfePlanilha;
	}
	
	/*
	 * retorna o caminho do arquivo zip
	 * 
	 */
	public String returnPathZip(String fullPath) {
		
		String path = storage.getLocation() +"/" + "arquivos-"+FuncoesSistema.getTime()+".zip";
		return fullPath+path;
	}

	/*
	 * retorna uma string com o tamanho passado no parametro tamanhoCampo
	 * concatenando o valor do parametro string + a quantidade de 0 a completar o
	 * tamanho
	 * 
	 */
	public static String mascaraNumero(int tamanhoCampo, String string) {
		string = string.replace("/", "").replace(".", "").replace("-", "").replace(",", "").trim();
		String complemento = "0";
		while (complemento.length() <= tamanhoCampo) {
			complemento = complemento + "0";
		}
		string = complemento + string;
		int sTamanho = string.length();
		string = string.substring(sTamanho - tamanhoCampo, sTamanho);
		return string;
	}

	/*
	 * retorna uma string com o tamanho passado no parametro tamanhoCampo
	 * concatenando o valor do parametro string + a quantidade de " " (espaços) a
	 * completar o tamanho
	 * 
	 */
	public static String mascaraTexto(int tamanhoCampo, String string) {
		string = string.replace("/", "").replace(".", "").replace("-", "").replace(",", "").trim();
		String complemento = " ";
		while (complemento.length() <= tamanhoCampo) {
			complemento = complemento + " ";
		}
		string = complemento + string;
		int sTamanho = string.length();
		string = string.substring(sTamanho - tamanhoCampo, sTamanho);
		return string;
	}

	/*
	 * retorna uma string com o tamanho passado no parametro tamanho preenchido com
	 * 0
	 */
	public static String completarComZeros(int tamanho) {

		String valor = "";

		while (valor.length() < tamanho) {
			valor = valor + "0";
		}

		return valor;
	}

	/*
	 * retorna uma string com o tamanho passado no parametro tamanho preenchido com
	 * " " (espaços)
	 */
	public static String completarComEspacos(int tamanho) {

		String valor = "";

		while (valor.length() < tamanho) {
			valor = valor + " ";
		}

		return valor;
	}

	/*
	 * retorna uma string com uma data no formato dd-MM-yyyy hh-mm
	 */
	public static String getTime() {

		Date c = Calendar.getInstance().getTime();
		String time = String.valueOf(c).replace(" ", "").replace("/", "-").replace(":", "-");

		return time;
	}

	/*
	 * recebe uma string por parametro e retorna uma string com acentos removidos
	 */
	public static String removeAcentos(String linha) {

		return linha.replace("á", "a").replace("ã", "a").replace("é", "e").replace("ê", "e").replace("í", "i")
				.replace("ô", "o").replace("õ", "o").replace("ú", "u").replace("ç", "c").replace("Á", "A")
				.replace("Ã", "A").replace("É", "E").replace("Ê", "e").replace("Í", "I").replace("Ô", "O")
				.replace("Õ", "O").replace("Ú", "U").replace("Ç", "C");

	}

	/*
	 * zipa os dois arquivos recebidos por parametros, e retorna uma string com o
	 * caminho do arquivo zip
	 */
	public String zipFiles(String file1, String file2, String fullPath) {
		String zip = returnPathZip(fullPath);
		try {
			List<String> srcFiles = Arrays.asList(fullPath+file1, fullPath+file2);
			FileOutputStream fos = new FileOutputStream(zip);
			ZipOutputStream zipOut = new ZipOutputStream(fos);
			for (String srcFile : srcFiles) {
				File fileToZip = new File(srcFile);
				FileInputStream fis = new FileInputStream(fileToZip);
				ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
				zipOut.putNextEntry(zipEntry);

				byte[] bytes = new byte[1024];
				int length;
				while ((length = fis.read(bytes)) >= 0) {
					zipOut.write(bytes, 0, length);
				}
				fis.close();
			}
			zipOut.close();
			fos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return zip;
	}
	

}
