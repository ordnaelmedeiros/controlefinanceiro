package com.medeiros.ordnael.controlefinanceiro;

import java.time.LocalDate;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/teste")
@Produces("application/json")
public class TesteRest {

	@GET
	@Path("/ping")
	public String ping() {
		return LocalDate.now().toString();
	}
	
	@GET
	@Path("/objeto")
	public Teste objeto() {
		return new Teste();
	}
	
	@POST
	@Path("/objeto")
	public Teste post(Teste teste) {
		teste.setId(teste.getId()+10);
		teste.setDescricao(teste.getDescricao()+" + 10");
		return teste;
	}
	
}


class Teste {
	
	private int id = 0;
	private String descricao = "Descrição";
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}