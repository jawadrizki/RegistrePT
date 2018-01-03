package com.poissontata.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("GC")
public class Agent extends User {

	public Agent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Agent(String username, String password, boolean active) {
		super(username, password, active);
		// TODO Auto-generated constructor stub
	}
	

}
