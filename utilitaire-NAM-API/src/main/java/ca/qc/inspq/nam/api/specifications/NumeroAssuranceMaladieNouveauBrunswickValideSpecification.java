package ca.qc.inspq.nam.api.specifications;

import org.springframework.stereotype.Service;

@Service
public class NumeroAssuranceMaladieNouveauBrunswickValideSpecification implements NumeroAssuranceMaladieValideSpecification {
	
	private static final String FORMAT_NAM_NOUVEAU_BRUNSWICK_VALIDE = "^[0-9]{9}$";
	
	@Override
	public boolean estSatisfaitePar(String nam)
	{
		return sassurerQueLaChaineDeCaracteresRespecteLexpressionReguliere(nam, FORMAT_NAM_NOUVEAU_BRUNSWICK_VALIDE);
	}

}
