package com.medeiros.ordnael.javaangular.core.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.medeiros.ordnael.javaangular.core.resource.Resource;

public abstract class ControllerCRUDGeneric<Model> extends ControllerCRUD<Model> {

	
	public List<Model> pesquisar() throws Exception {
		
		try (Resource<Model> resource = new Resource<>(this.getClasse())) {
			
			return resource.findAll();
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	public Model pesquisar(@PathVariable Long id) throws Exception {
		
		try (Resource<Model> resource = new Resource<>(this.getClasse())) {
			
			return resource.find(id);
			
		} catch (Exception e) {
			throw e;
		}
		
	}

	public Model gravar(@RequestBody Model model) throws Exception {
		
		try (Resource<Model> resource = new Resource<>(this.getClasse())) {
			
			resource.beginTransaction();
			return resource.persist(model);
			
		} catch (Exception e) {
			throw e;
		}
		
	}

	public Model alterar(@RequestBody Model model) throws Exception {

		try (Resource<Model> resource = new Resource<>(this.getClasse())) {
			
			resource.beginTransaction();
			return resource.merge(model);
			
		} catch (Exception e) {
			throw e;
		}
		
	}

	public boolean excluir(@PathVariable Long id) throws Exception {

		try (Resource<Model> resource = new Resource<>(this.getClasse())) {
			
			resource.beginTransaction();
			resource.remove(id);
			
			return true;
			
		} catch (Exception e) {
			throw e;
		}
		
	}

}
