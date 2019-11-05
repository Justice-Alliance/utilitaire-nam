package ca.qc.inspq.nam.api.utilitaire;

import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.qc.inspq.nam.api.modele.Personne;
import ca.qc.inspq.nam.api.modele.Sexe;
import ca.qc.inspq.nam.api.specifications.NumeroAssuranceMaladieAlbertaValideSpecification;
import ca.qc.inspq.nam.api.specifications.NumeroAssuranceMaladieQuebecValideSpecification;
import ca.qc.inspq.nam.api.specifications.NumeroAssuranceMaladieColombieBritanniqueValideSpecification;
import ca.qc.inspq.nam.api.specifications.NumeroAssuranceMaladieManitobaValideSpecification;
import ca.qc.inspq.nam.api.specifications.NumeroAssuranceMaladieTerritoiresDuNordOuestValideSpecification;
import ca.qc.inspq.nam.api.specifications.NumeroAssuranceMaladieNouvelleEcosseValideSpecification;
import ca.qc.inspq.nam.api.specifications.NumeroAssuranceMaladieNouveauBrunswickValideSpecification;
import ca.qc.inspq.nam.api.specifications.NumeroAssuranceMaladieTerreNeuveEtLabradorValideSpecification;
import ca.qc.inspq.nam.api.specifications.NumeroAssuranceMaladieNunavutValideSpecification;
import ca.qc.inspq.nam.api.specifications.NumeroAssuranceMaladieOntarioValideSpecification;
import ca.qc.inspq.nam.api.specifications.NumeroAssuranceMaladieIleDuPrinceEdouardValideSpecification;
import ca.qc.inspq.nam.api.specifications.NumeroAssuranceMaladieSaskatchewanValideSpecification;
import ca.qc.inspq.nam.api.specifications.NumeroAssuranceMaladieYukonValideSpecification;
import ca.qc.inspq.nam.api.specifications.PersonneGenerationNAMValideSpecification;

@Service
public class ServiceUtilitairesNAM {
	
	@Autowired
	private NumeroAssuranceMaladieQuebecValideSpecification numeroAssuranceMaladieQuebecValideSpecification;
	
	@Autowired
	private NumeroAssuranceMaladieAlbertaValideSpecification numeroAssuranceMaladieAlbertaValideSpecification;
	
	@Autowired
	private NumeroAssuranceMaladieColombieBritanniqueValideSpecification numeroAssuranceMaladieColombieBritanniqueValideSpecification;

	@Autowired
	private NumeroAssuranceMaladieManitobaValideSpecification numeroAssuranceMaladieManitobaValideSpecification;
	
	@Autowired
	private NumeroAssuranceMaladieTerritoiresDuNordOuestValideSpecification numeroAssuranceMaladieTerritoiresDuNordOuestValideSpecification;
	
	@Autowired
	private NumeroAssuranceMaladieNouvelleEcosseValideSpecification numeroAssuranceMaladieNouvelleEcosseValideSpecification;
	
	@Autowired
	private NumeroAssuranceMaladieNouveauBrunswickValideSpecification numeroAssuranceMaladieNouveauBrunswickValideSpecification;
	
	@Autowired
	private NumeroAssuranceMaladieTerreNeuveEtLabradorValideSpecification numeroAssuranceMaladieTerreNeuveEtLabradorValideSpecification;
	
	@Autowired
	private NumeroAssuranceMaladieNunavutValideSpecification numeroAssuranceMaladieNunavutValideSpecification;
	
	@Autowired
	private NumeroAssuranceMaladieOntarioValideSpecification numeroAssuranceMaladieOntarioValideSpecification;
	
	@Autowired
	private NumeroAssuranceMaladieIleDuPrinceEdouardValideSpecification numeroAssuranceMaladieIleDuPrinceEdouardValideSpecification;
	
	@Autowired
	private NumeroAssuranceMaladieSaskatchewanValideSpecification numeroAssuranceMaladieSaskatchewanValideSpecification;
	
	@Autowired
	private NumeroAssuranceMaladieYukonValideSpecification numeroAssuranceMaladieYukonValideSpecification;
	
	@Autowired
	private PersonneGenerationNAMValideSpecification personneGenerationNAMValideSpecification; 
	
	
    private static final String ENCODAGE_EBCDIC = "Cp1047";

