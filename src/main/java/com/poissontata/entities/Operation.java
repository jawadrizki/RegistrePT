package com.poissontata.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="operations")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type",discriminatorType=DiscriminatorType.STRING,length=1)
public abstract class Operation implements Serializable {
	@Id @GeneratedValue
	protected Long id;
	protected Date date;
	@ManyToOne
	@JoinColumn(name="reglement")
	protected Reglement reglement;
	protected Boolean documentExiste;
	protected Long montant;
	protected String autres;
	public Operation() {
		super();
	}
	public Operation(Date date, Reglement reglement, Boolean existe, Long montant, String autres) {
		super();
		this.date = date;
		this.reglement = reglement;
		this.documentExiste = existe;
		this.montant = montant;
		this.autres = autres;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Reglement getReglement() {
		return reglement;
	}
	public void setReglement(Reglement reglement) {
		this.reglement = reglement;
	}
	public Long getId() {
		return id;
	}
	public Boolean getDocumentExiste() {
		return documentExiste;
	}
	public void setDocumentExiste(Boolean documentExiste) {
		this.documentExiste = documentExiste;
	}
	public Long getMontant() {
		return montant;
	}
	public void setMontant(Long montant) {
		this.montant = montant;
	}
	public String getAutres() {
		return autres;
	}
	public void setAutres(String autres) {
		this.autres = autres;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	
	
	

	
}
