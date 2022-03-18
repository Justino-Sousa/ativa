package br.com.js.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.js.entity.Path;

@Repository
public class PathDaoImpl implements PathDao {
	
	@PersistenceContext
	EntityManager em;
	
	@Override
	public void salvar(Path path) {
		em.persist(path);
	}

	@Override
	public List<Path> recuperar() {
		return em.createQuery("select p from Path p", Path.class).getResultList();
	}

	@Override
	public void atualizar(Path path) {
		em.merge(path);
	}

	@Override
	public void excluir(int idPath) {
		em.remove(em.getReference(Path.class, idPath));
	}

}
