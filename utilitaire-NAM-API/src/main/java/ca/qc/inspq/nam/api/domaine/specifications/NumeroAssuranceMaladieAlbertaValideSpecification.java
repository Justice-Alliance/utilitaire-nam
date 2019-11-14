package ca.qc.inspq.nam.api.domaine.specifications;

import org.springframework.stereotype.Service;

@Service
public class NumeroAssuranceMaladieAlbertaValideSpecification implements NumeroAssuranceMaladieValideSpecification {

	private static final String FORMAT_NAM_ALBERTA_VALIDE = "^[1-9]{1}[0-9]{8}$";
	
	@Override
	public boolean estSatisfaitePar(String nam) {
		return sassurerQueLaChaineDeCaracteresRespecteLexpressionReguliere(nam, FORMAT_NAM_ALBERTA_VALIDE);
	}
}
