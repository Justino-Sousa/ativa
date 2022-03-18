package br.com.js.service;

import java.util.Calendar;

import org.springframework.web.multipart.MultipartFile;

public interface ImportacaoService {
	String transformarArquivo(MultipartFile file, int idEmpresa, Calendar pInicial, Calendar pFinal,
			EmpresaService empresaService, String outputPath, String inputPath, String outputPathFornecedores);
}
