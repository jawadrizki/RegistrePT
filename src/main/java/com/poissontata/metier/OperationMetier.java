package com.poissontata.metier;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import com.poissontata.entities.Operation;

public interface OperationMetier {
	
	public Operation findDocument(Long id);
	public void addDocument(Operation operation);
	public void deleteDocument(Operation operation);
	public void updateDocument(Operation operation);
	public Double getAllOperation(Date date1, Date date2,String type);
	public Integer getCountAllOperation(Date date1, Date date2,String type);
	public Page<Operation> getAllOperationByType(Long idReglement, String type,int size, int page);
	public Long getTotalFactures(Long idReglement);
	public Long getTotalVirement(Long idReglement);
	public Long getTotalBons(Long idReglement);
	public Long getTotalPaiements(Long idReglement);
	public Double getTotalByMonth(Date date,String type);
	public Integer getCountByMonth(Date date,String type);
	

}
