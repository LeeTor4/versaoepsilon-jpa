package com.epsilon.model.movimentacao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_totdiariocf")
public class TotalizadorDiarioCuponsFiscais  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long                   id;
	@Column(name="id_totdiariocf")
	private Long                   idPai;
	private String                 cst;
	private String                 cfop;
	private Double                 vlOperacao;
	
	public TotalizadorDiarioCuponsFiscais() {
		
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdPai() {
		return idPai;
	}
	public void setIdPai(Long idPai) {
		this.idPai = idPai;
	}
	public String getCst() {
		return cst;
	}
	public void setCst(String cst) {
		this.cst = cst;
	}
	public String getCfop() {
		return cfop;
	}
	public void setCfop(String cfop) {
		this.cfop = cfop;
	}
	public Double getVlOperacao() {
		return vlOperacao;
	}
	public void setVlOperacao(Double vlOperacao) {
		this.vlOperacao = vlOperacao;
	}

}
