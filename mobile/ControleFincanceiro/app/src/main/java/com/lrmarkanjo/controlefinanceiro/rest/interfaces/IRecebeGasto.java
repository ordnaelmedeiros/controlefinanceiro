package com.lrmarkanjo.controlefinanceiro.rest.interfaces;

import com.lrmarkanjo.controlefinanceiro.banco.modelos.Gasto;

/**
 * Created by lrmar on 11/11/2016.
 */

public interface IRecebeGasto {

    public void result(Gasto gasto);
    public void fail(String erro);

}
