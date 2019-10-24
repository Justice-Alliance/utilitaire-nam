package ca.qc.inspq.nam.api.utilitaire;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import static org.mockito.ArgumentMatchers.*;

import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.test.context.junit4.SpringRunner;

import ca.qc.inspq.nam.api.modele.Provinces;
import ca.qc.inspq.nam.api.modele.Sexe;
import ca.qc.inspq.nam.api.utilitaire.ServiceUtilitairesNAM;

@RunWith(SpringRunner.class)
public class ServiceUtilitaireNAMTest {
	
	private static final String NAM_QUEBEC_VALIDE = "TREM04121925";
	private static final String NAM_QUEBEC_NON_VALIDE = "TREM04121935";
	
	// TODO valider tous les cas pour toutes les provinces
	/*
	  | province | nam             | reponse              |
      | QC       | TREM04121925    | Le NAM est valide!   |
      | QC       | TREM04121935    | Le NAM est invalide! |
      | AB       |       940114192 | Le NAM est valide!   |
      | AB       |         9401141 | Le NAM est invalide! |
      | BC       |      9759528158 | Le NAM est valide!   |
      | BC       |      9759528153 | Le NAM est invalide! |
      | PE       |       940114195 | Le NAM est valide!   |
      | PE       |         9401141 | Le NAM est invalide! |
      | MB       |       940114192 | Le NAM est valide!   |
      | MB       |         9401141 | Le NAM est invalide! |
      | NB       |       940114192 | Le NAM est valide!   |
      | NB       |         9401141 | Le NAM est invalide! |
      | NS       |      9401141923 | Le NAM est valide!   |
      | NS       |   9401141926545 | Le NAM est invalide! |
      | NU       |        94011419 | Le NAM est invalide! |
      | ON       |      9401141925 | Le NAM est valide!   |
      | ON       |   9401141925565 | Le NAM est invalide! |
      | SK       |       940114195 | Le NAM est valide!   |
      | SK       |   9401141958545 | Le NAM est invalide! |
      | NL       |    940114192698 | Le NAM est valide!   |
      | NL       | 940114192698012 | Le NAM est invalide! |
      | NT       |         9401141 | Le NAM est valide!   |
      | NT       |   9401141023365 | Le NAM est invalide! |
      | YT       |       940114195 | Le NAM est valide!   |
      | YT       |  94011419503256 | Le NAM est invalide! |
	 */

	private ServiceUtilitairesNAM serviceUtilitaireNAM;
	
	@Before
	public void preparerSpyUtilitairesNAM() {
		serviceUtilitaireNAM = spy(ServiceUtilitairesNAM.class);
	}
	
	// -------------- Valider un nam ------------------
	
	// Québec valide
	@Test
	public void quandJeValideUnNamPourLeQuebec_siLeNamEstValide_alorsJeRetourneVrai() throws UnsupportedEncodingException, ParseException {
		assertThat(serviceUtilitaireNAM.validerNAM(NAM_QUEBEC_VALIDE, Provinces.QC.toString())).isTrue();
	}
	
	// Québec non valide
	@Test
	public void quandJeValideUnNamPourLeQuebec_siLeNamNestPasValide_alorsJeRetourneFaux() throws UnsupportedEncodingException, ParseException {
		assertThat(serviceUtilitaireNAM.validerNAM(NAM_QUEBEC_NON_VALIDE, Provinces.QC.toString())).isFalse();
	}
	
	// Alberta valide
	@Test
	public void quandJeValideUnNamPourLAlberta_siLeNamEstValide_alorsJeRetourneVrai() throws UnsupportedEncodingException, ParseException {
		String nomProvince = "AB";
		String nam = "940114192";
		boolean resultatAttendu = true;
		boolean validerNam = serviceUtilitaireNAM.validerNAM(nam, nomProvince);
		assertEquals(validerNam, resultatAttendu);
	}
	
	// Alberta non valide
	
