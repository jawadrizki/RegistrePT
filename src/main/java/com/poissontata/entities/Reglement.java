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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name="reglements")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type",discriminatorType=DiscriminatorType.STRING,length=2)

public abstract class Reglement implements Serializable {
	@Id @GeneratedValue
	protected Long id;
	protected Date dateStart;
	protected Date dateEnd;
	@ManyToOne
	@JoinColumn(name="collaborateur")
	protected Collaborateur collaborateur;
	@OneToMany(mappedBy="reglement" , cascade=CascadeType.REMOVE)
	protected Collection<Operation> operations;
	protected Boolean active;
	
	public Reglement() {
		super();
	}
	public Reglement(Date dateStart, Date dateEnd, Collaborateur collaborateur) {
		super();
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.collaborateur = collaborateur;
	}
	public Date getDateStart() {
		return dateStart;
	}
	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}
	public Date getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}
	public Collaborateur getOwner() {
		return collaborateur;
	}
	public void setOwner(Collaborateur collaborateur) {
		this.collaborateur = collaborateur;
	}
	public Collection<Operation> getDocuments() {
		return operations;
	}
	public void setDocuments(Collection<Operation> operations) {
		this.operations = operations;
	}
	public Long getId() {
		return id;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	
	
	
	

}
