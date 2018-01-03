package com.poissontata.metier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.poissontata.dao.AchatMarcheRepository;
import com.poissontata.entities.AchatMarche;
@Service
public class AchatMarcheManager implements AchatMarcheMetier {
	
	@Autowired
	private AchatMarcheRepository AchatMarcheRepository;
	
	@Override
	public Page<AchatMarche> getAll(int page, int size) {
		return AchatMarcheRepository.findAll(new PageRequest(page, size));
	}
	
	@Override
	public AchatMarche addAchatMarche(AchatMarche achatMarche) {
		return AchatMarcheRepository.save(achatMarche);
	}
	

}
