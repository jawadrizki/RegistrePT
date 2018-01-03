package com.poissontata.entities;


import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name="reglements_Fournisseurs")
@DiscriminatorValue("RF")
public class ReglementFournisseurs extends Reglement {

	public ReglementFournisseurs() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReglementFournisseurs(Date dateStart, Date dateEnd, Collaborateur owner) {
		super(dateStart, dateEnd, owner);
		// TODO Auto-generated constructor stub
	}
	
	

}
