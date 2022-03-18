package br.com.js.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.js.entity.Empresa;
import br.com.js.java.Constantes;
import br.com.js.service.EmpresaService;

@Controller
@RequestMapping("/empresa")
public class EmpresaController {

	@Autowired
	EmpresaService empresaService;
	
	//******************* Create *******************//
	@PostMapping("/salvar")
	public String salvar(@ModelAttribute("empresa") Empresa empresa, BindingResult result, RedirectAttributes attr){
		if(result.hasErrors()) {
			attr.addFlashAttribute(Constantes.nmMensagemErro,Constantes.mensagemCadastroErro);
			return Constantes.redirectEmpresaList;
		}
		empresaService.salvar(empresa);
		attr.addFlashAttribute(Constantes.nmMensagem, Constantes.mensagemCadastroSucesso);
		return  Constantes.redirectEmpresaList;
	}
	
	//******************* Read *******************//
	@GetMapping("/lista")
	public ModelAndView listar(ModelMap model, @ModelAttribute("empresa") Empresa empresa) {
		model.addAttribute("empresas",empresaService.recuperar());
		return new ModelAndView(Constantes.returnViewEmpresaList,model);
	}
	
	//******************* update *******************//
	@GetMapping("/{id}/atualizar")
	public ModelAndView preAtualizar(@PathVariable("id") int id, ModelMap model){
		Empresa empresa = empresaService.recuperarPorId(id);
		model.addAttribute("empresa", empresa);
		return new ModelAndView(Constantes.returnViewEmpresaEdit,model);
	}
	
	@PostMapping("/atualizar")
	public String atualizar(@ModelAttribute("empresa") Empresa empresa, BindingResult result,RedirectAttributes attr) {
		if(result.hasErrors()) {
			attr.addFlashAttribute(Constantes.nmMensagemErro,Constantes.mensagemAtualizacaoErro);
			return Constantes.redirectEmpresaList;
		}
		System.out.println(empresa.getId());
		empresaService.atualizar(empresa);
		attr.addFlashAttribute(Constantes.nmMensagem, Constantes.mensagemAtualizacaoSucesso);
		return Constantes.redirectEmpresaList;
	}
	
	//******************* Delete *******************//
	@GetMapping("/{id}/remover")
	public String excluir(@PathVariable("id") int id, RedirectAttributes attr) {
		empresaService.excluir(id);
		attr.addFlashAttribute(Constantes.nmMensagem,Constantes.mensagemExclusaoSucesso);
		return Constantes.redirectEmpresaList;
	}
}