    public boolean validerNAM(String nam, String province)
            throws UnsupportedEncodingException, ParseException {
        switch (province) {
        	case "QC":
        		return numeroAssuranceMaladieQuebecValideSpecification.estSatisfaitePar(nam);
            case "AB":
            	return numeroAssuranceMaladieAlbertaValideSpecification.estSatisfaitePar(nam);
            case "BC":
            	return numeroAssuranceMaladieColombieBritanniqueValideSpecification.estSatisfaitePar(nam);
            case "MB":
                return numeroAssuranceMaladieManitobaValideSpecification.estSatisfaitePar(nam);
            case "NT":
                return numeroAssuranceMaladieTerritoiresDuNordOuestValideSpecification.estSatisfaitePar(nam);
            case "NS":
                return numeroAssuranceMaladieNouvelleEcosseValideSpecification.estSatisfaitePar(nam);
            case "NB":
                return numeroAssuranceMaladieNouveauBrunswickValideSpecification.estSatisfaitePar(nam);
            case "NL":
                return numeroAssuranceMaladieTerreNeuveEtLabradorValideSpecification.estSatisfaitePar(nam);
            case "NU":
                return numeroAssuranceMaladieNunavutValideSpecification.estSatisfaitePar(nam);
            case "ON":
                return numeroAssuranceMaladieOntarioValideSpecification.estSatisfaitePar(nam);
            case "PE":
                return numeroAssuranceMaladieIleDuPrinceEdouardValideSpecification.estSatisfaitePar(nam);
            case "SK":
                return numeroAssuranceMaladieSaskatchewanValideSpecification.estSatisfaitePar(nam);
            case "YT":
                return numeroAssuranceMaladieYukonValideSpecification.estSatisfaitePar(nam);
            default:
                throw new IllegalArgumentException("La province de la carte santé n'est pas valide.");
        }
    }    	
    	
    public List<String> obtenirCombinaisonsValidesDeNAM(Personne personne) throws UnsupportedEncodingException {
    	personneGenerationNAMValideSpecification.estSatisfaitePar(personne);
        List<String> nams = new ArrayList<>();
        String namReel = construireNAMReel(personne);
        String namPartiel = obtenirSequenceGenerationNAM(personne);
        for (int i = 1; i < 10; i++) {
            String namPartielAvecJumeau = namPartiel.toString() + i;
            int validateur = calculerCaractereValidateur(namPartielAvecJumeau.getBytes(ENCODAGE_EBCDIC), true);
            nams.add(String.format("%s%d%d", namReel.toString(), i, validateur));
        }
        return nams;
    }

	private String obtenirSequenceGenerationNAM(Personne personne) {
		StringBuilder namPartiel = new StringBuilder();
        namPartiel.append(obtenirNomEtPrenomPourNam(personne));
        namPartiel.append(personne.getDateNaissance().getYear());
        namPartiel.append(personne.getSexe().getCode());
        Integer mm = personne.getDateNaissance().getMonthValue();
        namPartiel.append(StringUtils.leftPad(mm.toString(), 2, "0"));
        String jourNaissance = StringUtils.leftPad(Integer.toString(personne.getDateNaissance().getDayOfMonth()), 2, "0");
        namPartiel.append(jourNaissance);
		return namPartiel.toString();
	}
    
    private String construireNAMReel(Personne personne) {
        StringBuilder namReel = new StringBuilder();
        namReel.append(obtenirNomEtPrenomPourNam(personne));
        namReel.append(Integer.toString(personne.getDateNaissance().getYear()).substring(2));
        switch (personne.getSexe()) {
	        case FEMININ:
	        	namReel.append(personne.getDateNaissance().getMonthValue() + 50);
	        	break;
	        case MASCULIN:
	        	namReel.append(StringUtils.leftPad(Integer.toString(personne.getDateNaissance().getMonthValue()), 2, "0"));
	        	break;
	       	default:
	       		throw new InvalidParameterException("Vous devez fournir le sexe valide, utilisez M ou F.");
	        }
        String jourNaissance = StringUtils.leftPad(Integer.toString(personne.getDateNaissance().getDayOfMonth()), 2, "0");
        namReel.append(jourNaissance);
        return namReel.toString();
    }
    
    private String obtenirNomEtPrenomPourNam(Personne personne) {
    	StringBuilder nomEtPrenomPourNam = new StringBuilder();
        if (personne.getNomNormalise().length() < 3) {
        	nomEtPrenomPourNam.append(StringUtils.rightPad(personne.getNomNormalise(), 3, "X"));
        }
        else {
        	nomEtPrenomPourNam.append(personne.getNomNormalise().substring(0, 3));
        }
        nomEtPrenomPourNam.append(personne.getPrenomNormalise().substring(0, 1));
        return nomEtPrenomPourNam.toString();
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
