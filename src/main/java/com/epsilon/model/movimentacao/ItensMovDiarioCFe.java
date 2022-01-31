package com.epsilon.model.movimentacao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_itenscfe")
public class ItensMovDiarioCFe implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -940955283213247571L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long idPaiEmp;
	private Long idPaiEst;
	@Column(name="equip_id")
	private Long idPai;
	private String numCFe;
	private String numItem;
	private String codItem;
	private Double qtde;
	private String und;
	private Double vlUnit;
	private Double vlProd;
	private Double vlDesc;
	private Double vlItem;
	private String cstIcms;
	private String cfop;
	private String chaveCFe;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdPaiEmp() {
		return idPaiEmp;
	}
	public void setIdPaiEmp(Long idPaiEmp) {
		this.idPaiEmp = idPaiEmp;
	}
	public Long getIdPaiEst() {
		return idPaiEst;
	}
	public void setIdPaiEst(Long idPaiEst) {
		this.idPaiEst = idPaiEst;
	}
	public Long getIdPai() {
		return idPai;
	}
	public void setIdPai(Long idPai) {
		this.idPai = idPai;
	}
	
	public String getNumCFe() {
		return numCFe;
	}
	public void setNumCFe(String numCFe) {
		this.numCFe = numCFe;
	}
	public String getNumItem() {
		return numItem;
	}
	public void setNumItem(String numItem) {
		this.numItem = numItem;
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
	public String getCstIcms() {
		return cstIcms;
	}
	public void setCstIcms(String cstIcms) {
		this.cstIcms = cstIcms;
	}
	public String getCfop() {
		return cfop;
	}
	public void setCfop(String cfop) {
		this.cfop = cfop;
	}
	public Double getVlUnit() {
		return vlUnit;
	}
	public void setVlUnit(Double vlUnit) {
		this.vlUnit = vlUnit;
	}
	
	public Double getVlProd() {
		return vlProd;
	}
	public void setVlProd(Double vlProd) {
		this.vlProd = vlProd;
	}
	public Double getVlDesc() {
		return vlDesc;
	}
	public void setVlDesc(Double vlDesc) {
		this.vlDesc = vlDesc;
	}
	public Double getVlItem() {
		return vlItem;
	}
	public void setVlItem(Double vlItem) {
		this.vlItem = vlItem;
	}
	public String getChaveCFe() {
		return chaveCFe;
	}
	public void setChaveCFe(String chaveCFe) {
		this.chaveCFe = chaveCFe;
	}

}
