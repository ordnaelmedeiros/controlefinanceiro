package com.medeiros.ordnael.javaangular.controller;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medeiros.ordnael.javaangular.core.controller.ControllerCRUDGeneric;
import com.medeiros.ordnael.javaangular.core.resource.Resource;
import com.medeiros.ordnael.javaangular.models.Gasto;
import com.medeiros.ordnael.javaangular.models.Usuario;

@RestController
@RequestMapping("/gasto")
public class GastoController extends ControllerCRUDGeneric<Gasto> {


	@Autowired
	private HttpServletRequest request;
	
	
	public Class<Gasto> getClasse() {
		return Gasto.class;
	}
	
	@Override
	public Gasto gravar(@RequestBody Gasto gasto) throws Exception {
		
		try (
			Resource<Gasto> resource = new Resource<>(Gasto.class);
			Resource<Usuario> resUsuario = new Resource<>(Usuario.class);
		) {
			
			String email = request.getHeader("User-Agent");
			Usuario usuario = (Usuario) resUsuario.createCriteria().add(Restrictions.eq("email", email)).uniqueResult();
			
			gasto.setUsuario(usuario);
			
			resource.beginTransaction();
			return resource.persist(gasto);
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	@Override
	public Gasto alterar(Gasto gasto) throws Exception {

		try (
			Resource<Gasto> resource = new Resource<>(Gasto.class);
			Resource<Usuario> resUsuario = new Resource<>(Usuario.class);
		) {
			
			String email = request.getHeader("User-Agent");
			Usuario usuario = (Usuario) resUsuario.createCriteria().add(Restrictions.eq("email", email)).uniqueResult();
			
			gasto.setUsuario(usuario);
			
			
			resource.beginTransaction();
			return resource.merge(gasto);
			
		} catch (Exception e) {
			throw e;
		}
	}
	
}
