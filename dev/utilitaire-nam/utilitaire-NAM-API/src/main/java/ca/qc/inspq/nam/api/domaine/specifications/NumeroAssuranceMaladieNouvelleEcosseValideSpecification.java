package ca.qc.inspq.nam.api.domaine.specifications;

import org.springframework.stereotype.Service;

@Service
public class NumeroAssuranceMaladieNouvelleEcosseValideSpecification implements NumeroAssuranceMaladieValideSpecification {
	
	private static final String FORMAT_NAM_NOUVELLE_ECOSSE_VALIDE = "^[0-9]{10}$";

	@Override
	public boolean estSatisfaitePar(String nam) {
		return sassurerQueLaChaineDeCaracteresRespecteLexpressionReguliere(nam, FORMAT_NAM_NOUVELLE_ECOSSE_VALIDE);
	}
}
