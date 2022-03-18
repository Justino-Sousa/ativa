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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import br.com.js.entity.Empresa;
import br.com.js.service.FileSystemStorageService;
import br.com.js.service.PathServiceImpl;

public class MontarArquivoNotas {
	
	private FileSystemStorageService fileSystemStorageService = new  FileSystemStorageService();
	
	private String fileInput;
	private String fileOutput;

	private String cnpj;
	private String codigoEmpresa;
	private String dataInicial;
	private String dataFinal;
	private MontarArquivoFornecedores arquivoFornecedores = new MontarArquivoFornecedores();

	@Autowired
	PathServiceImpl salvarCaminho;

	public String processar(MultipartFile file, String dataIni, String dataFina, Empresa empresa, String outputPath,
			String inputPath, String outputPathFornecedores) {
		
		String fullPath = fileSystemStorageService.store(file,inputPath);
		FuncoesSistema f = new FuncoesSistema();
		f.setFullPath(fullPath);
		
		this.fileInput = fullPath+inputPath;
		this.fileOutput = fullPath+outputPath;
		
		this.cnpj = empresa.getCnpj();
		this.codigoEmpresa = String.valueOf(empresa.getCodDominio());
		this.dataInicial = dataIni;
		this.dataFinal = dataFina;

		montarCabecalho();
		montarCorpoDeNota();
		arquivoFornecedores.setCodigoEmpresa(codigoEmpresa);
		arquivoFornecedores.setFileInput(fileInput);
		arquivoFornecedores.setFileOutput(fullPath+outputPathFornecedores);
		arquivoFornecedores.processar();
		
		return fullPath;
	}

	// Monta o cabe�alho do arquivoO
	private void montarCabecalho() {

		String campo1 = "01";
		String campo2 = FuncoesSistema.mascaraNumero(7, this.codigoEmpresa);
		String campo3 = FuncoesSistema.mascaraNumero(14, this.cnpj.replace(".", "").replace("/", "").replace("-", ""));
		String campo4 = dataInicial;
		String campo5 = dataFinal;
		String campo6 = "N";
		String campo7 = "02";
		String campo8 = "00000";
		String campo9 = "1";
		String campo10 = "17";

		String linhaGravacao = campo1 + campo2 + campo3 + campo4 + campo5 + campo6 + campo7 + campo8 + campo9 + campo10;
		gravarNoArquivo(linhaGravacao);
	}

