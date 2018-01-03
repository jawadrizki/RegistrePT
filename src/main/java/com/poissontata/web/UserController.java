package com.poissontata.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poissontata.entities.Agent;
import com.poissontata.entities.Collaborateur;
import com.poissontata.entities.CollaborateurCompte;
import com.poissontata.entities.User;
import com.poissontata.metier.CollaborateurManager;
import com.poissontata.metier.Tools;
import com.poissontata.metier.UserManager;

@Controller
public class UserController {

	@Autowired
	UserManager userManager;
	@Autowired
	CollaborateurManager collaborateurManager;

	@RequestMapping("/users")
	public String users(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "2") int size) {
		Page<User> users = userManager.getAllUsers(page, size);
		model.addAttribute("users", users);
		int[] pages = new int[users.getTotalPages()];
		model.addAttribute("pages", pages);
		return "users";
	}

	@RequestMapping("/searchUser")
	public String searchUsers(Model model, String username, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "2") int size) {
		Page<User> users = userManager.findUsersByUsername("%" + username + "%", page, size);
		model.addAttribute("users", users);
		int[] pages = new int[users.getTotalPages()];
		model.addAttribute("pages", pages);
		return "users";
	}

	@RequestMapping("/deleteUser")
	public String deleteUser(Model model, String username) {
		User user = userManager.findUser(username);
		if (!user.getClass().getSimpleName().equals("Admin")) {
			// userManager.deleteUser(user);
			System.out.println("ok");
		}
		return "redirect:/users";
	}

	@RequestMapping("/activeToggel")
	public String activeToggel(Model model, String username) {
		userManager.toggelActivateCompte(username);
		return "redirect:/users";
	}

	@RequestMapping("/changePwd")
	public String changePswd(Model model, String username) {
		User user = userManager.findUser(username);
		model.addAttribute("user", user);
		return "changePwd";
	}

	@RequestMapping(value = "/savePwd", method = RequestMethod.POST)
	public String savePwd(Model model, String username, String pwd1, String pwd2) {
		List<String> errors = new ArrayList<>();
		User user = userManager.findUser(username);
		model.addAttribute("user", user);
		if (pwd1.equals(pwd2)) {
			if (!Tools.getSpecialCharacterCount(pwd1)){
				
				errors.add("Mot de passe invalid !");
				
				model.addAttribute("errors", errors);
				
			    return "changePwd";
			}else {
				userManager.changePwd(username, pwd1);
				System.out.println(pwd1);
				return "redirect:/users";
				
			}
		} else {
			errors.add("La confirmation de mot de passe n'est pas correct !");
			model.addAttribute("errors", errors);
			return "changePwd";
		}
	}
	
	@RequestMapping("/addUser")
	public String addUser(Model model) {
		model.addAttribute("username", "");
		model.addAttribute("partenaires", userManager.findPartenaireWithoutCompte());
		
		return "addUser";
	}
	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public String saveUser(Model model, String username, String usernamep, String pwd1, String pwd2, String partenaire, String type) {
		
		List<String> errors = new ArrayList<>();
		if((!Tools.getSpecialCharacterCount(username) && type.equals("GC")) 
				|| (!Tools.getSpecialCharacterCount(usernamep) && type.equals("PC"))
				){
			errors.add("nom d'utilisateur invalid !");
			if(type.equals("GC")) model.addAttribute("username", username);
			if(type.equals("PC")) model.addAttribute("username", usernamep);
			model.addAttribute("partenaires", userManager.findPartenaireWithoutCompte());
			model.addAttribute("errors", errors);
			
		    return "addUser";
		}else if (pwd1.equals(pwd2)) {
			if (!Tools.getSpecialCharacterCount(pwd1)){
				
				errors.add("Mot de passe invalid !");
				model.addAttribute("partenaires", userManager.findPartenaireWithoutCompte());
				model.addAttribute("errors", errors);
				if(type.equals("GC")) model.addAttribute("username", username);
				if(type.equals("PC")) model.addAttribute("username", usernamep);
				
				
			    return "addUser";
			}else {
				if(type.equals("GC")){
					Agent agent = new Agent();
					agent.setActive(true);
					agent.setPassword(pwd1);
					agent.setUsername(username);
					userManager.addAgentCompte(agent);
				}else if(type.equals("PC")){
					CollaborateurCompte collaborateurCompte = new CollaborateurCompte();
					Long id = Long.parseLong(partenaire);
					collaborateurCompte.setPartenaire(id);
					collaborateurCompte.setActive(true);
					collaborateurCompte.setPassword(pwd1);
					collaborateurCompte.setUsername(usernamep);
					userManager.addPartenaireCompte(collaborateurCompte);
				}
				return "redirect:/users";
				
			}
		} else {
			errors.add("La confirmation de mot de passe n'est pas correct !");
			model.addAttribute("errors", errors);
			if(type.equals("GC")) model.addAttribute("username", username);
			if(type.equals("PC")) model.addAttribute("username", usernamep);
			model.addAttribute("partenaires", userManager.findPartenaireWithoutCompte());
			return "addUser";
		}
		
		
	}
}
