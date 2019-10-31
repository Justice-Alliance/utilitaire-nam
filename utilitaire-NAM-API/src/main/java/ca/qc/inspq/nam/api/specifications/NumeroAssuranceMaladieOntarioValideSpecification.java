package ca.qc.inspq.nam.api.specifications;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import org.springframework.stereotype.Service;

import ca.qc.inspq.nam.api.modele.TypeRegex;

@Service
public class NumeroAssuranceMaladieOntarioValideSpecification implements NumeroAssuranceMaladieValideSpecification {
	
	@Override
	public boolean estSatisfaitePar(String nam) throws UnsupportedEncodingException, ParseException {
		return sassurerQueLaChaineDeCaracteresRespecteLexpressionReguliere(nam, TypeRegex.REGEX_NAM_ONTARIO) && validerNumeroCarteSanteOntario (nam);
	}
	
	private boolean validerNumeroCarteSanteOntario(String numeroCarteSante) {
        if (numeroCarteSante.length() != 10) {
            throw new IllegalArgumentException("Le numero de carte santé spécifié n'a pas la bonne taille");
        }
        char[] tableau = numeroCarteSante.toCharArray();
        int checkDigit = Integer.parseInt(String.valueOf(tableau[9]));

        int[] luhn = new int[9];

        for (int i = 0; i < tableau.length - 1; i++) {
            if (i % 2 == 0) {
                // Pair, donc position impaire dans la chaine
                luhn[i] = Integer.parseInt(String.valueOf(tableau[i])) * 2;
                if (luhn[i] >= 10) {
                    luhn[i] = luhn[i] - 9;
                }
            }
            else {
                // Impair, donc position paire dans la chaine
                luhn[i] = Integer.parseInt(String.valueOf(tableau[i]));
            }
        }

        int somme = 0;
        for (int x : luhn) {
            somme += x;
        }
        int unite = somme % 10;

        return (10 - unite) == checkDigit;
    }

}
