package com.medeiros.ordnael.controlefinanceiro.resources.gasto;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.medeiros.ordnael.controlefinanceiro.entidades.Gasto;

@Stateless
public class GastoRessource {

	@PersistenceContext
	private EntityManager em;
	
	public void gravar(Gasto gasto) {
		
		gasto.setAtualizacao(null);
		if (gasto.getId()==null) {
			
		} else {
			
		}
		
	}
	
}
