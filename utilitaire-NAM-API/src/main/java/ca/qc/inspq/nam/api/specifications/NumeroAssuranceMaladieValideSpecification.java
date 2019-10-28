package ca.qc.inspq.nam.api.specifications;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ca.qc.inspq.nam.api.modele.TypeRegex;

public interface NumeroAssuranceMaladieValideSpecification {

	boolean estSatisfaitePar(String nam) throws UnsupportedEncodingException, ParseException;
	
	default boolean sassurerQueLaChaineDeCaracteresRespecteLexpressionReguliere(String chaineTexte, TypeRegex regEx) {
        if (chaineTexte != null) {
            Pattern pattern = Pattern.compile(regEx.getValue());
            Matcher matcher = pattern.matcher(chaineTexte);

            return matcher.matches();
        }
        return false;
    }
}