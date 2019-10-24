package ca.qc.inspq.nam.api.utilitaire;

import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import ca.qc.inspq.nam.api.modele.Sexe;
import ca.qc.inspq.nam.api.modele.TypeRegex;

public class ServiceUtilitairesNAM {

    private static final String ENCODAGE_EBCDIC = "Cp1047";

    public boolean validerNAM(String nam, String province)
            throws UnsupportedEncodingException, ParseException {
        if (province.equals("QC")) {
            return validerNumeroCarteSanteQuebec(nam);
        }
        switch (province) {
            case "AB":
                return validerStringRegex(nam, TypeRegex.REGEX_NAM_ALBERTA);
            case "BC":
                return validerStringRegex(nam, TypeRegex.REGEX_NAM_COLOMBIE_BRITANNIQUE)
                        &&
                        validerNumeroCarteSanteColombieBritannique(nam);
            case "MB":
                return validerStringRegex(nam, TypeRegex.REGEX_NAM_MANITOBA);
            case "NT":
                return validerStringRegex(nam, TypeRegex.REGEX_NAM_TERRITOIRES_NO);
            case "NS":
                return validerStringRegex(nam, TypeRegex.REGEX_NAM_NOUVELLE_ECOSSE);
            case "NB":
                return validerStringRegex(nam, TypeRegex.REGEX_NAM_NOUVEAU_BRUNSWICK);
            case "NL":
                return validerStringRegex(nam, TypeRegex.REGEX_NAM_TERRE_NEUVE_LABRADOR);
            case "NU":
                return validerStringRegex(nam, TypeRegex.REGEX_NAM_NUNAVUT);
            case "ON":
                return validerStringRegex(nam, TypeRegex.REGEX_NAM_ONTARIO)
                        &&
                        validerNumeroCarteSanteOntario(nam);
            case "PE":
                return validerStringRegex(nam, TypeRegex.REGEX_NAM_ILE_PRINCE_EDOUARD);
            case "SK":
                return validerStringRegex(nam, TypeRegex.REGEX_NAM_SASKATCHEWAN);
            case "YT":
                return validerStringRegex(nam, TypeRegex.REGEX_NAM_YUKON);
            default:
                throw new IllegalArgumentException("La province de la carte santé n'est pas valide.");
        }
    }
    
    public List<String> obtenirCombinaisonsValidesDeNAM(String prenom,
            String nom,
            Date dateNaissance,
            String sexe) throws UnsupportedEncodingException {
        List<String> nams = new ArrayList<>();

        if (StringUtils.isBlank(prenom)
                || StringUtils.isBlank(nom)
                || StringUtils.isBlank(sexe)
                || dateNaissance == null) {
            throw new InvalidParameterException("Vous devez fournir un prénom, un nom et une date de naissance.");
        }
        // Normaliser le prénom et le nom
        prenom = normaliserRAMQ(prenom);
        nom = normaliserRAMQ(nom);
        // Générer un NAM
        StringBuilder namPartiel = new StringBuilder();
        StringBuilder namReel = new StringBuilder();
        // Si le nom de famille est plus petit que 3 caractères, on rempli avec X.
        if (nom.length() < 3) {
            namPartiel.append(StringUtils.rightPad(nom, 3, "X"));
        }
        else {
            namPartiel.append(nom.substring(0, 3));
        }
        // On prend la première lettre du prénom
        namPartiel.append(prenom.substring(0, 1));

        namReel.append(namPartiel.toString());

        // On prend l'année sur 4 chiffres
        SimpleDateFormat sdfYYYY = new SimpleDateFormat("yyyy");
        namPartiel.append(sdfYYYY.format(dateNaissance));

        SimpleDateFormat sdfYY = new SimpleDateFormat("yy");
        namReel.append(sdfYY.format(dateNaissance));

        // On prend le mois et le sexe
        namPartiel.append(sexe);
        SimpleDateFormat sdfMM = new SimpleDateFormat("MM");
        Integer mm = Integer.parseInt(sdfMM.format(dateNaissance));
        namPartiel.append(StringUtils.leftPad(mm.toString(), 2, "0"));

        if ("F".equals(sexe)) {
            namReel.append(mm + 50);
        }
        else if ("M".equals(sexe)) {
            namReel.append(StringUtils.leftPad(mm.toString(), 2, "0"));
        }
        else {
            //throw new IllegalArgumentException("Vous devez fournir le sexe valide");
            throw new InvalidParameterException("Vous devez fournir le sexe valide, utilisez M ou F.");
        }

        // On prend le jour du mois
        SimpleDateFormat sdfJJ = new SimpleDateFormat("dd");
        String jourNaissance = StringUtils.leftPad(sdfJJ.format(dateNaissance), 2, "0");
        namPartiel.append(jourNaissance);

        namReel.append(jourNaissance);

        // Le caractère de distinction des jumeux va de 1 à 9 ou de A à Z mais dans le cas présent,
        // on ne génère que les possibilités de 1 à 9.
        for (int i = 1; i < 10; i++) {
            String namPartielAvecJumeau = namPartiel.toString().toUpperCase() + i;
            int validateur = calculerCaractereValidateur(namPartielAvecJumeau.getBytes(ENCODAGE_EBCDIC), true);
            nams.add(String.format("%s%d%d", namReel.toString(), i, validateur).toUpperCase());
        }

        return nams;
    }

