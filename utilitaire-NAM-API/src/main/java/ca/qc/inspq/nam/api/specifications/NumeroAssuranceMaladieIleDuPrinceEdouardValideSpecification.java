package ca.qc.inspq.nam.api.specifications;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import org.springframework.stereotype.Service;

import ca.qc.inspq.nam.api.modele.TypeRegex;

@Service
public class NumeroAssuranceMaladieIleDuPrinceEdouardValideSpecification implements NumeroAssuranceMaladieValideSpecification {

	@Override
	public boolean estSatisfaitePar(String nam) throws UnsupportedEncodingException, ParseException {
		return sassurerQueLaChaineDeCaracteresRespecteLexpressionReguliere(nam, TypeRegex.REGEX_NAM_ILE_PRINCE_EDOUARD);
	}
}
