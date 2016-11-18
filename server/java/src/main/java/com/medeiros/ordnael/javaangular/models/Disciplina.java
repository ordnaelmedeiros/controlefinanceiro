package com.medeiros.ordnael.javaangular.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "SEQ_DISCIPLINA", sequenceName = "SEQ_DISCIPLINA", initialValue = 1)
public class Disciplina {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_DISCIPLINA")
	private Long disciplinaId;
	
	@Column(length=80)
	private String descricao;
	
	public Long getDisciplinaId() {
		return disciplinaId;
	}
	public void setDisciplinaId(Long disciplinaId) {
		this.disciplinaId = disciplinaId;
	}
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
