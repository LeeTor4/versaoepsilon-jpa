package com.epsilon.model.movimentacao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_itensmovdiario")
public class ItensMovDiario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long                   id;
	@Column(name="id_itensmovdiario")
	private Long                   idPai;
	private Long                   idPaiRedZ;
	private String                 codItem;
	private Double                 qtde;
	private String                 und;
	private Double                 vlItem;
	private Double                 vlPis;
	private Double                 vlCofins;
	
	public ItensMovDiario() {
		
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
	
	public Long getIdPaiRedZ() {
		return idPaiRedZ;
	}
	public void setIdPaiRedZ(Long idPaiRedZ) {
		this.idPaiRedZ = idPaiRedZ;
	}
	public String getCodItem() {
		return codItem;
	}
	public void setCodItem(String codItem) {
		this.codItem = codItem;
	}
	public Double getQtde() {
		return qtde;
	}
	public void setQtde(Double qtde) {
		this.qtde = qtde;
	}
	public String getUnd() {
		return und;
	}
	public void setUnd(String und) {
		this.und = und;
	}
	public Double getVlItem() {
		return vlItem;
	}
	public void setVlItem(Double vlItem) {
		this.vlItem = vlItem;
	}
	public Double getVlPis() {
		return vlPis;
	}
	public void setVlPis(Double vlPis) {
		this.vlPis = vlPis;
	}
	public Double getVlCofins() {
		return vlCofins;
	}
	public void setVlCofins(Double vlCofins) {
		this.vlCofins = vlCofins;
	}

}