	// Monta o corpo da nota
	private void montarCorpoDeNota() {
		try {

			InputStream is = new FileInputStream(fileInput);
			InputStreamReader isr = new InputStreamReader(is, "ISO-8859-1");
			BufferedReader br = new BufferedReader(isr);
			String linha = br.readLine();
			int sq02 = 1;
			while (linha != null) {
				String[] split = linha.split("[;]");

				String campo1 = "02";

				String campo2 = FuncoesSistema.mascaraNumero(7, String.valueOf(sq02)); // N�mero sequencial de lan�amentos

				String campo3 = FuncoesSistema.mascaraNumero(7, this.codigoEmpresa); // Pegar o c�digo da empresa no dom�nio

				String campo4 = FuncoesSistema.mascaraTexto(14, split[13]);
				String campo5 = null;
				String tpNota = split[8].toLowerCase();
				if (tpNota.equals("sim")) {
					campo5 = FuncoesSistema.mascaraNumero(7, "42");
				} else {
					campo5 = FuncoesSistema.mascaraNumero(7, "40");
				}

				String campo6 = FuncoesSistema.completarComZeros(2);
				String campo7 = FuncoesSistema.mascaraNumero(7, "505");

				String campo8 = null;
				String uf = split[7].toLowerCase();
				if (uf.equals("pe")||uf.equals("PE")||uf.equals("Pe")) {
					campo8 = FuncoesSistema.mascaraNumero(7, "1933");
				} else {
					campo8 = FuncoesSistema.mascaraNumero(7, "2933");
				}

				String campo9 = FuncoesSistema.completarComZeros(2);
			
				String campo10 = split[4].toLowerCase();
				String campo11 = FuncoesSistema.mascaraTexto(7, " ");
			
				if(campo10.contains("r") || campo10.contains("p") || campo10.contains("a")) {
					
					campo10 = campo10.replace("r", "").replace("p", "").replace("a", "");	
					campo11 = "      A";
				}
				campo10 = FuncoesSistema.mascaraNumero(7, campo10);
				
				String campo12 = FuncoesSistema.completarComZeros(7);
				
				String campo13 = null;
				LocalDate dataEntrada = null;
				LocalDate dataEmissao = null;
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				
				dataEntrada = LocalDate.parse(split[1].toString(), formatter);
				dataEmissao = LocalDate.parse(split[0].toString(), formatter);
				
				if(dataEntrada.isBefore(dataEmissao)) {
					campo13 = split[0];
				}else {
					campo13 = split[1];
				}
			
				String campo14 = split[0];
				String campo15 = FuncoesSistema.mascaraNumero(13, split[16]);
				String campo16 = FuncoesSistema.completarComZeros(13);
				String campo17 = FuncoesSistema.completarComEspacos(30);
				String campo18 = FuncoesSistema.completarComEspacos(2);
				String campo19 = FuncoesSistema.completarComEspacos(1);
				String campo20 = "T";
				String campo21 = "P";
				String campo22 = "E";
				
				String campo23 = split[6].toLowerCase();
				if(campo23.contains("exterior")) {
					campo23 = "0000000";
				}else {
					campo23 = FuncoesSistema.mascaraNumero(7, split[6]);
				}
				
				String campo24 = FuncoesSistema.completarComZeros(7);
				String campo25 = FuncoesSistema.completarComZeros(7);
				String campo26 = FuncoesSistema.completarComEspacos(6);
				String campo27 = FuncoesSistema.completarComEspacos(6);
				String campo28 = FuncoesSistema.completarComZeros(7);
				String campo29 = "01/01/2020";
				String campo30 = FuncoesSistema.completarComZeros(13);
				String campo31 = FuncoesSistema.completarComZeros(13);
				String campo32 = FuncoesSistema.completarComZeros(13);
				String campo33 = FuncoesSistema.completarComZeros(7);
				String campo34 = FuncoesSistema.completarComZeros(13);
				String campo35 = FuncoesSistema.completarComZeros(13);
				String campo36 = FuncoesSistema.completarComZeros(13);
				String campo37 = FuncoesSistema.completarComZeros(1);
				String campo38 = FuncoesSistema.completarComZeros(13);
				String campo39 = FuncoesSistema.completarComZeros(13);
				String campo40 = FuncoesSistema.completarComZeros(13);
				String campo41 = FuncoesSistema.completarComZeros(1);
				String campo42 = FuncoesSistema.completarComZeros(7);
				String campo43 = FuncoesSistema.completarComZeros(7);
				String campo44 = FuncoesSistema.completarComZeros(7);
				String campo45 = FuncoesSistema.completarComEspacos(20);
				String campo46 = FuncoesSistema.completarComEspacos(20);
				String campo47 = FuncoesSistema.completarComEspacos(300);
				String campo48 = FuncoesSistema.completarComEspacos(44);
				String campo49 = FuncoesSistema.completarComEspacos(6);
				String campo50 = "E";
				String campo51 = FuncoesSistema.completarComZeros(1);
				String campo52 = FuncoesSistema.completarComEspacos(44);
				String campo53 = FuncoesSistema.completarComEspacos(48);
				String campo54 = FuncoesSistema.completarComZeros(7);
				String campo55 = FuncoesSistema.completarComEspacos(255);
				String campo56 = FuncoesSistema.completarComZeros(1);
				String campo57 = FuncoesSistema.completarComZeros(7);
				String campo58 = FuncoesSistema.completarComZeros(4);
				String campo59 = FuncoesSistema.completarComZeros(13);
				String campo60 = FuncoesSistema.completarComZeros(13);
				String campo61 = FuncoesSistema.completarComZeros(5);
				String campo62 = FuncoesSistema.completarComZeros(5);
				String campo63 = FuncoesSistema.completarComEspacos(8);
				String campo64 = FuncoesSistema.completarComEspacos(15);
				String campo65 = FuncoesSistema.completarComEspacos(1);
				String campo66 = FuncoesSistema.completarComZeros(5);
				String campo67 = FuncoesSistema.completarComEspacos(22);
				

				String linhaGravacao = campo1 + campo2 + campo3 + campo4 + campo5 + campo6 + campo7 + campo8 + campo9
						+ campo10 + campo11 + campo12 + campo13 + campo14 + campo15 + campo16 + campo17 + campo18
						+ campo19 + campo20 + campo21 + campo22 + campo23 + campo24 + campo25 + campo26 + campo27
						+ campo28 + campo29 + campo30 + campo31 + campo32 + campo33 + campo34 + campo35 + campo36
						+ campo37 + campo38 + campo39 + campo40 + campo41 + campo42 + campo43 + campo44 + campo45
						+ campo46 + campo47 + campo48 + campo49 + campo50 + campo51 + campo52 + campo53 + campo54
						+ campo55 + campo56 + campo57 + campo58 + campo59 + campo60 + campo61 + campo62 + campo63 
						+ campo64 + campo65 + campo66 + campo67;

				gravarNoArquivo(linhaGravacao);
				montarImposto(linha);
				linha = br.readLine();
				sq02++;
			}
			br.close();

		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// Monta o imposto da nota
	private void montarImposto(String linha) {

		String split[] = linha.split("[;]");

		boolean inss = false; // posi��o 17
		boolean iss = false; // posi��o 29
		boolean irrf = false; // posi��o 35
		boolean pcc = false; // posi��o 41
		int sq = 1;

		if (!(split[17].trim().equals("-"))) {
			inss = true;
		}
		if (!split[29].trim().equals("-")) {
			iss = true;
		}
		if (!split[35].trim().equals("-")) {
			irrf = true;
		}
		if (!split[41].trim().equals("-")) {
			pcc = true;
		}

		if (inss == true || iss == true || irrf == true || pcc == true) {

			String campo1 = "03";
			String campo8 = FuncoesSistema.completarComZeros(13);
			String campo9 = FuncoesSistema.completarComZeros(13);
			String campo10 = FuncoesSistema.completarComZeros(13);
			String campo11 = FuncoesSistema.completarComZeros(13);
			String campo12 = FuncoesSistema.mascaraNumero(13, split[16]);
			String campo13 = FuncoesSistema.completarComEspacos(6);
			String campo14 = FuncoesSistema.completarComZeros(13);
			String campo15 = FuncoesSistema.completarComZeros(13);
			String campo16 = FuncoesSistema.completarComEspacos(74);

			if (inss == true) {
				String campo2 = FuncoesSistema.mascaraNumero(7, String.valueOf(sq));
				String campo3 = "0000026";
				String campo4 = FuncoesSistema.mascaraNumero(5, split[20]);
				String campo5 = FuncoesSistema.mascaraNumero(13, split[21]);
				String campo6 = FuncoesSistema.mascaraNumero(5, split[22]);
				String campo7 = FuncoesSistema.mascaraNumero(13, split[17]);
				inss = false;
				sq++;
				String linhaGravacao = campo1 + campo2 + campo3 + campo4 + campo5 + campo6 + campo7 + campo8 + campo9
						+ campo10 + campo11 + campo12 + campo13 + campo14 + campo15 + campo16;
				gravarNoArquivo(linhaGravacao);
			}

			if (iss == true) {
				String campo2 = FuncoesSistema.mascaraNumero(7, String.valueOf(sq));
				String campo3 = "0000018";
				String campo4 = FuncoesSistema.mascaraNumero(5, split[26]);
				String campo5 = FuncoesSistema.mascaraNumero(13, split[27]);
				String campo6 = FuncoesSistema.mascaraNumero(5, split[28]);
				String campo7 = FuncoesSistema.mascaraNumero(13, split[29]);
				iss = false;
				sq++;
				String linhaGravacao = campo1 + campo2 + campo3 + campo4 + campo5 + campo6 + campo7 + campo8 + campo9
						+ campo10 + campo11 + campo12 + campo13 + campo14 + campo15 + campo16;
				gravarNoArquivo(linhaGravacao);
			}

			if (irrf == true) {
				String campo2 = FuncoesSistema.mascaraNumero(7, String.valueOf(sq));
				String campo3 = "0000016";
				String campo4 = FuncoesSistema.mascaraNumero(5, split[32]);
				String campo5 = FuncoesSistema.mascaraNumero(13, split[33]);
				String campo6 = FuncoesSistema.mascaraNumero(5, split[34]);
				String campo7 = FuncoesSistema.mascaraNumero(13, split[35]);
				irrf = false;
				sq++;
				String linhaGravacao = campo1 + campo2 + campo3 + campo4 + campo5 + campo6 + campo7 + campo8 + campo9
						+ campo10 + campo11 + campo12 + campo13 + campo14 + campo15 + campo16;
				gravarNoArquivo(linhaGravacao);
			}

			if (pcc == true) {
				String campo2 = FuncoesSistema.mascaraNumero(7, String.valueOf(sq));
				String campo3 = "0000025";
				String campo4 = FuncoesSistema.mascaraNumero(5, split[38]);
				String campo5 = FuncoesSistema.mascaraNumero(13, split[39]);
				String campo6 = FuncoesSistema.mascaraNumero(5, split[40]);
				String campo7 = FuncoesSistema.mascaraNumero(13, split[41]);
				pcc = false;
				sq++;
				String linhaGravacao = campo1 + campo2 + campo3 + campo4 + campo5 + campo6 + campo7 + campo8 + campo9
						+ campo10 + campo11 + campo12 + campo13 + campo14 + campo15 + campo16;
				gravarNoArquivo(linhaGravacao);
			}
		}
	}

	private void gravarNoArquivo(String linhaGravacao) {

		try {
			OutputStream os;
			os = new FileOutputStream(this.fileOutput, true);
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter bfw = new BufferedWriter(osw);
			bfw.write(linhaGravacao + "\n");
			bfw.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

}
