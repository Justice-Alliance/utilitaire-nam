package ca.qc.inspq.nam.api.domaine.specifications;

import org.springframework.stereotype.Service;

@Service
public class NumeroAssuranceMaladieYukonValideSpecification implements NumeroAssuranceMaladieValideSpecification {
	
	private static final String FORMAT_NAM_YUKON_VALIDE = "^[0-9]{9}$";
	
	@Override
	public boolean estSatisfaitePar(String nam) {
		return sassurerQueLaChaineDeCaracteresRespecteLexpressionReguliere (nam, FORMAT_NAM_YUKON_VALIDE);
	}

}
