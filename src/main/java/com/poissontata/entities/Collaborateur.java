package com.poissontata.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity(name="partenaires")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type",discriminatorType=DiscriminatorType.STRING,length=1)
public abstract class Collaborateur implements Serializable {
	@Id @GeneratedValue 
	protected Long id;
	
	
	@Pattern(regexp="[0-9]{10}", message="il faut respecter le format de numero de telephone")
	protected String telephone;
	
	protected String addresse;
	@OneToMany(mappedBy="collaborateur", cascade=CascadeType.REMOVE)
	protected Collection<Reglement> reglements;
	protected Date dateAjoute;
	
	public Collaborateur(String telephone, String addresse,
			Date dateAjoute) {
		super();
		this.telephone = telephone;
		this.addresse = addresse;
		this.dateAjoute = dateAjoute;
	}
	
	

	public Collaborateur() {
		
	}
	



	@Override
	public String toString() {
		return "Partenaire [id=" + id + ", telephone="
				+ telephone + ", addresse=" + addresse + ", dateAjoute="
				+ dateAjoute + "]";
	}






	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}


	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public String getAddresse() {
		return addresse;
	}
	public void setAddresse(String addresse) {
		this.addresse = addresse;
	}
	public Date getDateAjoute() {
		return dateAjoute;
	}
	public void setDateAjoute(Date dateAjoute) {
		this.dateAjoute = dateAjoute;
	}



	public Collection<Reglement> getReglements() {
		return reglements;
	}



	public void setReglements(Collection<Reglement> reglements) {
		this.reglements = reglements;
	}
	
	
	

}
