package br.com.js.service;

import java.util.List;

import br.com.js.entity.Path;

public interface PathService {
	void salvar(Path path);

	List<Path> recuperar();

	void atualizar(Path path);

	void excluir(int idPath);
}
