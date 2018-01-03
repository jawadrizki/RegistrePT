package com.poissontata.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.poissontata.entities.Operation;

@Transactional
public interface OperationRepository extends JpaRepository<Operation, Long> {
	@Query("select d from operations d where d.reglement.id = :idregl")
	public List<Operation> findDocuemntsByReglement(@Param("idregl")Long idReglement);
	@Query("select sum(o.montant) from operations o where type = 'V' and o.reglement.id = :regl")
	public Long getTotalVirments(@Param("regl")Long idReglement);
	@Query("select sum(o.montant) from operations o where type = 'F' and o.reglement.id = :regl")
	public Long getTotalFactures(@Param("regl")Long idReglement);
	@Query("select sum(o.montant) from operations o where type = 'P' and o.reglement.id = :regl")
	public Long getTotalPaiements(@Param("regl")Long idReglement);
	@Query("select sum(o.montant) from operations o where type = 'B' and o.reglement.id = :regl")
	public Long getTotalBons(@Param("regl")Long idReglement);
	@Query("select o from operations o where o.reglement.id = :idRegl and type = :type")
	public Page<Operation> getAllOperationsByType(@Param("idRegl")Long idReglement, @Param("type")String type,Pageable pageable);
	@Query("select sum(montant) from operations where month(date) = month(:date) and type = :type")
	public Double getTotalByMonth(@Param("date")String date, @Param("type")String type);
	@Query("select Count(*) from operations where month(date) = month(:date) and type = :type")
	public Integer getCountByMonth(@Param("date")String date, @Param("type")String type);
	@Query("select sum(montant) from operations where (date between :date2 and :date1) and type = :type")
	public Double getTotalOperationsByTime(@Param("date1")Date date1, @Param("date2")Date date2, @Param("type")String type);
	@Query("select count(montant) from operations where (date between :date2 and :date1) and type = :type")
	public Integer getCountOperationsByTime(@Param("date1")Date date1, @Param("date2")Date date2, @Param("type")String type);

}
