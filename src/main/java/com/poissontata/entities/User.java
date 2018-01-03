package com.poissontata.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import java.util.Collection;
import java.util.Set;
import javax.persistence.JoinColumn;



@Entity(name="users")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type",discriminatorType=DiscriminatorType.STRING,length=2)
public class User implements Serializable {
	@Id
	protected String username;
	protected String password;
	protected boolean active;
	
	@ManyToMany
    @JoinTable(name = "UsersRoles", joinColumns = @JoinColumn(name = "user", referencedColumnName = "username"), 
    inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "role"))
	private Collection<Role> roles;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public Collection<Role> getRoles() {
		return roles;
	}
	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}
	public User(String username, String password, boolean active) {
		super();
		this.username = username;
		this.password = password;
		this.active = active;
	}
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	

}
