package com.lrmarkanjo.controlefinanceiro.banco.modelos;

import com.lrmarkanjo.controlefinanceiro.banco.enums.GastoGrupo;
import com.lrmarkanjo.controlefinanceiro.banco.enums.GastoGrupoCusto;
import com.lrmarkanjo.controlefinanceiro.banco.enums.TipoPagamento;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by lrmar on 06/11/2016.
 */

public class Gasto {

    private Integer id;
    private Integer idExterno;
    private Date data = new Date();
    private Date sincronizacao;
    private BigDecimal valor;
    private TipoPagamento tipoPagamento;
    private GastoGrupo grupo;
    private GastoGrupoCusto subGrupo;
    private String descricao;
    private byte[] imagem;
    private Integer ativo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public GastoGrupo getGrupo() {
        return grupo;
    }

    public void setGrupo(GastoGrupo grupo) {
        this.grupo = grupo;
    }

    public GastoGrupoCusto getSubGrupo() {
        return subGrupo;
    }

    public void setSubGrupo(GastoGrupoCusto subGrupo) {
        this.subGrupo = subGrupo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public Date getSincronizacao() {
        return sincronizacao;
    }

    public void setSincronizacao(Date sincronizacao) {
        this.sincronizacao = sincronizacao;
    }

    public Integer getIdExterno() {
        return idExterno;
    }

    public void setIdExterno(Integer idExterno) {
        this.idExterno = idExterno;
    }

    public Integer getAtivo() {
        return ativo;
    }

    public void setAtivo(Integer ativo) {
        this.ativo = ativo;
    }
}
