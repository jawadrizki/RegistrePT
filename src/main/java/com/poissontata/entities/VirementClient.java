package com.poissontata.entities;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("V")
public class VirementClient extends Operation {
	
	

	public VirementClient() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VirementClient(Date date, Reglement reglement, Boolean existe, Long montant, String autres) {
		super(date, reglement, existe, montant, autres);
		// TODO Auto-generated constructor stub
	}
	

}
