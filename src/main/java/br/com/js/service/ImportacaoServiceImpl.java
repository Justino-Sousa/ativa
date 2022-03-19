package br.com.js.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.js.entity.Empresa;
import br.com.js.java.MontarArquivoNotas;
import br.com.js.relatorios.RelatorioDsreExcel;

@Service
public class ImportacaoServiceImpl implements ImportacaoService {

	public String transformarArquivo(MultipartFile file, int idEmpresa, Calendar pInicial, Calendar pFinal, EmpresaService empresaService, String outputPath, String inputPath, String outputPathFornecedores) {
		
		SimpleDateFormat formatar = new SimpleDateFormat("dd/MM/yyyy");
		String pinicial = formatar.format(pInicial.getTime());
		String pfinal  =  formatar.format(pFinal.getTime());
		
		MontarArquivoNotas arquivo =  new MontarArquivoNotas();
	
		Empresa empresa = empresaService.recuperarPorId(idEmpresa);
		String fullpath = arquivo.processar(file, pinicial, pfinal, empresa, outputPath, inputPath, outputPathFornecedores);
		
		return fullpath;
	}

	@Override
	public String transformarArquivoNfse(MultipartFile file, String outputPath, String inputPath) {
		String fullpath = new  FileSystemStorageService().store(file, inputPath);
		
		new RelatorioDsreExcel(fullpath+inputPath,fullpath+outputPath).geraPlanilha();
		return fullpath;
	}

}