package com.poissontata.metier;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.poissontata.dao.OperationRepository;
import com.poissontata.dao.ReglementRepository;
import com.poissontata.entities.Client;
import com.poissontata.entities.Fournisseur;
import com.poissontata.entities.Collaborateur;
import com.poissontata.entities.Reglement;
import com.poissontata.entities.ReglementClients;
import com.poissontata.entities.ReglementFournisseurs;

@Service
public class ReglementManager implements ReglementMetier {
	
	@Autowired
	ReglementRepository reglementRepository;
	@Autowired
	OperationRepository operationRepository;

	@Override
	public Reglement findReglement(Long id) {
		Reglement reglement = null;
		reglement = reglementRepository.findOne(id);
		if(reglement != null) reglement.setDocuments(operationRepository.findDocuemntsByReglement(id));
		return reglement;
	}

	@Override
	public void deleteReglement(Reglement reglement) {
		reglementRepository.delete(reglement);
	}

	@Override
	public void updateReglement(Reglement reglement) {
		reglementRepository.save(reglement);

	}



	@Override
	public void addReglement(Collaborateur collaborateur) {
		if(collaborateur.getClass() == Client.class){
			ReglementClients reglement = new ReglementClients(new Date(), null, collaborateur);
			reglementRepository.desactivateAll(collaborateur.getId());
			reglement.setActive(true);
			reglementRepository.save(reglement);
			
		}else if(collaborateur.getClass() == Fournisseur.class){
			ReglementFournisseurs reglement = new ReglementFournisseurs(new Date(), null, collaborateur);
			reglementRepository.desactivateAll(collaborateur.getId());
			reglement.setActive(true);
			reglementRepository.save(reglement);
		}
		
	}

	@Override
	public List<Reglement> findReglementsByPartenaire(Long idPartenaire) {
		return reglementRepository.findReglementsByPartenaire(idPartenaire);
	}

	@Override
	public Reglement getActiveReglementOf(Long partenaireID) {
		Reglement ActiveRegle = reglementRepository.getActiveReglementOF(partenaireID);
		return ActiveRegle;
		
	}
	@Override
	public Long getTotalFacturesOf(Long partenaireID){
		List<Reglement> reglements = this.findReglementsByPartenaire(partenaireID);
		Long total = 0L;
		for (Reglement reglement : reglements) {
			Long id = reglement.getId();
			if(operationRepository.getTotalFactures(id) != null)
			total = total + operationRepository.getTotalFactures(id);
			else continue;
		}
		return total;
	}
	@Override
	public Long getTotalBonsOf(Long partenaireID){
		List<Reglement> reglements = this.findReglementsByPartenaire(partenaireID);
		Long total = 0L;
		for (Reglement reglement : reglements) {
			Long id = reglement.getId();
			if(operationRepository.getTotalBons(id) != null)
			total = total + operationRepository.getTotalBons(id);
			else continue;
		}
		return total;
	}
	@Override
	public Long getTotalVirmentsOf(Long partenaireID){
		List<Reglement> reglements = this.findReglementsByPartenaire(partenaireID);
		Long total = 0L;
		for (Reglement reglement : reglements) {
			Long id = reglement.getId();
			if(operationRepository.getTotalVirments(id) != null)
			total = total + operationRepository.getTotalVirments(id);
			else continue;
		}
		return total;
	}
	@Override
	public Long getTotalPaiementsOf(Long partenaireID){
		List<Reglement> reglements = this.findReglementsByPartenaire(partenaireID);
		Long total = 0L;
		for (Reglement reglement : reglements) {
			Long id = reglement.getId();
			if(operationRepository.getTotalPaiements(id) != null)
			total = total + operationRepository.getTotalPaiements(id);
			else continue;
		}
		return total;
	}

	@Override
	public void activeThisReglement(Reglement reglement) {
		// TODO Auto-generated method stub
		
	}
	
	


}
