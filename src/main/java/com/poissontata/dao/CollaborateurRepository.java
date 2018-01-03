package com.poissontata.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.poissontata.entities.Collaborateur;
@Transactional
public interface CollaborateurRepository extends JpaRepository<Collaborateur, Long> {
	@Query("select p from partenaires p where p.nomComplet like :mc")
	public Page<Collaborateur> findFournisseursByName(@Param("mc")String name,Pageable pageable);
	@Query("select p from partenaires p where p.nomSte like :mc")
	public Page<Collaborateur> findClientsByName(@Param("mc")String name,Pageable pageable);
	@Query("select p from partenaires p where type = :type")
	public Page<Collaborateur> findAllByType(@Param("type")String type,Pageable pageable);
	@Query("select p from partenaires p where type = :type")
	public List<Collaborateur> findAllByTypeList(@Param("type")String type);
	

}
