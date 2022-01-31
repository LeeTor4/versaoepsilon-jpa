package com.epsilon.model.movimentacao;

import java.io.Serializable;
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
@Table(name = "tb_equipamentocfe")
public class EquipamentoCFe implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8017060055716719792L;
   
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long      id;
	@Column(name="lote_id")
	private Long      idPai;
	private String    codModDocFiscal;
	private String    numSerieEquipSat;
	private LocalDate dtEmissao;
	private String    docInicial;
	private String    docFinal;
	
	@OneToMany(cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name="equip_id")
	private List<ItensMovDiarioCFe> itensDiarioCFe = new ArrayList<ItensMovDiarioCFe>();
	
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
	public String getCodModDocFiscal() {
		return codModDocFiscal;
	}
	public void setCodModDocFiscal(String codModDocFiscal) {
		this.codModDocFiscal = codModDocFiscal;
	}
	public String getNumSerieEquipSat() {
		return numSerieEquipSat;
	}
	public void setNumSerieEquipSat(String numSerieEquipSat) {
		this.numSerieEquipSat = numSerieEquipSat;
	}
	public LocalDate getDtEmissao() {
		return dtEmissao;
	}
	public void setDtEmissao(LocalDate dtEmissao) {
		this.dtEmissao = dtEmissao;
	}
	public String getDocInicial() {
		return docInicial;
	}
	public void setDocInicial(String docInicial) {
		this.docInicial = docInicial;
	}
	public String getDocFinal() {
		return docFinal;
	}
	public void setDocFinal(String docFinal) {
		this.docFinal = docFinal;
	}
	public List<ItensMovDiarioCFe> getItensDiarioCFe() {
		return itensDiarioCFe;
	}
	public void setItensDiarioCFe(List<ItensMovDiarioCFe> itensDiarioCFe) {
		this.itensDiarioCFe = itensDiarioCFe;
	}
	
	public void adicionaItensMovDiario(ItensMovDiarioCFe itensDiarioCFe) {
		   this.itensDiarioCFe.add(itensDiarioCFe);
	}
}
