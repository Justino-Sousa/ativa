package br.com.js.service;

import java.util.List;

import br.com.js.entity.Empresa;

public interface EmpresaService {

	void salvar(Empresa empresa);

	List<Empresa> recuperar();

	Empresa recuperarPorId(int idEmpresa);

	void atualizar(Empresa empresa);

	void excluir(int idEmpresa);

}
