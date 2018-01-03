package com.poissontata;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.poissontata.dao.OperationRepository;
import com.poissontata.dao.CollaborateurRepository;
import com.poissontata.dao.ReglementRepository;
import com.poissontata.dao.RoleRepository;
import com.poissontata.dao.UserRepository;
import com.poissontata.entities.Admin;
import com.poissontata.entities.Client;
import com.poissontata.entities.Fournisseur;
import com.poissontata.entities.Collaborateur;
import com.poissontata.entities.CollaborateurCompte;
import com.poissontata.entities.Reglement;
import com.poissontata.entities.ReglementClients;
import com.poissontata.entities.Role;
import com.poissontata.entities.User;
import com.poissontata.uploadService.StorageService;

@SpringBootApplication
public class RegisterPtApplication implements CommandLineRunner {
	@Autowired
	OperationRepository operationRepository;
	@Autowired
	CollaborateurRepository collaborateurRepository;
	@Autowired
	ReglementRepository reglementRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	StorageService storageService;
	
	public static void main(String[] args) {
		ApplicationContext context =  SpringApplication.run(RegisterPtApplication.class, args);
		/*Partenaire p = partenaireRepository.findOne(1L);
		PartenaireCompte u = new PartenaireCompte("partenaire2","pass",true,p.getId());
		userRepository.addUser(u.getUsername(), u.getPassword(), u.isActive(), "AC", u.getPartenaire());
		
		Partenaire fournisseur = new Fournisseur("clientfor", "clientfor", "06667766555", "06667766555",
				"addresse", new Date());
		partenaireRepository.save(fournisseur);
		ReglementClients reglement = new ReglementClients(new Date(), new Date(), client);
		reglementRepository.save(reglement);
		
		Client c = (Client) partenaireRepository.findOne(1L);
		*/
		//System.out.println(c.getReglements());
		//System.out.println(partenaireRepository.findByName("%rizki%"));
		//System.out.println(partenaireRepository.findClientByName("%client%"));
	}
	@Override
	public void run(String... arg0) throws Exception {
		storageService.deleteAll();
		storageService.init();
		/*Collection<User> users = new ArrayList<>();
		Collection<Role> roles = new ArrayList<>();
		
		User user = userRepository.save(new Admin("user", "user", true));
		Role role = new Role("USER");
		roles.add(role);
		users.add(user);
		user.setRoles(roles);
		role.setUsers(users);
		roleRepository.save(role);
		*/
		
		
		
		
		
		
	}
}
