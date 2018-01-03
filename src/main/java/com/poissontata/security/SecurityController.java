package com.poissontata.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poissontata.entities.Collaborateur;
import com.poissontata.metier.CollaborateurManager;
import com.poissontata.metier.UserManager;

@Controller
public class SecurityController {
	@Autowired
	CollaborateurManager collaborateurManager;
	
	@Autowired
	UserManager userManager;
	
	@RequestMapping(value = "/login")
	public String login() {
		return "login";
	}
	@RequestMapping(value = "/")
	public String home() {
		return "redirect:/home";
	}
	@RequestMapping(value = "/inexistants")
	public String inexistants(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			UserDetails userDetails = ((UserDetails)principal);
			System.out.println(userDetails.getAuthorities());
			if(userManager.getAccountOwner(userDetails.getUsername()) != null) {
			Collaborateur collaborateur = collaborateurManager.findPartenaire(userManager.getAccountOwner(userDetails.getUsername()).getId());
			model.addAttribute("partenaire", collaborateur);
			}
		}
		return "404";
	}
	@RequestMapping(value = "/accessDenied")
	public String accessDenied(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			UserDetails userDetails = ((UserDetails)principal);
			System.out.println(userDetails.getAuthorities());
			if(userManager.getAccountOwner(userDetails.getUsername()) != null) {
			Collaborateur collaborateur = collaborateurManager.findPartenaire(userManager.getAccountOwner(userDetails.getUsername()).getId());
			model.addAttribute("partenaire", collaborateur);
			}
		}
		return "accessDenied";
		}
	}
