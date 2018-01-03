package com.poissontata.metier;

import java.util.List;

import org.springframework.data.domain.Page;

import com.poissontata.entities.Collaborateur;
import com.poissontata.entities.Reglement;

public interface CollaborateurMetier {
	
	public Collaborateur findPartenaire(Long id);
	public Page<Collaborateur> findClientByName(String name, int page, int size);
	public Page<Collaborateur> findFournisseurByName(String name, int page, int size);
	public void addPartenaire(Collaborateur collaborateur);
	public void deletePartenaire(Collaborateur collaborateur);
	public void updatePartenaire(Collaborateur collaborateur);
	public Page<Collaborateur> findAllClients(int page, int size);
	public List<Collaborateur> findAllClientsList();
	public List<Collaborateur> findAllFourinsseursList();
	public Page<Collaborateur> findAllFournisseurs(int page, int size);
	public List<Reglement> getAllReglementByPartenaire(Collaborateur collaborateur);
	public Double getSituationFournisseurs(); 
	public Double getSituationClients(); 
	

}
