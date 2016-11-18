package com.medeiros.ordnael.javaangular.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.medeiros.ordnael.javaangular.core.controller.Controller;

@RestController
public class TesteController extends Controller {

	@Autowired
	private HttpServletRequest request;
	
	
	@RequestMapping(value = "/ping", method = RequestMethod.GET)
	public Teste ping() throws Exception {
		
		System.out.println("User-Agent: " + request.getHeader("User-Agent"));
		
		return new Teste(1l,"ping");
		
	}

	@RequestMapping(value = "/erro", method = RequestMethod.GET)
	public Teste erro() throws Exception {
		
		throw new Exception("Erro Teste");
		
	}

}

class Teste {

	private Long testeId;
	private String descricao;

	public Long getTesteId() {
		return testeId;
	}

	public void setTesteId(Long testeId) {
		this.testeId = testeId;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Teste(Long testeId, String descricao) {
		this.testeId = testeId;
		this.descricao = descricao;
	}

}