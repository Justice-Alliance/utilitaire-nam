package ca.qc.inspq.nam.api.specifications;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import org.springframework.stereotype.Service;

@Service
public class NumeroAssuranceMaladieTerritoiresDuNordOuestValideSpecification implements NumeroAssuranceMaladieValideSpecification {
	
	private static final String FORMAT_NAM_TERRITOIRES_NORD_OUEST_VALIDE = "^[0-9]{7}$";
	
	@Override
	public boolean estSatisfaitePar(String nam) throws UnsupportedEncodingException, ParseException {
		return sassurerQueLaChaineDeCaracteresRespecteLexpressionReguliere(nam, FORMAT_NAM_TERRITOIRES_NORD_OUEST_VALIDE);
	}

}