package ca.qc.inspq.nam.api.specifications;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import org.springframework.stereotype.Service;

import ca.qc.inspq.nam.api.modele.TypeRegex;

@Service
public class NumeroAssuranceMaladieColombieBritanniqueValideSpecification implements NumeroAssuranceMaladieValideSpecification {

	@Override
	public boolean estSatisfaitePar(String nam) throws UnsupportedEncodingException, ParseException {
		return sassurerQueLaChaineDeCaracteresRespecteLexpressionReguliere(nam, TypeRegex.REGEX_NAM_COLOMBIE_BRITANNIQUE)
               &&
               validerNumeroCarteSanteColombieBritannique(nam);
	}
	
	private boolean validerNumeroCarteSanteColombieBritannique(String numeroCarteSante) {
        if (numeroCarteSante.length() != 10) {
            throw new IllegalArgumentException("Le numero de carte santé spécifié n'a pas la bonne taille");
        }

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
