package com.medeiros.ordnael.javaangular.models;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name="seq_gasto_pkey", sequenceName="seq_gasto_pkey", initialValue=1)
public class Gasto {

	@Id
	@Column(name="gasto_id")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_gasto_pkey")
    private Long id;
	
	@Column
    private Date data = new Date();
	
	@Column
    private BigDecimal valor;
	
	@Column(name="tipo_pagamento")
    private String tipoPagamento;
	
	@Column
    private String grupo;
	
	@Column(name="sub_grupo")
    private String subGrupo;
	
	@Column
    private String descricao;
	
	@Column
    private byte[] imagem;
	
	@Column
	@SequenceGenerator(name="seq_gasto_atualizacao", sequenceName="seq_gasto_atualizacao", initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_gasto_atualizacao")
	private Integer atualizacao;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="usuario_id")
	private Usuario usuario;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public String getTipoPagamento() {
		return tipoPagamento;
	}
	public void setTipoPagamento(String tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public String getSubGrupo() {
		return subGrupo;
	}
	public void setSubGrupo(String subGrupo) {
		this.subGrupo = subGrupo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public byte[] getImagem() {
		return imagem;
	}
	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}
	public Integer getAtualizacao() {
		return atualizacao;
	}
	public void setAtualizacao(Integer atualizacao) {
		this.atualizacao = atualizacao;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
    
}
