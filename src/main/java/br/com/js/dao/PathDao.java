package br.com.js.dao;

import java.util.List;

import br.com.js.entity.Path;

public interface PathDao {
	void salvar(Path path);

	List<Path>recuperar();

	void atualizar(Path path);

	void excluir(int idPath);
}
