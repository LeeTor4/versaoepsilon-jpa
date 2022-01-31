package com.epsilon.model.movimentacao;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_saldo_itn_tot_lote")
public class SaldoItensTotalizadoPorLote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "lote_id")
	private Long    idPaiLote;
	private Long idCodItem;
	private String cnpj;
	private String descricao;
	private String ano;
	private String mes;
	private String codItem;
	private String codAntItem;
	private BigDecimal saldoIni; 
	private BigDecimal totQtdeEnt; 
	private BigDecimal totVlEnt; 
	private BigDecimal totQtdeSai; 
	private BigDecimal totVlSai;

	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
    
	public Long getIdPaiLote() {
		return idPaiLote;
	}
	public void setIdPaiLote(Long idPaiLote) {
		this.idPaiLote = idPaiLote;
	}
	public Long getIdCodItem() {
		return idCodItem;
	}
	public void setIdCodItem(Long idCodItem) {
		this.idCodItem = idCodItem;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getAno() {
		return ano;
	}
	public void setAno(String ano) {
		this.ano = ano;
	}
	public String getMes() {
		return mes;
	}
	public void setMes(String mes) {
		this.mes = mes;
	}
	public String getCodItem() {
		return codItem;
	}
	public void setCodItem(String codItem) {
		this.codItem = codItem;
	}
	public String getCodAntItem() {
		return codAntItem;
	}
	public void setCodAntItem(String codAntItem) {
		this.codAntItem = codAntItem;
	}
	public BigDecimal getSaldoIni() {
		return saldoIni;
	}
	public void setSaldoIni(BigDecimal saldoIni) {
		this.saldoIni = saldoIni;
	}
	public BigDecimal getTotQtdeEnt() {
		return totQtdeEnt;
	}
	public void setTotQtdeEnt(BigDecimal totQtdeEnt) {
		this.totQtdeEnt = totQtdeEnt;
	}
	public BigDecimal getTotVlEnt() {
		return totVlEnt;
	}
	public void setTotVlEnt(BigDecimal totVlEnt) {
		this.totVlEnt = totVlEnt;
	}
	public BigDecimal getTotQtdeSai() {
		return totQtdeSai;
	}
	public void setTotQtdeSai(BigDecimal totQtdeSai) {
		this.totQtdeSai = totQtdeSai;
	}
	public BigDecimal getTotVlSai() {
		return totVlSai;
	}
	public void setTotVlSai(BigDecimal totVlSai) {
		this.totVlSai = totVlSai;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(ano, cnpj, codItem);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SaldoItensTotalizadoPorLote other = (SaldoItensTotalizadoPorLote) obj;
		return Objects.equals(ano, other.ano) && Objects.equals(cnpj, other.cnpj)
				&& Objects.equals(codItem, other.codItem);
	}
}
