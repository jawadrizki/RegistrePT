package com.poissontata.entities;



import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
@Entity
public class Role {
	
	@Id
	private String role;
	
	@ManyToMany(mappedBy="roles")
	private Collection<User> users;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	public Role(String role) {
		super();
		this.role = role;
	}
	
	
	public Role(String role, Collection<User> users) {
		super();
		this.role = role;
		this.users = users;
	}

	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	

}
