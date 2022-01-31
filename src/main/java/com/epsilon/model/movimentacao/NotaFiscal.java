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
@Table(name = "tb_notafiscal")
public class NotaFiscal implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1306235939004063833L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="lote_id")
	private Long idPai;
	private Long idEmp;
	private Long idEst;
	private String tipoOperacao;
	private String especie;
	private String indDocProprio;
	private String serie;
	private String Subserie;
	private String numDoc;
	private LocalDate  DataEmissao;
	private String situacaoDocumento;
	private LocalDate DataEntradaSaida;
	private String codRemetenteDestinatario;
	private BigDecimal valorTotalProdutos;
	private BigDecimal valorFrete;
	private BigDecimal valorSeguro;
	private BigDecimal valoroutrasDespesas;
	private BigDecimal valordescontoTotal;
	private BigDecimal valorTotal;
	private String    chaveEletronica;
	
	@OneToMany(cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name="nota_id")
	private List<ProdutoNotaFiscal> prodNota = new ArrayList<ProdutoNotaFiscal>();
	
	public NotaFiscal() {
		
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
	public String getTipoOperacao() {
		return tipoOperacao;
	}
	public void setTipoOperacao(String tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}
	public String getEspecie() {
		return especie;
	}
	public void setEspecie(String especie) {
		this.especie = especie;
	}
	public String getIndDocProprio() {
		return indDocProprio;
	}
	public void setIndDocProprio(String indDocProprio) {
		this.indDocProprio = indDocProprio;
	}
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	public String getSubserie() {
		return Subserie;
	}
	public void setSubserie(String subserie) {
		Subserie = subserie;
	}
	public String getNumDoc() {
		return numDoc;
	}
	public void setNumDoc(String numDoc) {
		this.numDoc = numDoc;
	}
	public LocalDate getDataEmissao() {
		return DataEmissao;
	}
	public void setDataEmissao(LocalDate dataEmissao) {
		DataEmissao = dataEmissao;
	}
	public String getSituacaoDocumento() {
		return situacaoDocumento;
	}
	public void setSituacaoDocumento(String situacaoDocumento) {
		this.situacaoDocumento = situacaoDocumento;
	}
	public LocalDate getDataEntradaSaida() {
		return DataEntradaSaida;
	}
	public void setDataEntradaSaida(LocalDate dataEntradaSaida) {
		DataEntradaSaida = dataEntradaSaida;
	}
	public String getCodRemetenteDestinatario() {
		return codRemetenteDestinatario;
	}
	public void setCodRemetenteDestinatario(String codRemetenteDestinatario) {
		this.codRemetenteDestinatario = codRemetenteDestinatario;
	}
	public BigDecimal getValorTotalProdutos() {
		return valorTotalProdutos;
	}
	public void setValorTotalProdutos(BigDecimal valorTotalProdutos) {
		this.valorTotalProdutos = valorTotalProdutos;
	}
	public BigDecimal getValorFrete() {
		return valorFrete;
	}
	public void setValorFrete(BigDecimal valorFrete) {
		this.valorFrete = valorFrete;
	}
	public BigDecimal getValorSeguro() {
		return valorSeguro;
	}
	public void setValorSeguro(BigDecimal valorSeguro) {
		this.valorSeguro = valorSeguro;
	}
	public BigDecimal getValoroutrasDespesas() {
		return valoroutrasDespesas;
	}
	public void setValoroutrasDespesas(BigDecimal valoroutrasDespesas) {
		this.valoroutrasDespesas = valoroutrasDespesas;
	}
	public BigDecimal getValordescontoTotal() {
		return valordescontoTotal;
	}
	public void setValordescontoTotal(BigDecimal valordescontoTotal) {
		this.valordescontoTotal = valordescontoTotal;
	}
	public BigDecimal getValorvalorTotal() {
		return valorTotal;
	}
	public void setValorvalorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
	public String getChaveEletronica() {
		return chaveEletronica;
	}
	public void setChaveEletronica(String chaveEletronica) {
		this.chaveEletronica = chaveEletronica;
	}
	
	public void adicionaProdutoNota(ProdutoNotaFiscal prodNota) {
		this.prodNota.add(prodNota);
	}
	public List<ProdutoNotaFiscal> getProdNota() {
		return prodNota;
	}
	public void setProdNota(List<ProdutoNotaFiscal> prodNota) {
		this.prodNota = prodNota;
	}

}
