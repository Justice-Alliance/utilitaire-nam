package ca.qc.inspq.nam.api.domaine.specifications;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import ca.qc.inspq.nam.api.domaine.modele.Personne;

@Service
public class PersonneGenerationNAMValideSpecification implements Specification<Personne> {
	
	@Override
	 public boolean estSatisfaitePar(Personne personne) {
		return toutesLesInformationsSurLaPersonneSontFournies(personne);
	}
	
	private boolean toutesLesInformationsSurLaPersonneSontFournies(Personne personne) {
		return StringUtils.isNotEmpty(personne.getPrenomNormalise()) &&
				StringUtils.isNotEmpty(personne.getNomNormalise()) && 
				personne.getSexe() != null &&
				personne.getDateNaissance() != null;
	}
}
