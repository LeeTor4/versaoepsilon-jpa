package com.epsilon.model.movimentacao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "tb_reducaoz")
public class ReducaoZ implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long                   id;
	private Long                   id_ecf;
	@Column(name="lote_id")
	private Long                   idPai;
	private Long idEmp;
	private Long idEst;
	private LocalDate              dtReducaoZ;
	private String                 posicaoCRO;
	private String                 posicaoRDZ;
	private String                 numCOO;
	private BigDecimal             vlGrandeTotal;
	private BigDecimal             vlVendaBruta;
	
	@OneToMany(cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name="id_totparc")
	private List<TotParciaisRDZ>   totaisParcReducoesZ = new ArrayList<TotParciaisRDZ>();
	
	@OneToMany(cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name="id_totdiariocf")
	private List<TotalizadorDiarioCuponsFiscais> totaisCuponsFiscais = new ArrayList<TotalizadorDiarioCuponsFiscais>();
	
	public ReducaoZ() {
		
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId_ecf() {
		return id_ecf;
	}
	public void setId_ecf(Long id_ecf) {
		this.id_ecf = id_ecf;
	}
	public Long getIdPai() {
		return idPai;
	}
	public void setIdPai(Long idPai) {
		this.idPai = idPai;
	}
	
	public Long getIdEmp() {
		return idEmp;
	}
	public void setIdEmp(Long idEmp) {
		this.idEmp = idEmp;
	}
	public Long getIdEst() {
		return idEst;
	}
	public void setIdEst(Long idEst) {
		this.idEst = idEst;
	}
	public LocalDate getDtReducaoZ() {
		return dtReducaoZ;
	}
	public void setDtReducaoZ(LocalDate dtReducaoZ) {
		this.dtReducaoZ = dtReducaoZ;
	}
	public String getPosicaoCRO() {
		return posicaoCRO;
	}
	public void setPosicaoCRO(String posicaoCRO) {
		this.posicaoCRO = posicaoCRO;
	}
	public String getPosicaoRDZ() {
		return posicaoRDZ;
	}
	public void setPosicaoRDZ(String posicaoRDZ) {
		this.posicaoRDZ = posicaoRDZ;
	}
	public String getNumCOO() {
		return numCOO;
	}
	public void setNumCOO(String numCOO) {
		this.numCOO = numCOO;
	}
	public BigDecimal getVlGrandeTotal() {
		return vlGrandeTotal;
	}
	public void setVlGrandeTotal(BigDecimal vlGrandeTotal) {
		this.vlGrandeTotal = vlGrandeTotal;
	}
	public BigDecimal getVlVendaBruta() {
		return vlVendaBruta;
	}
	public void setVlVendaBruta(BigDecimal vlVendaBruta) {
		this.vlVendaBruta = vlVendaBruta;
	}
	public List<TotParciaisRDZ> getTotParcReducoesZ() {
		return totaisParcReducoesZ;
	}
	public void setTotParcReducoesZ(List<TotParciaisRDZ> totParcReducoesZ) {
		this.totaisParcReducoesZ = totParcReducoesZ;
	}
	
	public List<TotalizadorDiarioCuponsFiscais> getTotaisCuponsFiscais() {
		return totaisCuponsFiscais;
	}
	public void adicionaTotParcRedZ(TotParciaisRDZ tot) {
		totaisParcReducoesZ.add(tot);
	}
	
	public void adicionaTotalCuponsFiscais(TotalizadorDiarioCuponsFiscais totais) {
		totaisCuponsFiscais.add(totais);
	}

}
