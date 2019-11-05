package ca.qc.inspq.nam.api.specifications;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import org.springframework.stereotype.Service;

@Service
public class NumeroAssuranceMaladieColombieBritanniqueValideSpecification implements NumeroAssuranceMaladieValideSpecification {
	
	private static final String FORMAT_NAM_COLOMBIE_BRITANNIQUE_VALIDE = "^9[0-9]{9}$";

	@Override
	public boolean estSatisfaitePar(String nam) throws UnsupportedEncodingException, ParseException {
		return sassurerQueLaChaineDeCaracteresRespecteLexpressionReguliere(nam, FORMAT_NAM_COLOMBIE_BRITANNIQUE_VALIDE)
               && validerNumeroCarteSanteColombieBritanniqueParAlgorithmeValidation(nam);
	}
	
	private boolean validerNumeroCarteSanteColombieBritanniqueParAlgorithmeValidation(String numeroCarteSante) {
        char[] tableau = numeroCarteSante.toCharArray();
        int checkDigit = Integer.parseInt(String.valueOf(tableau[9]));

        int[] poids = { 2, 4, 8, 5, 10, 9, 7, 3 };
        int[] restes = new int[8];

        for (int i = 1; i < tableau.length - 1; i++) {
            restes[i - 1] = (Integer.parseInt(String.valueOf(tableau[i])) * poids[i - 1]) % 11;
        }

        int somme = 0;
        for (int x : restes) {
            somme += x;
        }

        return 11 - (somme % 11) == checkDigit;
    }
}
