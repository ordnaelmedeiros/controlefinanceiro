package com.medeiros.ordnael.javaangular.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medeiros.ordnael.javaangular.core.controller.ControllerCRUDGeneric;
import com.medeiros.ordnael.javaangular.models.Usuario;

@RestController
@RequestMapping("/usuario")
public class UsuarioController extends ControllerCRUDGeneric<Usuario> {

	public Class<Usuario> getClasse() {
		return Usuario.class;
	}
	
}
