package ca.qc.inspq.nam.api.specifications;

import org.springframework.stereotype.Service;

@Service
public class NumeroAssuranceMaladieSaskatchewanValideSpecification implements NumeroAssuranceMaladieValideSpecification {
	
	private static final String FORMAT_NAM_SASKATCHEWAN_VALIDE = "^[0-9]{9}$";
	
	@Override
	public boolean estSatisfaitePar(String nam) {
		return sassurerQueLaChaineDeCaracteresRespecteLexpressionReguliere (nam, FORMAT_NAM_SASKATCHEWAN_VALIDE);
	}

}