    public Sexe obtenirSexe(String nam)
            throws NumberFormatException, UnsupportedEncodingException, ParseException {
        nam = nam.toUpperCase();
        if (validerNAM(nam, "QC")) {
            int partieMois = Integer.parseInt(nam.substring(6, 8));
            return partieMois > 50 ? Sexe.FEMININ : Sexe.MASCULIN;
        }
        throw new InvalidParameterException("Le NAM est invalide");
    }

    public Date trouverDateNaissance(String nam) throws UnsupportedEncodingException, ParseException {
        // S'assurer que nous avons 12 caractères pour le NAM et que le format est bon
        nam = nam.toUpperCase();
        boolean valide = validerNAM(nam, "QC");

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

            // C) Multiplier la valeur décimale de chaque caractère du matricule décomposé par
            //    les multiplicateurs respectifs
            // D) Additionner les produits de ces multiplications
            // E) Le caractère validateur est le chiffre dans la position des unités de la somme des produits.
            int caractereValidateur = calculerCaractereValidateur(namConvertiEnDecimal, true);

            // F) Si le code est égal, le NAM est valide.
            valide = v == caractereValidateur;
            while (!valide) {
                // On réessaie avec un usager ayant plus de 100 ans
                courant.add(Calendar.YEAR, -100);
                formatEntree.set2DigitYearStart(courant.getTime());
                annee = formatSortie.format(formatEntree.parse(aaS));
                namRecompose = String.format("%s%s%s%s%02d%s%s", nomi, annee.substring(0, 2), aaS, sx, mm, jj, s);
                namConvertiEnDecimal = namRecompose.getBytes(ENCODAGE_EBCDIC);
                caractereValidateur = calculerCaractereValidateur(namConvertiEnDecimal, true);
                valide = v == caractereValidateur;
            }
            return new SimpleDateFormat("yyyy-MM-dd").parse(String.format("%s-%d-%s", annee, mm, jj));
        }
        throw new InvalidParameterException("Le NAM est invalide");
    }
    
    public String normaliserRAMQ(String text) {
        if (text == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer(text.toLowerCase());
        sb = replaceAll(sb, "[áàâä]", "a");
        sb = replaceAll(sb, "[éèêë]", "e");
        sb = replaceAll(sb, "[íìîï]", "i");
        sb = replaceAll(sb, "[óòôö]", "o");
        sb = replaceAll(sb, "[úùûü]", "u");
        sb = replaceAll(sb, "[ç]", "c");
        sb = replaceAll(sb, "[ñ]", "n");
        sb = replaceAll(sb, "[^0-9a-z%]", "");
        return sb.toString().toUpperCase().replace("SAINTE", "ST").replace("SAINT", "ST");
    }

    private boolean validerNumeroCarteSanteQuebec(String nam)
            throws UnsupportedEncodingException, ParseException {
        // S'assurer que nous avons 12 caractères pour le NAM et que le format est bon
        nam = nam.toUpperCase();
        boolean valide = validerStringRegex(nam, TypeRegex.REGEX_NAM_QUEBEC);

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

    private boolean validerStringRegex(String chaineTexte, TypeRegex regEx) {
        if (chaineTexte != null) {
            Pattern pattern = Pattern.compile(regEx.getValue());
            Matcher matcher = pattern.matcher(chaineTexte);

            return matcher.matches();
        }
        return false;
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

    private StringBuffer replaceAll(StringBuffer sb, String patron, String remplacant) {

        Pattern p = Pattern.compile(patron);
        Matcher m = p.matcher(sb);
        StringBuffer sbOut = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sbOut, remplacant);
        }
        m.appendTail(sbOut);

        return sbOut;
    }
}
