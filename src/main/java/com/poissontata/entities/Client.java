package com.poissontata.entities;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

@Entity
@DiscriminatorValue("C")
public class Client extends Collaborateur {
	
	@Pattern(regexp="[A-Za-z' ']{4,50}", message="Le nom faut contenir seulement des letters et longeur entre 4 et 50")
	protected String nomSte;
	@Pattern(regexp="[0-9]{10}", message="il faut respecter le format de numero de fax")
	protected String fax;

	public Client() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Client(String nomSte, String telephone, String fax, String addresse,
			Date dateAjoute) {
		super(telephone,addresse, dateAjoute);
		this.nomSte = nomSte;
		this.fax = fax;
		
		// TODO Auto-generated constructor stub
	}
	public String getNomSte() {
		return nomSte;
	}



	public void setNomSte(String nomSte) {
		this.nomSte = nomSte;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	

}