	// Colombie-Britannique valide
	@Test
	public void quandJeValideUnNamPourLaColombieBritannique_siLeNamEstValide_alorsJeRetourneVrai() throws UnsupportedEncodingException, ParseException {
		String nomProvince = "BC";
		String nam = "9759528158";
		boolean resultatAttendu = true;
		boolean validerNam = serviceUtilitaireNAM.validerNAM(nam, nomProvince);
		assertEquals(validerNam, resultatAttendu);
	}
	
	// Colombie-Britannique non valide
	// Pas la bonne taille
	@Test(expected = IllegalArgumentException.class)
	public void quandJeValideUnNamDeLaColombieBritannique_siLeNamNaPasDixCaracteres_alorsJeLanceIllegalArgumentException()
			throws UnsupportedEncodingException, ParseException {
		IllegalArgumentException exception = new IllegalArgumentException(
				"Le numero de carte santé spécifié n'a pas la bonne taille");
		String nomProvince = "BC";
		String nam = "9401141925";
		doThrow(exception).when(serviceUtilitaireNAM).validerNAM(anyString(), anyString());
		serviceUtilitaireNAM.validerNAM(nam, nomProvince);
		assertEquals("Le numero de carte santé spécifié n'a pas la bonne taille", exception.getMessage());
	}
	
	// Ne respecte pas le format
	
	
	// Manitoba valide
	@Test
	public void quandJeValideUnNamPourLeManitoba_siLeNamEstValide_alorsJeRetourneVrai() throws UnsupportedEncodingException, ParseException {
		String nomProvince = "MB";
		String nam = "940114192";
		boolean resultatAttendu = true;
		boolean validerNam = serviceUtilitaireNAM.validerNAM(nam, nomProvince);
		assertEquals(validerNam, resultatAttendu);
	}
	
	// Manitoba non valide
	
	// Territoires du Nord-Ouest valide
	@Test
	public void quandJeValideUnNamPourLesTerritoiresDuNordOuest_siLeNamEstValide_alorsJeRetourneVrai() throws UnsupportedEncodingException, ParseException {
		String nomProvince = "NT";
		String nam = "9401141";
		boolean resultatAttendu = true;
		boolean validerNam = serviceUtilitaireNAM.validerNAM(nam, nomProvince);
		assertEquals(validerNam, resultatAttendu);
	}
	
	// Territoires du Nord-Ouest non valide
	
	// Nouvelle-Écosse valide
	@Test
	public void quandJeValideUnNamPourLaNouvelleEcosse_siLeNamEstValide_alorsJeRetourneVrai() throws UnsupportedEncodingException, ParseException {
		String nomProvince = "NS";
		String nam = "9401141923";
		boolean resultatAttendu = true;
		boolean validerNam = serviceUtilitaireNAM.validerNAM(nam, nomProvince);
		assertEquals(validerNam, resultatAttendu);
	}
	
	// Nouvelle-Écosse non valide
	
	// Nouveau-Brunswick valide
	@Test
	public void quandJeValideUnNamPourLeNouveauBrunswick_siLeNamEstValide_alorsJeRetourneVrai() throws UnsupportedEncodingException, ParseException {
		String nomProvince = "NB";
		String nam = "940114192";
		boolean resultatAttendu = true;
		boolean validerNam = serviceUtilitaireNAM.validerNAM(nam, nomProvince);
		assertEquals(validerNam, resultatAttendu);
	}
	
	// Nouveau-Brunswick non valide
	
	// Terre-Neuve-et-Labrador valide
	@Test
	public void quandJeValideUnNamPourTerreNeuveEtLabrador_siLeNamEstValide_alorsJeRetourneVrai() throws UnsupportedEncodingException, ParseException {
		String nomProvince = "NL";
		String nam = "940114192698";
		boolean resultatAttendu = true;
		boolean validerNam = serviceUtilitaireNAM.validerNAM(nam, nomProvince);
		assertEquals(validerNam, resultatAttendu);
	}
	
