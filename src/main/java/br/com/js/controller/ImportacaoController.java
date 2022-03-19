package br.com.js.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.js.java.Constantes;
import br.com.js.java.FuncoesSistema;
import br.com.js.service.EmpresaService;
import br.com.js.service.ImportacaoService;

@Controller
@RequestMapping("/importacao")
public class ImportacaoController {
	
	String inputPath;
	String outputPath;
	String inputPathNfse;
	String outputPathNfse;
	String outputPathFornecedores;
	String fullPath;
	FuncoesSistema f = new FuncoesSistema();

	@Autowired
	EmpresaService empresaService;

	@Autowired
	ImportacaoService importacaoService;

	@GetMapping("/upload")
	public ModelAndView upload(ModelMap model) {
		model.addAttribute("empresas", empresaService.recuperar());
		return new ModelAndView(Constantes.returnViewImportacao, model);
	}

	@PostMapping("/importar")
	public String converter(@RequestParam("file") MultipartFile file, @RequestParam("idEmpresa") int idEmpresa,
			@RequestParam("pInicial") @DateTimeFormat(pattern = "yyyy-MM-dd") Calendar pInicial,
			@RequestParam("pFinal") @DateTimeFormat(pattern = "yyyy-MM-dd") Calendar pFinal, RedirectAttributes attr) {		
		
	
		this.inputPath = f.returnPathinput();
		this.outputPath = f.returnPathOutput();
		this.outputPathFornecedores = f.returnPathOutputFornecedores();
		
		 fullPath = importacaoService.transformarArquivo(file, idEmpresa, pInicial, pFinal, empresaService, this.outputPath, inputPath, outputPathFornecedores); 
		attr.addFlashAttribute(Constantes.nmMensagem, Constantes.mensagemImportacaoSucesso);
		return Constantes.redirectDownlod;
	}
	
	@GetMapping("/download")
	public ResponseEntity<Object> downloadFile() throws IOException {
				
		File file = new File(f.zipFiles(outputPath, outputPathFornecedores, fullPath));
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
		
		headers.add("Content-Disposition",
				String.format("attachment; filename=\"%s\"", file.getName()));
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		
		ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers)
				.contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/txt")).body(resource);

		return responseEntity;
	}
	
	
	@GetMapping("/uploadNfseExcel")
	public ModelAndView uploadNfseExcel(ModelMap model) {
		model.addAttribute("empresas", empresaService.recuperar());
		return new ModelAndView(Constantes.returnViewNfse, model);
	}
	
	@PostMapping("/importarNfse")
	public String converterNfse(@RequestParam("file") MultipartFile file, RedirectAttributes attr) {		
		
		this.inputPathNfse = f.returnPathinput();
		this.outputPathNfse = f.returnPathOutputNfePlanilha();
		fullPath = importacaoService.transformarArquivoNfse(file, outputPathNfse, inputPathNfse);
		attr.addFlashAttribute(Constantes.nmMensagem, Constantes.mensagemImportacaoSucesso);
		return Constantes.redirectDownlodNfse;
	}
	
	@GetMapping("/downloadNfse")
	public ResponseEntity<Object> downloadFileNFSe() throws IOException {
				
		File file = new File(fullPath+outputPathNfse);
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
		
		headers.add("Content-Disposition",
				String.format("attachment; filename=\"%s\"", file.getName()));
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		
		ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers)
				.contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/txt")).body(resource);

		return responseEntity;
	}
	
}
