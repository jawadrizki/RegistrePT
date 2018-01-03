package com.poissontata.metier;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.poissontata.dao.OperationRepository;
import com.poissontata.entities.Operation;

@Service
public class OperationManager implements OperationMetier {
	
	@Autowired
	OperationRepository operationRepository;

	@Override
	public Operation findDocument(Long id) {
		Operation operation =operationRepository.findOne(id);
		return operation;

	}

	@Override
	public void addDocument(Operation operation) {
		operationRepository.save(operation);
	}

	@Override
	public void deleteDocument(Operation operation) {
		operationRepository.delete(operation);

	}

	@Override
	public void updateDocument(Operation operation) {
		operationRepository.save(operation);
	}

	@Override
	public Page<Operation> getAllOperationByType(Long idReglement, String type, int size, int page) {
		return operationRepository.getAllOperationsByType(idReglement, type, new PageRequest(page, size));
	}

	@Override
	public Long getTotalFactures(Long idReglement) {
		return operationRepository.getTotalFactures(idReglement);
	}

	@Override
	public Long getTotalVirement(Long idReglement) {
		// TODO Auto-generated method stub
		return operationRepository.getTotalVirments(idReglement);
	}

	@Override
	public Long getTotalBons(Long idReglement) {
		// TODO Auto-generated method stub
		return operationRepository.getTotalBons(idReglement);
	}

	@Override
	public Long getTotalPaiements(Long idReglement) {
		// TODO Auto-generated method stub
		return operationRepository.getTotalPaiements(idReglement);
	}

	@Override
	public Double getTotalByMonth(Date date, String type) {
		Double res = 0D;
		if(operationRepository.getTotalByMonth(new SimpleDateFormat("yyyy-MM-dd").format(date).toString(),type) != null) {
			res = operationRepository.getTotalByMonth(new SimpleDateFormat("yyyy-MM-dd").format(date).toString(),type);
		}
		return res;
	}

	@Override
	public Integer getCountByMonth(Date date, String type) {
		Integer res = 0;
		if(operationRepository.getCountByMonth(new SimpleDateFormat("yyyy-MM-dd").format(date).toString(),type) != null) {
			res = operationRepository.getCountByMonth(new SimpleDateFormat("yyyy-MM-dd").format(date).toString(),type);
		}
		return res;
	}

	@Override
	public Double getAllOperation(Date date1, Date date2, String type) {
		return operationRepository.getTotalOperationsByTime(date1, date2, type);
	}

	@Override
	public Integer getCountAllOperation(Date date1, Date date2, String type) {
		// TODO Auto-generated method stub
		return operationRepository.getCountOperationsByTime(date1, date2, type);
	}

	
	

}
