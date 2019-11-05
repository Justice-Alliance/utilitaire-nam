package ca.qc.inspq.nam.api.specifications;

import java.security.InvalidParameterException;

import org.springframework.stereotype.Service;
import ca.qc.inspq.nam.api.modele.Personne;

@Service
public class PersonneGenerationNAMValideSpecification implements PersonneValideSpecification {
	
	@Override
	 public void estSatisfaitePar(Personne personne) {
		if (personne.getPrenomNormalise() == null || personne.getNomNormalise() == null || personne.getSexe() == null || 
				personne.getDateNaissance() == null) {
			throw new InvalidParameterException();
		}
	}

}
