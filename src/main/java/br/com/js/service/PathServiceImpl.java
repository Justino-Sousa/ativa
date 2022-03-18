package br.com.js.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.js.dao.PathDao;
import br.com.js.entity.Path;

@Service
@Transactional
public class PathServiceImpl implements PathService{

	@Autowired
	PathDao pathDao;
	
	@Override
	public void salvar(Path path) {
		pathDao.salvar(path);	
	}


	@Override
	@Transactional(readOnly = true)
	public List<Path> recuperar() {
		return pathDao.recuperar();
	}
	
	@Override
	public void atualizar(Path path) {
		pathDao.atualizar(path);
	}

	@Override
	public void excluir(int idPath) {
		pathDao.excluir(idPath);
	}



}
