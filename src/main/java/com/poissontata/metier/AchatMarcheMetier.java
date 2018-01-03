package com.poissontata.metier;

import org.springframework.data.domain.Page;

import com.poissontata.entities.AchatMarche;

public interface AchatMarcheMetier {
	public Page<AchatMarche> getAll(int page, int size);
	public AchatMarche addAchatMarche(AchatMarche achatMarche);
}
