package ca.qc.inspq.nam.api.specifications;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import org.springframework.stereotype.Service;

@Service
public class NumeroAssuranceMaladieAlbertaValideSpecification implements NumeroAssuranceMaladieValideSpecification {

	private static final String FORMAT_NAM_ALBERTA_VALIDE = "^[1-9]{1}[0-9]{8}$";
	
	@Override
	public boolean estSatisfaitePar(String nam) throws UnsupportedEncodingException, ParseException {
		return sassurerQueLaChaineDeCaracteresRespecteLexpressionReguliere(nam, FORMAT_NAM_ALBERTA_VALIDE);
	}
}
