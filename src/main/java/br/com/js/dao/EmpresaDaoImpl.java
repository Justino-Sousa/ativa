package br.com.js.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.js.entity.Empresa;

@Repository
public class EmpresaDaoImpl implements EmpresaDao{

	@PersistenceContext
	private EntityManager em;
	
	
	@Override
	public void salvar(Empresa empresa) {
		em.persist(empresa);
	}

	@Override
	public List<Empresa> recuperar() {
		return  em.createQuery("select e from Empresa e", Empresa.class).getResultList();
	}

	@Override
	public Empresa recuperarPorId(int idEmpresa) {
		return em.find(Empresa.class, idEmpresa);
	}

	@Override
	public void atualizar(Empresa empresa) {
		em.merge(empresa);
	}

	@Override
	public void excluir(int idEmpresa) {
		em.remove(em.getReference(Empresa.class, idEmpresa));
	}
	
}
