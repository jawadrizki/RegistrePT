package com.poissontata.entities;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("F")
public class Facture extends Operation {

	public Facture() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Facture(Date date, Reglement reglement, Boolean existe, Long montant, String autres) {
		super(date, reglement, existe, montant, autres);
		// TODO Auto-generated constructor stub
	}
	
	

}
