package com.poissontata.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("PC")
public class CollaborateurCompte extends User {
	
	
	Long partenaire;

	public Long getPartenaire() {
		return partenaire;
	}

	public void setPartenaire(Long partenaire) {
		this.partenaire = partenaire;
	}

	public CollaborateurCompte() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CollaborateurCompte(String username, String password, boolean active, Long partenaire) {
		super(username, password, active);
		this.partenaire = partenaire;
		// TODO Auto-generated constructor stub
	}
	
	
	
	

}
