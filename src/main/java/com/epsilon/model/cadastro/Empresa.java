package com.epsilon.model.cadastro;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="tb_empresa")
public class Empresa implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -340334414186097112L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long      id;
	private String    razaoSocial;
	private String    nmFantasia;
	private String    cnpjBase;
	private String    cpf;
	private LocalDate dtIniAtiv;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name="emp_id")
	private List<Estabelecimento> estabs = new ArrayList<Estabelecimento>();
	
	public Empresa() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getNmFantasia() {
		return nmFantasia;
	}

	public void setNmFantasia(String nmFantasia) {
		this.nmFantasia = nmFantasia;
	}

	public String getCnpjBase() {
		return cnpjBase;
	}

	public void setCnpjBase(String cnpjBase) {
		this.cnpjBase = cnpjBase;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getDtIniAtiv() {
		return dtIniAtiv;
	}

	public void setDtIniAtiv(LocalDate dtIniAtiv) {
		this.dtIniAtiv = dtIniAtiv;
	}
	
	public List<Estabelecimento> getEstabs() {
		return estabs;
	}
	public void setEstabs(List<Estabelecimento> estabs) {
		this.estabs = estabs;
	}

	public void adicionaEstab(Estabelecimento est) {
		this.estabs.add(est);
	}
	
	public void removerEstab(Estabelecimento est) {
		this.estabs.remove(est);
	}

}
