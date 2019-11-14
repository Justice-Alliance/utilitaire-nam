package ca.qc.inspq.nam.api.utilitaire;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.qc.inspq.nam.api.modele.CaractereValidateur;
import ca.qc.inspq.nam.api.modele.NAMInfo;
import ca.qc.inspq.nam.api.modele.Personne;
import ca.qc.inspq.nam.api.modele.Sexe;
import ca.qc.inspq.nam.api.modele.Provinces;
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
    
    private static final int DEBUT_NOM_PRENOM_NAM_RECOMPOSE = 0;
    private static final int FIN_NOM_PRENOM_NAM_RECOMPOSE = 4;
    private static final int DEBUT_ANNEE_NAISSANCE_NAM_RECOMPOSE = 4;
    private static final int FIN_ANNEE_NAISSANCE_NAM_RECOMPOSE = 8;
    private static final int DEBUT_MOIS_NAISSANCE_NAM_RECOMPOSE = 9;
    private static final int FIN_MOIS_NAISSANCE_NAM_RECOMPOSE = 11;
    private static final int DEBUT_JOUR_NAISSANCE_NAM_RECOMPOSE = 11;
    private static final int FIN_JOUR_NAISSANCE_NAM_RECOMPOSE = 13;
    private static final int DEBUT_RESTE_SEQUENCE_APRES_ANNEE_NAM_RECOMPOSE = 8;
    private static final int POSITION_CARACTERE_VALIDATEUR_NAM = 11;
    
    private static final int CENT_ANS = 100;

    public boolean validerNAM(String nam, Provinces province) {
        switch (province) {
        	case QC:
        		return numeroAssuranceMaladieQuebecValideSpecification.estSatisfaitePar(nam);
            case AB:
            	return numeroAssuranceMaladieAlbertaValideSpecification.estSatisfaitePar(nam);
            case BC:
            	return numeroAssuranceMaladieColombieBritanniqueValideSpecification.estSatisfaitePar(nam);
            case MB:
                return numeroAssuranceMaladieManitobaValideSpecification.estSatisfaitePar(nam);
            case NT:
                return numeroAssuranceMaladieTerritoiresDuNordOuestValideSpecification.estSatisfaitePar(nam);
            case NS:
                return numeroAssuranceMaladieNouvelleEcosseValideSpecification.estSatisfaitePar(nam);
            case NB:
                return numeroAssuranceMaladieNouveauBrunswickValideSpecification.estSatisfaitePar(nam);
            case NL:
                return numeroAssuranceMaladieTerreNeuveEtLabradorValideSpecification.estSatisfaitePar(nam);
            case NU:
                return numeroAssuranceMaladieNunavutValideSpecification.estSatisfaitePar(nam);
            case ON:
                return numeroAssuranceMaladieOntarioValideSpecification.estSatisfaitePar(nam);
            case PE:
                return numeroAssuranceMaladieIleDuPrinceEdouardValideSpecification.estSatisfaitePar(nam);
            case SK:
                return numeroAssuranceMaladieSaskatchewanValideSpecification.estSatisfaitePar(nam);
            case YT:
                return numeroAssuranceMaladieYukonValideSpecification.estSatisfaitePar(nam);
            default:
                throw new IllegalArgumentException("La province de la carte santé n'est pas valide.");
        }
    }    	
    	
    public List<String> obtenirCombinaisonsValidesDeNAM(Personne personne) throws UnsupportedEncodingException {
    	if (!personneGenerationNAMValideSpecification.estSatisfaitePar(personne)) {
    		throw new IllegalArgumentException();
    	}
        List<String> nams = new ArrayList<>();
        String namReel = construireNAMReel(personne);
        String sequenceGenerationNAM = obtenirSequenceGenerationNAM(personne);
        for (int i = 1; i < 10; i++) {
            String sequenceGenerationNAMAvecJumeau = sequenceGenerationNAM.toString() + i;
            int validateur = new CaractereValidateur(sequenceGenerationNAMAvecJumeau).getValeur();
            nams.add(String.format("%s%d%d", namReel.toString(), i, validateur));
        }
        return nams;
    }
    
    public NAMInfo obtenirInformationsContenuesDansLeNam(String nam) throws ParseException, UnsupportedEncodingException {
    	String namEnMajuscules = nam.toUpperCase();
    	if (validerNAM(namEnMajuscules, Provinces.QC)) {
    		NAMInfo namInfo = new NAMInfo(trouverDateNaissance(namEnMajuscules), obtenirSexe(namEnMajuscules));
        	return namInfo;
    	};
    	throw new IllegalArgumentException("Le NAM est invalide");
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
	       		throw new IllegalArgumentException("Vous devez fournir le sexe valide, utilisez M ou F.");
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

    private Sexe obtenirSexe(String nam) throws UnsupportedEncodingException, ParseException {
    	int partieMois = Integer.parseInt(nam.substring(6, 8));
    	return partieMois > 50 ? Sexe.FEMININ : Sexe.MASCULIN;
    }

    private String trouverDateNaissance(String nam) throws UnsupportedEncodingException, ParseException {
        String namRecompose = creerSequenceValidationNAM(nam);
        int v = Integer.parseInt(nam.substring(POSITION_CARACTERE_VALIDATEUR_NAM));
        int caractereValidateur = new CaractereValidateur(namRecompose).getValeur();
        boolean valide = v == caractereValidateur;
        while (!valide) {
        namRecompose = reduireDeCentAnsDateDeNaissanceDansSequenceDeValidation(namRecompose);
        caractereValidateur = new CaractereValidateur(namRecompose).getValeur();
        valide = v == caractereValidateur;
        }
        int annee = Integer.parseInt(namRecompose.substring(DEBUT_ANNEE_NAISSANCE_NAM_RECOMPOSE, FIN_ANNEE_NAISSANCE_NAM_RECOMPOSE));
        int mois = Integer.parseInt(namRecompose.substring(DEBUT_MOIS_NAISSANCE_NAM_RECOMPOSE, FIN_MOIS_NAISSANCE_NAM_RECOMPOSE));
        int jour = Integer.parseInt(namRecompose.substring(DEBUT_JOUR_NAISSANCE_NAM_RECOMPOSE, FIN_JOUR_NAISSANCE_NAM_RECOMPOSE));
        LocalDate dateDeNaissance = LocalDate.of(annee, mois, jour);
        return dateDeNaissance.toString();
    }

	private String reduireDeCentAnsDateDeNaissanceDansSequenceDeValidation(String namRecompose) {
		return namRecompose.substring(DEBUT_NOM_PRENOM_NAM_RECOMPOSE, FIN_NOM_PRENOM_NAM_RECOMPOSE) 
				+ (Integer.parseInt(namRecompose.substring(DEBUT_ANNEE_NAISSANCE_NAM_RECOMPOSE, FIN_ANNEE_NAISSANCE_NAM_RECOMPOSE)) - CENT_ANS) 
				+ namRecompose.substring(DEBUT_RESTE_SEQUENCE_APRES_ANNEE_NAM_RECOMPOSE);
	}
    
    private String creerSequenceValidationNAM (String nam) throws UnsupportedEncodingException, ParseException {
        String nomi = nam.substring(0, 4);
        String aaS = nam.substring(4, 6);
        int partieMois = Integer.parseInt(nam.substring(6, 8));
        int mm = partieMois % 50;
        String sx = partieMois > 50 ? Sexe.FEMININ.code : Sexe.MASCULIN.code;
        String jj = nam.substring(8, 10);
        String s = nam.substring(10, 11);
        SimpleDateFormat formatEntree = new SimpleDateFormat("yy");
        SimpleDateFormat formatSortie = new SimpleDateFormat("yyyy");
        Calendar courant = Calendar.getInstance();
        courant.add(Calendar.YEAR, -100);
        formatEntree.set2DigitYearStart(courant.getTime());
        String annee = formatSortie.format(formatEntree.parse(aaS));
        String namRecompose = String.format("%s%s%s%s%02d%s%s", nomi, annee.substring(0, 2), aaS, sx, mm, jj, s);
    	return namRecompose;
    }
}
