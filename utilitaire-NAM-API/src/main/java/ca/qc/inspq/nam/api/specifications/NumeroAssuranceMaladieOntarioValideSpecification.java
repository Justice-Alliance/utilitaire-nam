package ca.qc.inspq.nam.api.specifications;

import org.springframework.stereotype.Service;

@Service
public class NumeroAssuranceMaladieOntarioValideSpecification implements NumeroAssuranceMaladieValideSpecification {
	
	private static final String FORMAT_NAM_ONTARIO_VALIDE = "^[0-9]{10}$";
	
	@Override
	public boolean estSatisfaitePar(String nam) {
		return sassurerQueLaChaineDeCaracteresRespecteLexpressionReguliere(nam, FORMAT_NAM_ONTARIO_VALIDE) && 
				validerNumeroCarteSanteOntarioParAlgorithmeValidation(nam);
	}
	
	private boolean validerNumeroCarteSanteOntarioParAlgorithmeValidation(String numeroCarteSante) {
        char[] tableau = numeroCarteSante.toCharArray();
        int checkDigit = Integer.parseInt(String.valueOf(tableau[9]));

        int[] luhn = new int[9];

        for (int i = 0; i < tableau.length - 1; i++) {
            if (caractereEstAUnePositionImpaireDansLaChaineComplete(i)) {
                luhn[i] = Integer.parseInt(String.valueOf(tableau[i])) * 2;
                if (luhn[i] >= 10) {
                    luhn[i] = luhn[i] - 9;
                }
            }
            else {
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

	private boolean caractereEstAUnePositionImpaireDansLaChaineComplete(int i) {
		return i % 2 == 0;
	}

}
