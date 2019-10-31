package ca.qc.inspq.nam.api.specifications;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface NumeroAssuranceMaladieValideSpecification {

	boolean estSatisfaitePar(String nam) throws UnsupportedEncodingException, ParseException;
	
	default boolean sassurerQueLaChaineDeCaracteresRespecteLexpressionReguliere(String chaineTexte, String regEx) {
        if (chaineTexte != null) {
            Pattern pattern = Pattern.compile(regEx);
            Matcher matcher = pattern.matcher(chaineTexte);

            return matcher.matches();
        }
        return false;
    }
}
