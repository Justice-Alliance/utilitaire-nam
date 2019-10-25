package ca.qc.inspq.nam.api.specifications;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.stereotype.Service;

import ca.qc.inspq.nam.api.modele.Sexe;
import ca.qc.inspq.nam.api.modele.TypeRegex;

@Service
public class NumeroAssuranceMaladieQuebecValideSpecification implements NumeroAssuranceMaladieValideSpecification {
	
	private static final String ENCODAGE_EBCDIC = "Cp1047";

	@Override
	public boolean estSatisfaitePar(String nam) throws UnsupportedEncodingException, ParseException {
		// S'assurer que nous avons 12 caractères pour le NAM et que le format est bon
        nam = nam.toUpperCase();
        boolean valide = sassurerQueLaChaineDeCaracteresRespecteLexpressionReguliere(nam, TypeRegex.REGEX_NAM_QUEBEC);

        if (valide) {
            // A) Décomposer le NAM
            String nomi = nam.substring(0, 4);
            String aaS = nam.substring(4, 6);
            int partieMois = Integer.parseInt(nam.substring(6, 8));
            int mm = partieMois % 50;
            String sx = partieMois > 50 ? Sexe.FEMININ.code : Sexe.MASCULIN.code;
            String jj = nam.substring(8, 10);
            String s = nam.substring(10, 11);
            int v = Integer.parseInt(nam.substring(11, 12));
            boolean blnDateValide = true;

            // Convertir AA en AAAA : Année de naissance de la personne assurée précédé du siècle.
            // Si le siècle de naissance de la personne assurée est inconnu, il est supposé qu'il
            // a moins de 100 ans
            // Valider que l'année de naissance correspond à l'année de naissance du NAM
            SimpleDateFormat formatEntree = new SimpleDateFormat("yy");
            SimpleDateFormat formatSortie = new SimpleDateFormat("yyyy");
            Calendar courant = Calendar.getInstance();
            courant.add(Calendar.YEAR, -100);
            formatEntree.set2DigitYearStart(courant.getTime());
            String annee = formatSortie.format(formatEntree.parse(aaS));

            // B) Trouver la valeur décimale de chaque caractère du numéro d'assurance maladie
            //    décomposé, obtenu à l'étape A.
            String namRecompose = String.format("%s%s%s%s%02d%s%s", nomi, annee.substring(0, 2), aaS, sx, mm, jj, s);
            byte[] namConvertiEnDecimal = namRecompose.getBytes(ENCODAGE_EBCDIC);

            //Validation sur la date de naissance
            SimpleDateFormat formatDateNaissance = new SimpleDateFormat("yyyyMMdd");
            formatDateNaissance.setLenient(false);
            blnDateValide = formatDateNaissance.parse(
                    annee + ((mm > 9) ? String.valueOf(mm) : "0" + String.valueOf(mm)) + nam.substring(8, 10),
                    new ParsePosition(0)) != null;

            // C) Multiplier la valeur décimale de chaque caractère du matricule décomposé par
            //    les multiplicateurs respectifs
            // D) Additionner les produits de ces multiplications
            // E) Le caractère validateur est le chiffre dans la position des unités de la somme des produits.
            int caractereValidateur = calculerCaractereValidateur(namConvertiEnDecimal, blnDateValide);

            // F) Si le code est égal, le NAM est valide.
            valide = (v == caractereValidateur) && blnDateValide;
            if (!valide) {
                // On réessaie avec un usager ayant plus de 100 ans
                courant.add(Calendar.YEAR, -100);
                formatEntree.set2DigitYearStart(courant.getTime());
                annee = formatSortie.format(formatEntree.parse(aaS));
                namRecompose = String.format("%s%s%s%s%02d%s%s", nomi, annee.substring(0, 2), aaS, sx, mm, jj, s);
                namConvertiEnDecimal = namRecompose.getBytes(ENCODAGE_EBCDIC);
                caractereValidateur = calculerCaractereValidateur(namConvertiEnDecimal, blnDateValide);
                blnDateValide = formatDateNaissance.parse(
                        annee + ((mm > 9) ? String.valueOf(mm) : "0" + String.valueOf(mm)) + nam.substring(8, 10),
                        new ParsePosition(0)) != null;
                valide = (v == caractereValidateur) && blnDateValide;
            }
        }
        return valide;
	}
	
	private int calculerCaractereValidateur(byte[] namConvertiEnDecimal, boolean blnDateValide) {
        // Tableau pour multiplication :
        //
        // N O M I  --  A A A A  --  Sx  M M  --  J J S
        // -------      -------      -------      -----
        // 1 3 7 9      1 7 1 3      4   5 7      6 9 1

        int somme = 0;

        if (!blnDateValide) {
            return -1;
        }

        for (int idx = 0; idx < 14; idx++) {
            switch (idx) {
                case 1:
                    somme += (namConvertiEnDecimal[idx] & 0xff) * 3;
                    break;
                case 2:
                    somme += (namConvertiEnDecimal[idx] & 0xff) * 7;
                    break;
                case 3:
                    somme += (namConvertiEnDecimal[idx] & 0xff) * 9;
                    break;
                case 5:
                    somme += (namConvertiEnDecimal[idx] & 0xff) * 7;
                    break;
                case 7:
                    somme += (namConvertiEnDecimal[idx] & 0xff) * 3;
                    break;
                case 8:
                    somme += (namConvertiEnDecimal[idx] & 0xff) * 4;
                    break;
                case 9:
                    somme += (namConvertiEnDecimal[idx] & 0xff) * 5;
                    break;
                case 10:
                    somme += (namConvertiEnDecimal[idx] & 0xff) * 7;
                    break;
                case 11:
                    somme += (namConvertiEnDecimal[idx] & 0xff) * 6;
                    break;
                case 12:
                    somme += (namConvertiEnDecimal[idx] & 0xff) * 9;
                    break;
                default:
                    somme += namConvertiEnDecimal[idx] & 0xff;
            }
        }
        return somme % 10;
    }
}
