package com.poissontata.entities;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

@Entity
@DiscriminatorValue("F")
public class Fournisseur extends Collaborateur {
	
	@Pattern(regexp="[A-Za-z' ']{4,50}", message="Le nom faut contenir seulement des letters et longeur entre 4 et 50")
	protected String nomComplet;
	

	public Fournisseur() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Fournisseur(String nomComplet, String telephone, String addresse,
			Date dateAjoute) {
		super(telephone, addresse, dateAjoute);
		this.nomComplet = nomComplet;
		// TODO Auto-generated constructor stub
	}

	public String getNomComplet() {
		return nomComplet;
	}

	public void setNomComplet(String nomComplet) {
		this.nomComplet = nomComplet;
	}

	

	
	
	
}
