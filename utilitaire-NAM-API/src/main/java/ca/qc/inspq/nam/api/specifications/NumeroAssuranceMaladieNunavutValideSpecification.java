package ca.qc.inspq.nam.api.specifications;

import org.springframework.stereotype.Service;

@Service
public class NumeroAssuranceMaladieNunavutValideSpecification implements NumeroAssuranceMaladieValideSpecification {
	
	private static final String FORMAT_NAM_NUNAVUT_VALIDE = "^1[0-9]{7}[2-7]{1}$";
	
	@Override
	public boolean estSatisfaitePar(String nam) {
		return sassurerQueLaChaineDeCaracteresRespecteLexpressionReguliere(nam, FORMAT_NAM_NUNAVUT_VALIDE);
	}
}
