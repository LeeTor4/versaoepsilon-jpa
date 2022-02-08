package com.epsilon.model.cadastro;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "tb_estabelecimento")
public class Estabelecimento implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="emp_id")
	private Long idPai;
	private String cnpj;
	private String ie;
	private String nome;
	private String nmFantasia;
	
	@OneToOne(cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name="id_end")
    private Endereco end = new Endereco();
	
	@OneToMany(cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name="est_id")
	private List<EquipamentoECF> equipsEcf = new ArrayList<EquipamentoECF>();
	

	@OneToMany(cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name="est_id")
	private List<Produto> produtos = new ArrayList<Produto>();
	
	
	
	@OneToMany(cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name="est_id")
	private List<LoteImportacaoSpedFiscal> lotes = new ArrayList<LoteImportacaoSpedFiscal>();
	

	public Estabelecimento() {

	}

	public Estabelecimento(String cnpj, String nome, String nmFantasia,
			Endereco end, Empresa emp) {
		super();

		this.cnpj = cnpj;
		this.nome = nome;
		this.nmFantasia = nmFantasia;
		this.end = end;

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



	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getIe() {
		return ie;
	}

	public void setIe(String ie) {
		this.ie = ie;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNmFantasia() {
		return nmFantasia;
	}

	public void setNmFantasia(String nmFantasia) {
		this.nmFantasia = nmFantasia;
	}

	public Endereco getEnd() {
		return end;
	}

	public void setEnd(Endereco end) {
		this.end = end;
	}

    
	public List<EquipamentoECF> getEquipEcf() {
		return equipsEcf;
	}

	public void setEquipEcf(List<EquipamentoECF> equipsEcf) {
		this.equipsEcf = equipsEcf;
	}

	public void adicionaEquipEcf(EquipamentoECF equipsEcf) {
		this.equipsEcf.add(equipsEcf);
	}
	public List<Produto> getProduto() {
		return produtos;
	}

	public void setProduto(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public void adicionaProduto(Produto produto) {
		this.produtos.add(produto);
	}
	
	
	
	public List<LoteImportacaoSpedFiscal> getLote() {
		return lotes;
	}

	public void setLote(List<LoteImportacaoSpedFiscal> lote) {
		this.lotes = lote;
	}
	
    public void adicionarLote(LoteImportacaoSpedFiscal lote) {
    	this.lotes.add(lote);
    }

}
