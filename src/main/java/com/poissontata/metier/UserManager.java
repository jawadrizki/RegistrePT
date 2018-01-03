package com.poissontata.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.poissontata.dao.UserRepository;
import com.poissontata.entities.Admin;
import com.poissontata.entities.Agent;
import com.poissontata.entities.Collaborateur;
import com.poissontata.entities.CollaborateurCompte;
import com.poissontata.entities.User;

@Service
public class UserManager implements UserMetier {
	
	@Autowired
	UserRepository userManager;

	@Override
	public User findUser(String username) {
		return userManager.findOne(username);
	}

	@Override
	public void addPartenaireCompte(CollaborateurCompte user) {
		userManager.addUser(user.getUsername(), user.getPassword(), user.isActive(), "PC", user.getPartenaire());
		userManager.associateRoleTo(user.getUsername(), "USER");
	}
	

	@Override
	public void updateUser(User user) {
		userManager.save(user);
		
	}

	@Override
	public void deleteUser(User user) {
		userManager.delete(user);
		
	}

	@Override
	public void addAdminCompte(Admin user) {
		userManager.addUser(user.getUsername(), user.getPassword(), user.isActive(), "AC",null);
		userManager.associateRoleTo(user.getUsername(), "ADMIN");
		
	}
	@Override
	public void addAgentCompte(Agent user) {
		userManager.addUser(user.getUsername(), user.getPassword(), user.isActive(), "GC",null);
		userManager.associateRoleTo(user.getUsername(), "AGENT");
		
	}

	@Override
	public Page<User> getAllUsers(int page,int size) {
		return userManager.findAll(new PageRequest(page, size));
	}

	@Override
	public Page<User> findUsersByUsername(String username, int page, int size) {
		return userManager.findByUsername(username, new PageRequest(page, size));
	}

	@Override
	public void toggelActivateCompte(String username) {
		User user = userManager.findOne(username);
		if(user.isActive())
			userManager.deactivateCompte(username);
		else
			userManager.activateCompte(username);
		
	}

	@Override
	public void changePwd(String username, String pwd) {
		userManager.changePwd(username, pwd);
		
	}

	public List<Collaborateur> findPartenaireWithoutCompte() {
		return userManager.findPartenaireWithoutCompte();
	}
	
	public Collaborateur getAccountOwner(String username) {
		// TODO Auto-generated method stub
		return userManager.getAccountOwner(username);

	}
	

}