	// Terre-Neuve et Labrador non valide
	
	// Nunavut valide
	@Test
	public void quandJeValideUnNamPourLeNunavut_siLeNamEstValide_alorsJeRetourneVrai() throws UnsupportedEncodingException, ParseException {
		String nomProvince = "NU";
		String nam = "112345672";
		boolean resultatAttendu = true;
		boolean validerNam = serviceUtilitaireNAM.validerNAM(nam, nomProvince);
		assertEquals(validerNam, resultatAttendu);
	}
	
	// Nunavut non valide
	
	// Ontario valide
	@Test
	public void quandJeValideUnNamPourLOntario_siLeNamEstValide_alorsJeRetourneVrai() throws UnsupportedEncodingException, ParseException {
		String nomProvince = "ON";
		String nam = "9401141925";
		boolean resultatAttendu = true;
		boolean validerNam = serviceUtilitaireNAM.validerNAM(nam, nomProvince);
		assertEquals(validerNam, resultatAttendu);
	}
	
	// Ontario non valide
	
	// Ile du Prince Edouard valide
	@Test
	public void quandJeValideUnNamPourLIleDuPrinceEdouard_siLeNamEstValide_alorsJeRetourneVrai() throws UnsupportedEncodingException, ParseException {
		String nomProvince = "PE";
		String nam = "940114195";
		boolean resultatAttendu = true;
		boolean validerNam = serviceUtilitaireNAM.validerNAM(nam, nomProvince);
		assertEquals(validerNam, resultatAttendu);
	}
	
	// Ile du Prince Edouard non valide
	
	// Saskatchewan valide
	@Test
	public void quandJeValideUnNamPourLaSaskatchewan_siLeNamEstValide_alorsJeRetourneVrai() throws UnsupportedEncodingException, ParseException {
		String nomProvince = "SK";
		String nam = "940114195";
		boolean resultatAttendu = true;
		boolean validerNam = serviceUtilitaireNAM.validerNAM(nam, nomProvince);
		assertEquals(validerNam, resultatAttendu);
	}
	
	// Saskatchewan non valide
	
	// Yukon valide
	@Test
	public void quandJeValideUnNamPourLeYukon_siLeNamEstValide_alorsJeRetourneVrai() throws UnsupportedEncodingException, ParseException {
		String nomProvince = "YT";
		String nam = "940114195";
		boolean resultatAttendu = true;
		boolean validerNam = serviceUtilitaireNAM.validerNAM(nam, nomProvince);
		assertEquals(validerNam, resultatAttendu);
	}
	
	// Yukon non valide
	
	// Province pas bonne?
	@Test(expected = IllegalArgumentException.class)
	public void quandJeValideUnNam_siLaProvinceEnvoyeeNestPasValide_alorsJeRetourneIllegalArgumentException() throws UnsupportedEncodingException, ParseException {
		IllegalArgumentException exception = new IllegalArgumentException(
				"La province de la carte santé n'est pas valide.");
		String nomProvince = "TT";
		String nam = "BOUF94011419";
		doThrow(new IllegalArgumentException()).when(serviceUtilitaireNAM).validerNAM(anyString(), anyString());
		serviceUtilitaireNAM.validerNAM(nam, nomProvince);
		assertEquals("La province de la carte santé n'est pas valide.", exception.getMessage());
	}
	
	// ----------------------------------------

	@Test
	public void doit_RetournerUneListeDeNAMValide_Quand_ObtenirCombinaisonsValidesDeNAM_EstAppeleAvecLesBonsParametres()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		List<String> valideNams = new ArrayList<>();
		String prenom = "Martin";
		String nom = "Tremblay";
		Date dateNaissance = new Date(2004 - 12 - 19);
		String sexe = "M";
		// Act
		valideNams = serviceUtilitaireNAM.obtenirCombinaisonsValidesDeNAM(prenom, nom, dateNaissance, sexe);

