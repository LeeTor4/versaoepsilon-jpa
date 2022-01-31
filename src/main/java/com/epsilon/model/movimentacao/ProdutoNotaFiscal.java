package com.epsilon.model.movimentacao;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_prodnf")
public class ProdutoNotaFiscal  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -532689057719883111L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="nota_id")
	private Long idPai;
	private String numItem;
	private String codProduto;
	private String cfop;
	private String cstA;
	private String cstB;
	private String  unidadeMedida;
	private BigDecimal quantidade;
	private BigDecimal valorBruto;
	private BigDecimal vlUnitItem;
	private BigDecimal valorSeguro;
	private BigDecimal valorDesconto;
	private BigDecimal valorDespesas;
	private BigDecimal valorTotal;
	private String csosn;
	
	public ProdutoNotaFiscal() {
		
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
	
	public String getNumItem() {
		return numItem;
	}

	public void setNumItem(String numItem) {
		this.numItem = numItem;
	}

	public String getCodProduto() {
		return codProduto;
	}
	public void setCodProduto(String codProduto) {
		this.codProduto = codProduto;
	}
	public String getCfop() {
		return cfop;
	}
	public void setCfop(String cfop) {
		this.cfop = cfop;
	}
	public String getCstA() {
		return cstA;
	}
	public void setCstA(String cstA) {
		this.cstA = cstA;
	}
	public String getCstB() {
		return cstB;
	}
	public void setCstB(String cstB) {
		this.cstB = cstB;
	}
	public String getUnidadeMedida() {
		return unidadeMedida;
	}
	public void setUnidadeMedida(String unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}
	public BigDecimal getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}
	public BigDecimal getValorBruto() {
		return valorBruto;
	}
	public void setValorBruto(BigDecimal valorBruto) {
		this.valorBruto = valorBruto;
	}
	
	public BigDecimal getVlUnitItem() {
		return vlUnitItem;
	}

	public void setVlUnitItem(BigDecimal vlUnitItem) {
		this.vlUnitItem = vlUnitItem;
	}

	public BigDecimal getValorSeguro() {
		return valorSeguro;
	}
	public void setValorSeguro(BigDecimal valorSeguro) {
		this.valorSeguro = valorSeguro;
	}
	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}
	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}
	public BigDecimal getValorDespesas() {
		return valorDespesas;
	}
	public void setValorDespesas(BigDecimal valorDespesas) {
		this.valorDespesas = valorDespesas;
	}
	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
	public String getCsosn() {
		return csosn;
	}
	public void setCsosn(String csosn) {
		this.csosn = csosn;
	}
	
	public Double getQtde() {
		return quantidade.doubleValue();
	}
	
	public Double getVlBruto() {
		return valorBruto.doubleValue();
	}

}
