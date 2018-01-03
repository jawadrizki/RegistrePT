package com.poissontata.metier;

import java.util.List;


import com.poissontata.entities.Collaborateur;
import com.poissontata.entities.Reglement;

public interface ReglementMetier {
	public Reglement findReglement(Long id);
	public void addReglement(Collaborateur collaborateur);
	public void deleteReglement(Reglement reglement);
	public void updateReglement(Reglement reglement);
	public List<Reglement> findReglementsByPartenaire(Long idPartenaire);
	public Reglement getActiveReglementOf(Long partenaireID);
	public Long getTotalFacturesOf(Long partenaireID);
	public Long getTotalBonsOf(Long partenaireID);
	public Long getTotalVirmentsOf(Long partenaireID);
	public Long getTotalPaiementsOf(Long partenaireID);
	public void activeThisReglement(Reglement reglement);
	
	

}
