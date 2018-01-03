package com.poissontata.entities;


import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name="reglements_clients")
@DiscriminatorValue("RC")
public class ReglementClients extends Reglement {

	public ReglementClients(Date dateStart, Date dateEnd, Collaborateur owner) {
		super(dateStart, dateEnd, owner);
		// TODO Auto-generated constructor stub
	}
	
	public ReglementClients() {
		// TODO Auto-generated constructor stub
	}
	
	

}
