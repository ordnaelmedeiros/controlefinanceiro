package com.medeiros.ordnael.javaangular.core.resource;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;

import com.medeiros.ordnael.javaangular.core.JPAUtils;

public class Resource<Model> implements Closeable {

	private Class<Model> classe;
	
	private EntityManager em;

	public Resource(Class<Model> classe) {
		this.classe = classe;
		this.em = JPAUtils.createEntityManager();
	}

	private EntityManager getEm() {
		return em;
	}

	public Criteria createCriteria() {
		
		Session session = this.getEm().unwrap(org.hibernate.Session.class);
		return session.createCriteria(this.classe);
		
	}

	public void beginTransaction() throws Exception {

		this.getEm().getTransaction().begin();

	}

	public List<Model> findAll() throws Exception {

		@SuppressWarnings("unchecked")
		List<Model> list = this.createCriteria().list();
		return list;

	}
	
	public Model find(Long id) throws Exception {

		Model model = this.getEm().find(this.classe, id);
		return model;

	}

	public Model persist(Model model) throws Exception {

		this.getEm().persist(model);
		return model;

	}

	public Model merge(Model model) throws Exception {

		this.getEm().merge(model);
		return model;

	}

	public void remove(Long id) throws Exception {

		Model ent = this.find(id);
		this.getEm().remove(ent);

	}

	@Override
	public void close() throws IOException {
		
		if (this.getEm().getTransaction().isActive()) {
			try {
				this.getEm().getTransaction().commit();
			} catch (Exception e) {
				try {
					this.getEm().getTransaction().rollback();
				} catch (Exception e2) {}
				this.getEm().close();
				throw e;
			}
		}
		
		this.getEm().close();
	}

}
