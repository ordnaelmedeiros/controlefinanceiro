package com.medeiros.ordnael.javaangular.core.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public abstract class ControllerCRUD<Model> extends Controller {

	public abstract Class<Model> getClasse();
	
	@RequestMapping(method = RequestMethod.GET)
	public abstract List<Model> pesquisar() throws Exception;
	
	@RequestMapping(path="/{id}", method = RequestMethod.GET)
	public abstract Model pesquisar(Long id) throws Exception;
	
	@RequestMapping(method = RequestMethod.POST)
	public abstract Model gravar(Model model) throws Exception;

	@RequestMapping(method = RequestMethod.PUT)
	public abstract Model alterar(Model model) throws Exception;

	@RequestMapping(path="/{id}", method = RequestMethod.DELETE)
	public abstract boolean excluir(Long id) throws Exception;

}
