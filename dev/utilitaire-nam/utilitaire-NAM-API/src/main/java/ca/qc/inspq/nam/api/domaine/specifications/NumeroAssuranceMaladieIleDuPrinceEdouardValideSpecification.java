package ca.qc.inspq.nam.api.domaine.specifications;

import org.springframework.stereotype.Service;

@Service
public class NumeroAssuranceMaladieIleDuPrinceEdouardValideSpecification implements NumeroAssuranceMaladieValideSpecification {
	
	private static final String FORMAT_NAM_ILE_PRINCE_EDOUARD_VALIDE = "^[0-9]{8,9}$";

	@Override
	public boolean estSatisfaitePar(String nam) {
		return sassurerQueLaChaineDeCaracteresRespecteLexpressionReguliere(nam, FORMAT_NAM_ILE_PRINCE_EDOUARD_VALIDE);
	}
}
