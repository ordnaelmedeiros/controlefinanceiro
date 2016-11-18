package com.medeiros.ordnael.controlefinanceiro.controllers;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.medeiros.ordnael.controlefinanceiro.entidades.Gasto;
import com.medeiros.ordnael.controlefinanceiro.resources.gasto.GastoRessource;

@Path("/gasto")
@Produces("application/json")
public class GastoController {

	@POST
	public Gasto post(Gasto gasto) {
		new GastoRessource().gravar(gasto);
		return gasto;
	}
	
}