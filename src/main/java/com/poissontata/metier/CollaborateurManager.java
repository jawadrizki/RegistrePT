package com.poissontata.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.poissontata.dao.CollaborateurRepository;
import com.poissontata.dao.ReglementRepository;
import com.poissontata.entities.Collaborateur;
import com.poissontata.entities.Reglement;

@Service
public class CollaborateurManager implements CollaborateurMetier {
	
	@Autowired
	CollaborateurRepository partenaireManager;
	
	@Autowired
	ReglementManager reglementManager;

	@Override
	public Collaborateur findPartenaire(Long id) {
		Collaborateur collaborateur = partenaireManager.findOne(id);
		collaborateur.setReglements(reglementManager.findReglementsByPartenaire(id));
		return collaborateur;

	}
	

	@Override
	public void addPartenaire(Collaborateur collaborateur) {
		partenaireManager.save(collaborateur);
		reglementManager.addReglement(collaborateur);
	}

	@Override
	public void deletePartenaire(Collaborateur collaborateur) {
		partenaireManager.delete(collaborateur);

	}

	@Override
	public void updatePartenaire(Collaborateur collaborateur) {
		partenaireManager.save(collaborateur);

	}

	@Override
	public Page<Collaborateur> findClientByName(String name, int page, int size) {
		return partenaireManager.findClientsByName(name, new PageRequest(page, size));
	}

	@Override
	public Page<Collaborateur> findAllClients(int page, int size) {
		return partenaireManager.findAllByType("C",new PageRequest(page, size));
	}

	@Override
	public Page<Collaborateur> findAllFournisseurs(int page, int size) {
		return partenaireManager.findAllByType("F",new PageRequest(page, size));
	}

	@Override
	public Page<Collaborateur> findFournisseurByName(String name, int page, int size) {
		return partenaireManager.findFournisseursByName(name, new PageRequest(page, size));
	}

	@Override
	public List<Collaborateur> findAllClientsList() {
		return partenaireManager.findAllByTypeList("C");
	}
	@Override
	public List<Collaborateur> findAllFourinsseursList() {
		return partenaireManager.findAllByTypeList("F");
	}


	@Override
	public List<Reglement> getAllReglementByPartenaire(Collaborateur collaborateur) {
		return reglementManager.findReglementsByPartenaire(collaborateur.getId());
	}


	@Override
	public Double getSituationFournisseurs() {
		List<Collaborateur> fournisseurs = partenaireManager.findAllByTypeList("F");
		Double total = 0D;
		for (Collaborateur collaborateur : fournisseurs) {
			if(reglementManager.getTotalBonsOf(collaborateur.getId()) != null) total += reglementManager.getTotalBonsOf(collaborateur.getId());
			if(reglementManager.getTotalPaiementsOf(collaborateur.getId()) != null) total -= reglementManager.getTotalPaiementsOf(collaborateur.getId());
		}
		return total;
	}


	@Override
	public Double getSituationClients() {
		List<Collaborateur> clients = partenaireManager.findAllByTypeList("C");
		Double total = 0D;
		for (Collaborateur collaborateur : clients) {
			if(reglementManager.getTotalVirmentsOf(collaborateur.getId()) != null) total += reglementManager.getTotalVirmentsOf(collaborateur.getId());
			if(reglementManager.getTotalFacturesOf(collaborateur.getId()) != null) total -= reglementManager.getTotalFacturesOf(collaborateur.getId());
		}
		return total;
	}
	

}
