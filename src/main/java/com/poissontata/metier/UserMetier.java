package com.poissontata.metier;


import java.util.List;

import org.springframework.data.domain.Page;

import com.poissontata.entities.Admin;
import com.poissontata.entities.Agent;
import com.poissontata.entities.Collaborateur;
import com.poissontata.entities.CollaborateurCompte;
import com.poissontata.entities.User;

public interface UserMetier {
	public User findUser(String username);
	public void addPartenaireCompte(CollaborateurCompte user);
	public void addAdminCompte(Admin user);
	public void updateUser(User user);
	public void deleteUser(User user);
	public Page<User> getAllUsers(int page,int size);
	public Page<User> findUsersByUsername(String userneme, int page, int size);
	public void toggelActivateCompte(String username);
	public void changePwd(String username, String pwd);
	public void addAgentCompte(Agent user);
	public List<Collaborateur> findPartenaireWithoutCompte();
	public Collaborateur getAccountOwner(String username);
	
	//public void associateRoleTo(String user, String role);
	

}
