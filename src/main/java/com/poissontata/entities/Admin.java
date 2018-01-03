package com.poissontata.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("AC")
public class Admin extends User{

	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Admin(String username, String password, boolean active) {
		super(username, password, active);
		// TODO Auto-generated constructor stub
	}
	
	

}
