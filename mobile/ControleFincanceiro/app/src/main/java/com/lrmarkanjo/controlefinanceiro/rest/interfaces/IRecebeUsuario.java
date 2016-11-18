package com.lrmarkanjo.controlefinanceiro.rest.interfaces;

import com.lrmarkanjo.controlefinanceiro.banco.modelos.Gasto;
import com.lrmarkanjo.controlefinanceiro.banco.modelos.Usuario;

/**
 * Created by lrmar on 11/11/2016.
 */

public interface IRecebeUsuario {

    public void result(Usuario usuario);
    public void fail(String erro);

}
