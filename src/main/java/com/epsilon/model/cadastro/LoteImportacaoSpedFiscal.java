package com.epsilon.model.cadastro;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

import com.epsilon.model.movimentacao.EquipamentoCFe;
import com.epsilon.model.movimentacao.HistoricoItens;
import com.epsilon.model.movimentacao.NotaFiscal;
import com.epsilon.model.movimentacao.ReducaoZ;
import com.epsilon.model.movimentacao.SaldoItensTotalizadoPorLote;

@Entity
@Table(name = "tb_importspedfiscal")
public class LoteImportacaoSpedFiscal implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3777479182714000898L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="est_id")
	private Long idPai;
	private Long idEmp;
	private Long idEst;
	private String codVersao;
	private String codFinalidade;
	private LocalDate dtIni;
	private LocalDate dtFin;
	private String nome;
	private String cnpj;
	private String cpf;
	private String uf;
	private String ie;
	private String codMun;
	private String IM;
	private String suframa;
	private String indPerfil;
	private String indAtiv;

	@OneToMany(cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name="lote_id")
	private List<NotaFiscal> notasFiscais = new ArrayList<NotaFiscal>();
	
	@OneToMany(cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name="lote_id")
	private List<ReducaoZ>    reducoes = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name="lote_id")
	private List<EquipamentoCFe>    equipamentosCfe = new ArrayList<EquipamentoCFe>();
	
	@OneToMany(cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name="lote_id")
	private List<HistoricoItens>    histItens = new ArrayList<HistoricoItens>();
	
	@OneToMany(cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name="lote_id")
	private List<SaldoItensTotalizadoPorLote> saldoPorLote = new ArrayList<SaldoItensTotalizadoPorLote>();
	
	public LoteImportacaoSpedFiscal() {

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

	public String getCodVersao() {
		return codVersao;
	}

	public void setCodVersao(String codVersao) {
		this.codVersao = codVersao;
	}

	public String getCodFinalidade() {
		return codFinalidade;
	}

	public void setCodFinalidade(String codFinalidade) {
		this.codFinalidade = codFinalidade;
	}

	public LocalDate getDtIni() {
		return dtIni;
	}

	public void setDtIni(LocalDate dtIni) {
		this.dtIni = dtIni;
	}

	public LocalDate getDtFin() {
		return dtFin;
	}

	public void setDtFin(LocalDate dtFin) {
		this.dtFin = dtFin;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getIe() {
		return ie;
	}

	public void setIe(String ie) {
		this.ie = ie;
	}

	public String getCodMun() {
		return codMun;
	}

	public void setCodMun(String codMun) {
		this.codMun = codMun;
	}

	public String getIM() {
		return IM;
	}

	public void setIM(String iM) {
		IM = iM;
	}

	public String getSuframa() {
		return suframa;
	}

	public void setSuframa(String suframa) {
		this.suframa = suframa;
	}

	public String getIndPerfil() {
		return indPerfil;
	}

	public void setIndPerfil(String indPerfil) {
		this.indPerfil = indPerfil;
	}

	public String getIndAtiv() {
		return indAtiv;
	}

	public void setIndAtiv(String indAtiv) {
		this.indAtiv = indAtiv;
	}
	
	public List<NotaFiscal> getNotasFiscais() {
		return notasFiscais;
	}

	public void setNotasFiscais(List<NotaFiscal> notasFiscais) {
		this.notasFiscais = notasFiscais;
	}

	public void adicionaNotasSaidas(NotaFiscal notasFiscais) {
		this.notasFiscais.add(notasFiscais);
	}
	
	public List<ReducaoZ> getReducoes() {
		return reducoes;
	}
	public void setReducoes(List<ReducaoZ> reducoes) {
		this.reducoes = reducoes;
	}

	public void adicionaReducoes(ReducaoZ rdz) {
		reducoes.add(rdz);
	}

	public List<EquipamentoCFe> getEquipCfe() {
		return equipamentosCfe;
	}

	public void setEquipCfe(List<EquipamentoCFe> equipamentosCfe) {
		this.equipamentosCfe = equipamentosCfe;
	}
	
	public void adicionaEquipamentoCfe(EquipamentoCFe equipCfe) {
		equipamentosCfe.add(equipCfe);
	}

	public List<HistoricoItens> getHistItens() {
		return histItens;
	}

	public void setHistItens(List<HistoricoItens> histItens) {
		this.histItens = histItens;
	}
	
	public void adicionaHistItem(HistoricoItens histItens) {
       this.histItens.add(histItens);
	}

	public List<SaldoItensTotalizadoPorLote> getSaldoPorLote() {
		return saldoPorLote;
	}

	public void setSaldoPorLote(List<SaldoItensTotalizadoPorLote> saldoPorLote) {
		this.saldoPorLote = saldoPorLote;
	}
	
	public void adicionaSaldoPorLote(SaldoItensTotalizadoPorLote saldoPorLote) {
	       this.saldoPorLote.add(saldoPorLote);
	}

	@Override
	public int hashCode() {
		return Objects.hash(cnpj, dtFin, dtIni);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoteImportacaoSpedFiscal other = (LoteImportacaoSpedFiscal) obj;
		return Objects.equals(cnpj, other.cnpj) && Objects.equals(dtFin, other.dtFin)
				&& Objects.equals(dtIni, other.dtIni);
	}

}
