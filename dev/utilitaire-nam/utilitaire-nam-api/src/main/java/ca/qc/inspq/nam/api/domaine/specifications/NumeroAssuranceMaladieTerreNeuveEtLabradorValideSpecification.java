package ca.qc.inspq.nam.api.domaine.specifications;

import org.springframework.stereotype.Service;

@Service
public class NumeroAssuranceMaladieTerreNeuveEtLabradorValideSpecification implements NumeroAssuranceMaladieValideSpecification {
	
	private static final String FORMAT_NAM_TERRE_NEUVE_LABRADOR_VALIDE = "^[0-9]{12}$";
	
	@Override
	public boolean estSatisfaitePar (String nam) {
		return sassurerQueLaChaineDeCaracteresRespecteLexpressionReguliere(nam, FORMAT_NAM_TERRE_NEUVE_LABRADOR_VALIDE);
	}
}