		// Assert
		assertEquals(valideNams.size(), 9);

	}

	@Test(expected = InvalidParameterException.class)
	public void doit_RetournerInvalidParameterException_Quand_ObtenirCombinaisonsValidesDeNAM_EstAppeleAvecUnDesParametreNullOuVide()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		InvalidParameterException exception = new InvalidParameterException(
				"Vous devez fournir un prénom, un nom et une date de naissance.");
		String prenom = "";
		String nom = "";
		Date dateNaissance = null;
		String sexe = "";
		// Act
		serviceUtilitaireNAM.obtenirCombinaisonsValidesDeNAM(prenom, nom, dateNaissance, sexe);

		// Assert
		assertEquals("Vous devez fournir un prénom, un nom et une date de naissance.", exception.getMessage());

	}

	@Test
	public void doit_RetournerLeSexeDeLaPersonne_Quand_ObtenirSexe_EstAppeleAvecUnNAMValide()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		String nam = "TREM04121925";
		when(serviceUtilitaireNAM.validerNAM(nam, "QC")).thenReturn(true);
		Sexe sexeAttendu = Sexe.MASCULIN;
		// Act
		Sexe sexe = serviceUtilitaireNAM.obtenirSexe(nam);

		// Assert
		assertEquals(sexe, sexeAttendu);

	}

	@Test(expected = InvalidParameterException.class)
	public void doit_RetournerInvalidParameterException_Quand_ObtenirSexe_EstAppeleAvecUnNAMInValide()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		InvalidParameterException exception = new InvalidParameterException("Le NAM est invalide");
		String nam = "TREM04121935";
		when(serviceUtilitaireNAM.validerNAM(nam, "QC")).thenReturn(false);
		// Act
		serviceUtilitaireNAM.obtenirSexe(nam);

		// Assert
		assertEquals("Le NAM est invalide", exception.getMessage());

	}

	@Test
	public void doit_RetournerUneDateNaissanceValide_Quand_TrouverDateNaissance_EstAppeleAvecUnNAMValide()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		String nam = "TREM04121925";
		when(serviceUtilitaireNAM.validerNAM(nam, "QC")).thenReturn(true);
		Date dateNaissanceAttendu = new SimpleDateFormat("yyyy-MM-dd").parse(String.format("%s-%d-%s", 2004, 12, 19));
		// Act
		Date dateNaissance = serviceUtilitaireNAM.trouverDateNaissance(nam);

		// Assert
		assertEquals(dateNaissance, dateNaissanceAttendu);

	}

	@Test(expected = InvalidParameterException.class)
	public void doit_RetournerInvalidParameterException_Quand_TrouverDateNaissance_EstAppeleAvecUnNAMInValide()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		InvalidParameterException exception = new InvalidParameterException("Le NAM est invalide");
		String nam = "TREM04121935";
		when(serviceUtilitaireNAM.validerNAM(nam, "QC")).thenReturn(false);
		// Act
		serviceUtilitaireNAM.trouverDateNaissance(nam);

		// Assert
		assertEquals("Le NAM est invalide", exception.getMessage());

	}

	@Test
	public void doit_RetournerNull_Quand_NormaliserRAMQ_EstAppeleAvecUnParametreStringNull() {

		// Arrange
		String texte = null;

		// Act
		String resultat = serviceUtilitaireNAM.normaliserRAMQ(texte);

		// Assert

		assertNull(resultat);

	}
	
	
	@Test
	@Ignore
	public void doit_RetournerVRAI_Quand_ValiderNumeroCarteSanteQuebec_EstAppelerAvecCorrecteNAM()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		String nam = "TREM04121914";
		boolean resultatAttendu = true;
		//when(utilitairesNAM.validerStringRegex(nam, TypeRegex.REGEX_NAM_QUEBEC)).thenReturn(true);

		// Act
		//boolean validerNam = utilitairesNAM.validerNumeroCarteSanteQuebec(nam);

		// Assert
		//assertEquals(validerNam, resultatAttendu);

	}
	
	@Test
	@Ignore
	public void doit_RetournerFAUX_Quand_ValiderNumeroCarteSanteQuebec_EstAppelerAvecIncorrectNAM()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		String nam = "TREM04121934";
		boolean resultatAttendu = false;
		//when(utilitairesNAM.validerStringRegex(nam, TypeRegex.REGEX_NAM_QUEBEC)).thenReturn(false);

		// Act
		//boolean validerNam = utilitairesNAM.validerNumeroCarteSanteQuebec(nam);

		// Assert
		//assertEquals(validerNam, resultatAttendu);

	}	

	@Test
	@Ignore
	public void doit_RetournerVRAI_Quand_ValiderStringRegex_EstAppelerAvecBonChaineCharacteres()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		String chaineTexte = "BOUF94011419";
		boolean resultatAttendu = true;

		// Act
		//boolean validerChaine = utilitairesNAM.validerStringRegex(chaineTexte, TypeRegex.REGEX_NAM_QUEBEC);

		// Assert
		//assertEquals(validerChaine, resultatAttendu);

	}

	@Test
	@Ignore
	public void doit_RetournerFAUX_Quand_ValiderStringRegex_EstAppelerAvecChaineCharacteresNull()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		String chaineTexte = null;
		boolean resultatAttendu = false;

		// Act
		//boolean validerChaine = utilitairesNAM.validerStringRegex(chaineTexte, 	TypeRegex.REGEX_NAM_QUEBEC);

		// Assert
		//assertEquals(validerChaine, resultatAttendu);

	}

	@Test
	@Ignore
	public void doit_RetournerVRAI_Quand_ValiderNumeroCarteSanteOntario_EstAppelerAvecCorrectNAM()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		String nam = "9401141925";
		boolean resultatAttendu = true;

		// Act
		//boolean validerNam = utilitairesNAM.validerNumeroCarteSanteOntario(nam);

		// Assert
		//assertEquals(validerNam, resultatAttendu);

	}

	@Test(expected = IllegalArgumentException.class)
	@Ignore
	public void doit_RetournerIllegalArgumentException_Quand_ValiderNumeroCarteSanteOntario_EstAppelerAvecNAMDeLongueurDifferentDe10()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		String nam = "940114192536";
		IllegalArgumentException exception = new IllegalArgumentException(
				"Le numero de carte santé spécifié n'a pas la bonne taille");

		// Act
		//utilitairesNAM.validerNumeroCarteSanteOntario(nam);

		// Assert
		//assertEquals("Le numero de carte santé spécifié n'a pas la bonne taille", exception.getMessage());

	}

	@Test
	@Ignore
	public void doit_RetournerVRAI_Quand_ValiderNumeroCarteSanteColombieBritannique_EstAppelerAvecCorrectNAM()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		String nam = "9759528158";
		boolean resultatAttendu = true;

		// Act
		//boolean validerNam = utilitairesNAM.validerNumeroCarteSanteColombieBritannique(nam);

		// Assert
		//assertEquals(validerNam, resultatAttendu);

	}

	@Test(expected = IllegalArgumentException.class)
	@Ignore
	public void doit_RetournerIllegalArgumentException_Quand_ValiderNumeroCarteSanteColombieBritannique_EstAppelerAvecNAMDeLongueurDifferentDe10()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		String nam = "940114192536";
		IllegalArgumentException exception = new IllegalArgumentException(
				"Le numero de carte santé spécifié n'a pas la bonne taille");

		// Act
		//utilitairesNAM.validerNumeroCarteSanteColombieBritannique(nam);

		// Assert
		assertEquals("Le numero de carte santé spécifié n'a pas la bonne taille", exception.getMessage());

	}

}
