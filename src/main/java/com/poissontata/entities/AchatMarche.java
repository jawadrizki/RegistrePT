package com.poissontata.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AchatMarche implements Serializable {
	@Id @GeneratedValue
	private Long id;
	private Date date;
	private String idCheque;
	private boolean valide;
	private Double montant;
	private String autres;
	
	public Double getMontant() {
		return montant;
	}
	public void setMontant(Double montant) {
		this.montant = montant;
	}
	
	@Override
	public String toString() {
		return "AchatMarche [id=" + id + ", date=" + date + ", idCheque=" + idCheque + ", valide=" + valide
				+ ", montant=" + montant + ", autres=" + autres + "]";
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getIdCheque() {
		return idCheque;
	}
	public void setIdCheque(String idCheque) {
		this.idCheque = idCheque;
	}
	public boolean isValide() {
		return valide;
	}
	public void setValide(boolean valide) {
		this.valide = valide;
	}
	public String getAutres() {
		return autres;
	}
	public void setAutres(String autres) {
		this.autres = autres;
	}
	public AchatMarche(Date date, String idCheque, boolean valide, String autres) {
		super();
		this.date = date;
		this.idCheque = idCheque;
		this.valide = valide;
		this.autres = autres;
	}
	public AchatMarche() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
