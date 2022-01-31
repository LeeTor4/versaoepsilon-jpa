package com.epsilon.model.movimentacao;

import java.io.Serializable;
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
@Table(name = "tb_totaisparcrdz")
public class TotParciaisRDZ implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long                   id;
	@Column(name="id_totparc")
	private Long                   idPai;
	private String                 codTotalizador;
	private Double                 vlAcumuladoTotRedZ;
	private String                 numTotalizador;
	private String                 descrNumTotalizador;
	
	
	@OneToMany(cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name="id_itensmovdiario")
	private List<ItensMovDiario>   itensMovimentoDiario = new ArrayList<>();
	
	public TotParciaisRDZ() {
		
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
	public String getCodTotalizador() {
		return codTotalizador;
	}
	public void setCodTotalizador(String codTotalizador) {
		this.codTotalizador = codTotalizador;
	}
	public Double getVlAcumuladoTotRedZ() {
		return vlAcumuladoTotRedZ;
	}
	public void setVlAcumuladoTotRedZ(Double vlAcumuladoTotRedZ) {
		this.vlAcumuladoTotRedZ = vlAcumuladoTotRedZ;
	}
	public String getNumTotalizador() {
		return numTotalizador;
	}
	public void setNumTotalizador(String numTotalizador) {
		this.numTotalizador = numTotalizador;
	}
	public String getDescNumTotalizador() {
		return descrNumTotalizador;
	}
	public void setDescNumTotalizador(String descrNumTotalizador) {
		this.descrNumTotalizador = descrNumTotalizador;
	}
	public List<ItensMovDiario> getItensMovimentoDiario() {
		return itensMovimentoDiario;
	}
	public void setItensMovimentoDiario(List<ItensMovDiario> itensMovimentoDiario) {
		this.itensMovimentoDiario = itensMovimentoDiario;
	}
	
	public void adicionaItensMovDiario(ItensMovDiario itn) {
		itensMovimentoDiario.add(itn);
	}

}
