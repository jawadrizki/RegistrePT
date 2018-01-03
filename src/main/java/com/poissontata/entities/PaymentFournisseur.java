package com.poissontata.entities;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("P")
public class PaymentFournisseur extends Operation {
	
	protected String referance;

	public PaymentFournisseur() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PaymentFournisseur(Date date, Reglement reglement, Boolean existe, Long montant, String reference, String autres) {
		super(date, reglement, existe, montant, autres);
		this.referance = reference;
		// TODO Auto-generated constructor stub
	}

	
	
	
	
}
