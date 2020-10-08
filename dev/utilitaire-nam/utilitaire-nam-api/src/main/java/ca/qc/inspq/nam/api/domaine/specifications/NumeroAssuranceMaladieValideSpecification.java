package ca.qc.inspq.nam.api.domaine.specifications;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface NumeroAssuranceMaladieValideSpecification extends Specification<String> {
	
	default boolean sassurerQueLaChaineDeCaracteresRespecteLexpressionReguliere(String chaineTexte, String regEx) {
        if (chaineTexte != null) {
            Pattern pattern = Pattern.compile(regEx);
            Matcher matcher = pattern.matcher(chaineTexte);

            return matcher.matches();
        }
        return false;
    }
}
