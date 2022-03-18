package br.com.js.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.js.dao.EmpresaDao;
import br.com.js.entity.Empresa;

@Service
@Transactional
public class EmpresaServiceImpl implements EmpresaService {

	@Autowired
	EmpresaDao empresaDao;
	
	@Override
	public void salvar(Empresa empresa) {
		empresaDao.salvar(empresa);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Empresa> recuperar() {
		return empresaDao.recuperar();
	}

	@Override
	@Transactional(readOnly = true)
	public Empresa recuperarPorId(int idEmpresa) {
		return empresaDao.recuperarPorId(idEmpresa);
	}

	@Override
	public void atualizar(Empresa empresa) {
		empresaDao.atualizar(empresa);
	}

	@Override
	public void excluir(int idEmpresa) {
		empresaDao.excluir(idEmpresa);
	}

}
