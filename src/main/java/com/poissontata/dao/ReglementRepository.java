package com.poissontata.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.poissontata.entities.Operation;
import com.poissontata.entities.Reglement;
@Transactional
public interface ReglementRepository extends JpaRepository<Reglement, Long> {
	@Query("select r from reglements r where r.collaborateur.id = :id")
	public List<Reglement> findReglementsByPartenaire(@Param("id")Long idPartenaire);
	
	@Query("select r from reglements r where r.collaborateur.id = :idp and r.active = true")
	public Reglement getActiveReglementOF(@Param("idp")Long idPartenaire);
	
	@Modifying
	@Query("update reglements r set r.active = false where r.collaborateur.id = :idp")
	public void desactivateAll(@Param("idp")Long idPartenaire);
	
	@Modifying
	@Query("update reglements r set r.active = true where r.id = :idr")
	public void activateReglement(@Param("idr")Long idReglement);
	
	
	
	
	
	
	
}
