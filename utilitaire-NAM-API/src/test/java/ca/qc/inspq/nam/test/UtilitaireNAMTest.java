package ca.qc.inspq.nam.test;

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
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.test.context.junit4.SpringRunner;

import ca.qc.inspq.nam.modele.Sexe;
import ca.qc.inspq.nam.utilitaire.UtilitairesNAM;

@RunWith(SpringRunner.class)
public class UtilitaireNAMTest {

	private UtilitairesNAM utilitairesNAM;
	
	@Before
	public void preparerSpyUtilitairesNAM() {
		utilitairesNAM = spy(UtilitairesNAM.class);
	}

	@Test
	public void doit_RetournerVRAI_Quand_ValiderNAM_EstAppelerAvecNomProvince_QuebecEtUnNAMValide()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		String nomProvince = "QC";
		String nam = "TREM04121925";
		boolean resultatAttendu = true;
		when(utilitairesNAM.validerNumeroCarteSanteQuebec(nam)).thenReturn(true);

		// Act
		boolean validerNam = utilitairesNAM.validerNAM(nam, nomProvince);

		// Assert
		assertEquals(validerNam, resultatAttendu);

	}

	@Test
	public void doit_RetournerFAUX_Quand_ValiderNAM_EstAppelerAvecNomProvince_QuebecEtUnNAMIvalide()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		String nomProvince = "QC";
		String nam = "TREM04121935";
		boolean resultatAttendu = false;
		when(utilitairesNAM.validerNumeroCarteSanteQuebec(nam)).thenReturn(false);

		// Act
		boolean validerNam = utilitairesNAM.validerNAM(nam, nomProvince);

		// Assert
		assertEquals(validerNam, resultatAttendu);

	}

	@Test
	public void doit_RetournerVRAI_Quand_ValiderNAM_EstAppelerAvecNomProvince_Alberta()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		String nomProvince = "AB";
		String nam = "940114192";
		boolean resultatAttendu = true;
		when(utilitairesNAM.validerStringRegex(nam, UtilitairesNAM.TypeRegex.REGEX_NAM_ALBERTA)).thenReturn(true);

		// Act
		boolean validerNam = utilitairesNAM.validerNAM(nam, nomProvince);

		// Assert
		assertEquals(validerNam, resultatAttendu);

	}

	@Test
	public void doit_RetournerVRAI_Quand_ValiderNAM_EstAppelerAvecNomProvince_ColombieBritanique()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		String nomProvince = "BC";
		String nam = "9401141925";
		boolean resultatAttendu = true;
		when(utilitairesNAM.validerStringRegex(nam, UtilitairesNAM.TypeRegex.REGEX_NAM_COLOMBIE_BRITANNIQUE))
				.thenReturn(true);
		when(utilitairesNAM.validerNumeroCarteSanteColombieBritannique(nam)).thenReturn(true);
		// Act
		boolean validerNam = utilitairesNAM.validerNAM(nam, nomProvince);

		// Assert
		assertEquals(validerNam, resultatAttendu);

	}

	@Test
	public void doit_RetournerVRAI_Quand_ValiderNAM_EstAppelerAvecNomProvince_Manitoba()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		String nomProvince = "MB";
		String nam = "940114192";
		boolean resultatAttendu = true;
		when(utilitairesNAM.validerStringRegex(nam, UtilitairesNAM.TypeRegex.REGEX_NAM_MANITOBA)).thenReturn(true);

		// Act
		boolean validerNam = utilitairesNAM.validerNAM(nam, nomProvince);

		// Assert
		assertEquals(validerNam, resultatAttendu);

	}

	@Test
	public void doit_RetournerVRAI_Quand_ValiderNAM_EstAppelerAvecNomProvince_Territoires_DuNord()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		String nomProvince = "NT";
		String nam = "9401141";
		boolean resultatAttendu = true;
		when(utilitairesNAM.validerStringRegex(nam, UtilitairesNAM.TypeRegex.REGEX_NAM_TERRITOIRES_NO))
				.thenReturn(true);

		// Act
		boolean validerNam = utilitairesNAM.validerNAM(nam, nomProvince);

		// Assert
		assertEquals(validerNam, resultatAttendu);

	}

	@Test
	public void doit_RetournerVRAI_Quand_ValiderNAM_EstAppelerAvecNomProvince_NouvelleEcosse()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		String nomProvince = "NS";
		String nam = "9401141923";
		boolean resultatAttendu = true;
		when(utilitairesNAM.validerStringRegex(nam, UtilitairesNAM.TypeRegex.REGEX_NAM_NOUVELLE_ECOSSE))
				.thenReturn(true);

		// Act
		boolean validerNam = utilitairesNAM.validerNAM(nam, nomProvince);

		// Assert
		assertEquals(validerNam, resultatAttendu);

	}

	@Test
	public void doit_RetournerVRAI_Quand_ValiderNAM_EstAppelerAvecNomProvince_NouveauBrunswick()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		String nomProvince = "NB";
		String nam = "940114192";
		boolean resultatAttendu = true;
		when(utilitairesNAM.validerStringRegex(nam, UtilitairesNAM.TypeRegex.REGEX_NAM_NOUVEAU_BRUNSWICK))
				.thenReturn(true);

		// Act
		boolean validerNam = utilitairesNAM.validerNAM(nam, nomProvince);

		// Assert
		assertEquals(validerNam, resultatAttendu);

	}

	@Test
	public void doit_RetournerVRAI_Quand_ValiderNAM_EstAppelerAvecNomProvince_TerreNeuveLabrador()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		String nomProvince = "NL";
		String nam = "940114192698";
		boolean resultatAttendu = true;
		when(utilitairesNAM.validerStringRegex(nam, UtilitairesNAM.TypeRegex.REGEX_NAM_TERRE_NEUVE_LABRADOR))
				.thenReturn(true);

		// Act
		boolean validerNam = utilitairesNAM.validerNAM(nam, nomProvince);

		// Assert
		assertEquals(validerNam, resultatAttendu);

	}

	@Test
	public void doit_RetournerVRAI_Quand_ValiderNAM_EstAppelerAvecNomProvince_Nunavut()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		String nomProvince = "NU";
		String nam = "94011419";
		boolean resultatAttendu = true;
		when(utilitairesNAM.validerStringRegex(nam, UtilitairesNAM.TypeRegex.REGEX_NAM_NUNAVUT)).thenReturn(true);

		// Act
		boolean validerNam = utilitairesNAM.validerNAM(nam, nomProvince);

		// Assert
		assertEquals(validerNam, resultatAttendu);

	}

	@Test
	public void doit_RetournerVRAI_Quand_ValiderNAM_EstAppelerAvecNomProvince_Ontario()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		String nomProvince = "ON";
		String nam = "9401141925";
		boolean resultatAttendu = true;
		when(utilitairesNAM.validerStringRegex(nam, UtilitairesNAM.TypeRegex.REGEX_NAM_ONTARIO)).thenReturn(true);
		when(utilitairesNAM.validerNumeroCarteSanteOntario(nam)).thenReturn(true);
		// Act
		boolean validerNam = utilitairesNAM.validerNAM(nam, nomProvince);

		// Assert
		assertEquals(validerNam, resultatAttendu);

	}

	@Test
	public void doit_RetournerVRAI_Quand_ValiderNAM_EstAppelerAvecNomProvince_IlePrinceEdouard()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		String nomProvince = "PE";
		String nam = "940114195";
		boolean resultatAttendu = true;
		when(utilitairesNAM.validerStringRegex(nam, UtilitairesNAM.TypeRegex.REGEX_NAM_ILE_PRINCE_EDOUARD))
				.thenReturn(true);

		// Act
		boolean validerNam = utilitairesNAM.validerNAM(nam, nomProvince);

		// Assert
		assertEquals(validerNam, resultatAttendu);

	}

	@Test
	public void doit_RetournerVRAI_Quand_ValiderNAM_EstAppelerAvecNomProvince_Saskatchwan()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		String nomProvince = "SK";
		String nam = "940114195";
		boolean resultatAttendu = true;
		when(utilitairesNAM.validerStringRegex(nam, UtilitairesNAM.TypeRegex.REGEX_NAM_SASKATCHEWAN)).thenReturn(true);

		// Act
		boolean validerNam = utilitairesNAM.validerNAM(nam, nomProvince);

		// Assert
		assertEquals(validerNam, resultatAttendu);

	}

	@Test
	public void doit_RetournerVRAI_Quand_ValiderNAM_EstAppelerAvecNomProvince_Yukon()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		String nomProvince = "YT";
		String nam = "940114195";
		boolean resultatAttendu = true;
		when(utilitairesNAM.validerStringRegex(nam, UtilitairesNAM.TypeRegex.REGEX_NAM_YUKON)).thenReturn(true);

		// Act
		boolean validerNam = utilitairesNAM.validerNAM(nam, nomProvince);

		// Assert
		assertEquals(validerNam, resultatAttendu);

	}

	@Test(expected = IllegalArgumentException.class)
	public void doit_RetournerIllegalArgumentException_Quand_ValiderNAM_EstAppelerAvecNomProvince_CorrespondAAucuneProvince()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		IllegalArgumentException exception = new IllegalArgumentException(
				"La province de la carte santé n'est pas valide.");
		String nomProvince = "TT";
		String nam = "BOUF94011419";
		doThrow(new IllegalArgumentException()).when(utilitairesNAM).validerNAM(anyString(), anyString());
		utilitairesNAM.validerNumeroCarteSanteQuebec(nam);

		// Act
		utilitairesNAM.validerNAM(nam, nomProvince);

		// Assert
		assertEquals("La province de la carte santé n'est pas valide.", exception.getMessage());

	}

	@Test(expected = IllegalArgumentException.class)
	public void doit_RetournerIllegalArgumentException_Quand_ValiderNAM_EstAppelerAvecNomProvince_ColombieBritanique_EtNombreCharactersEstDifferentDe10()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		IllegalArgumentException exception = new IllegalArgumentException(
				"Le numero de carte santé spécifié n'a pas la bonne taille");
		String nomProvince = "BC";
		String nam = "9401141925";
		when(utilitairesNAM.validerStringRegex(nam, UtilitairesNAM.TypeRegex.REGEX_NAM_COLOMBIE_BRITANNIQUE))
				.thenReturn(true);
		doThrow(exception).when(utilitairesNAM).validerNAM(anyString(), anyString());
		utilitairesNAM.validerNumeroCarteSanteColombieBritannique(nam);

		// Act
		utilitairesNAM.validerNAM(nam, nomProvince);

		// Assert
		assertEquals("Le numero de carte santé spécifié n'a pas la bonne taille", exception.getMessage());

	}

	@Test
	public void doit_RetournerVRAI_Quand_ValiderNumeroCarteSanteQuebec_EstAppelerAvecCorrecteNAM()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		String nam = "TREM04121914";
		boolean resultatAttendu = true;
		when(utilitairesNAM.validerStringRegex(nam, UtilitairesNAM.TypeRegex.REGEX_NAM_QUEBEC)).thenReturn(true);

		// Act
		boolean validerNam = utilitairesNAM.validerNumeroCarteSanteQuebec(nam);

		// Assert
		assertEquals(validerNam, resultatAttendu);

	}

	@Test
	public void doit_RetournerFAUX_Quand_ValiderNumeroCarteSanteQuebec_EstAppelerAvecIncorrectNAM()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		String nam = "TREM04121934";
		boolean resultatAttendu = false;
		when(utilitairesNAM.validerStringRegex(nam, UtilitairesNAM.TypeRegex.REGEX_NAM_QUEBEC)).thenReturn(false);

		// Act
		boolean validerNam = utilitairesNAM.validerNumeroCarteSanteQuebec(nam);

		// Assert
		assertEquals(validerNam, resultatAttendu);

	}

	@Test
	public void doit_RetournerVRAI_Quand_ValiderStringRegex_EstAppelerAvecBonChaineCharacteres()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		String chaineTexte = "BOUF94011419";
		boolean resultatAttendu = true;

		// Act
		boolean validerChaine = utilitairesNAM.validerStringRegex(chaineTexte,
				UtilitairesNAM.TypeRegex.REGEX_NAM_QUEBEC);

		// Assert
		assertEquals(validerChaine, resultatAttendu);

	}

	@Test
	public void doit_RetournerFAUX_Quand_ValiderStringRegex_EstAppelerAvecChaineCharacteresNull()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		String chaineTexte = null;
		boolean resultatAttendu = false;

		// Act
		boolean validerChaine = utilitairesNAM.validerStringRegex(chaineTexte,
				UtilitairesNAM.TypeRegex.REGEX_NAM_QUEBEC);

		// Assert
		assertEquals(validerChaine, resultatAttendu);

	}

	@Test
	public void doit_RetournerVRAI_Quand_ValiderNumeroCarteSanteOntario_EstAppelerAvecCorrectNAM()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		String nam = "9401141925";
		boolean resultatAttendu = true;

		// Act
		boolean validerNam = utilitairesNAM.validerNumeroCarteSanteOntario(nam);

		// Assert
		assertEquals(validerNam, resultatAttendu);

	}

	@Test(expected = IllegalArgumentException.class)
	public void doit_RetournerIllegalArgumentException_Quand_ValiderNumeroCarteSanteOntario_EstAppelerAvecNAMDeLongueurDifferentDe10()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		String nam = "940114192536";
		IllegalArgumentException exception = new IllegalArgumentException(
				"Le numero de carte santé spécifié n'a pas la bonne taille");

		// Act
		utilitairesNAM.validerNumeroCarteSanteOntario(nam);

		// Assert
		assertEquals("Le numero de carte santé spécifié n'a pas la bonne taille", exception.getMessage());

	}

	@Test
	public void doit_RetournerVRAI_Quand_ValiderNumeroCarteSanteColombieBritannique_EstAppelerAvecCorrectNAM()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		String nam = "9759528158";
		boolean resultatAttendu = true;

		// Act
		boolean validerNam = utilitairesNAM.validerNumeroCarteSanteColombieBritannique(nam);

		// Assert
		assertEquals(validerNam, resultatAttendu);

	}

	@Test(expected = IllegalArgumentException.class)
	public void doit_RetournerIllegalArgumentException_Quand_ValiderNumeroCarteSanteColombieBritannique_EstAppelerAvecNAMDeLongueurDifferentDe10()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		String nam = "940114192536";
		IllegalArgumentException exception = new IllegalArgumentException(
				"Le numero de carte santé spécifié n'a pas la bonne taille");

		// Act
		utilitairesNAM.validerNumeroCarteSanteColombieBritannique(nam);

		// Assert
		assertEquals("Le numero de carte santé spécifié n'a pas la bonne taille", exception.getMessage());

	}

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
		valideNams = utilitairesNAM.obtenirCombinaisonsValidesDeNAM(prenom, nom, dateNaissance, sexe);

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
		utilitairesNAM.obtenirCombinaisonsValidesDeNAM(prenom, nom, dateNaissance, sexe);

		// Assert
		assertEquals("Vous devez fournir un prénom, un nom et une date de naissance.", exception.getMessage());

	}

	@Test
	public void doit_RetournerLeSexeDeLaPersonne_Quand_ObtenirSexe_EstAppeleAvecUnNAMValide()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		String nam = "TREM04121925";
		when(utilitairesNAM.validerNAM(nam, "QC")).thenReturn(true);
		Sexe sexeAttendu = Sexe.MASCULIN;
		// Act
		Sexe sexe = utilitairesNAM.obtenirSexe(nam);

		// Assert
		assertEquals(sexe, sexeAttendu);

	}

	@Test(expected = InvalidParameterException.class)
	public void doit_RetournerInvalidParameterException_Quand_ObtenirSexe_EstAppeleAvecUnNAMInValide()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		InvalidParameterException exception = new InvalidParameterException("Le NAM est invalide");
		String nam = "TREM04121935";
		when(utilitairesNAM.validerNAM(nam, "QC")).thenReturn(false);
		// Act
		utilitairesNAM.obtenirSexe(nam);

		// Assert
		assertEquals("Le NAM est invalide", exception.getMessage());

	}

	@Test
	public void doit_RetournerUneDateNaissanceValide_Quand_TrouverDateNaissance_EstAppeleAvecUnNAMValide()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		String nam = "TREM04121925";
		when(utilitairesNAM.validerNAM(nam, "QC")).thenReturn(true);
		Date dateNaissanceAttendu = new SimpleDateFormat("yyyy-MM-dd").parse(String.format("%s-%d-%s", 2004, 12, 19));
		// Act
		Date dateNaissance = utilitairesNAM.trouverDateNaissance(nam);

		// Assert
		assertEquals(dateNaissance, dateNaissanceAttendu);

	}

	@Test(expected = InvalidParameterException.class)
	public void doit_RetournerInvalidParameterException_Quand_TrouverDateNaissance_EstAppeleAvecUnNAMInValide()
			throws UnsupportedEncodingException, ParseException {

		// Arrange
		InvalidParameterException exception = new InvalidParameterException("Le NAM est invalide");
		String nam = "TREM04121935";
		when(utilitairesNAM.validerNAM(nam, "QC")).thenReturn(false);
		// Act
		utilitairesNAM.trouverDateNaissance(nam);

		// Assert
		assertEquals("Le NAM est invalide", exception.getMessage());

	}

	@Test
	public void doit_RetournerNull_Quand_NormaliserRAMQ_EstAppeleAvecUnParametreStringNull() {

		// Arrange
		String texte = null;

		// Act
		String resultat = utilitairesNAM.normaliserRAMQ(texte);

		// Assert

		assertNull(resultat);

	}

}
