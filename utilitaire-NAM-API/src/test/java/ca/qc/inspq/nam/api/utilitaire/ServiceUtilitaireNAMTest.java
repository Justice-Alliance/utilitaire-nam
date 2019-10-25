package ca.qc.inspq.nam.api.utilitaire;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.mockito.ArgumentMatchers.*;

import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import ca.qc.inspq.nam.api.modele.Provinces;
import ca.qc.inspq.nam.api.modele.Sexe;
import ca.qc.inspq.nam.api.specifications.NumeroAssuranceMaladieAlbertaValideSpecification;
import ca.qc.inspq.nam.api.specifications.NumeroAssuranceMaladieQuebecValideSpecification;
import ca.qc.inspq.nam.api.utilitaire.ServiceUtilitairesNAM;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceUtilitaireNAMTest {
	
	private static final String NAM_QUEBEC_VALIDE = "TREM04121925";

	
	private static final String NAM_ALBERTA_VALIDE = "940114192";
	private static final String NAM_COLOMBIE_BRITANNIQUE_VALIDE = "9759528158";
	private static final String NAM_COLOMBIE_BRITANNIQUE_NON_VALIDE = "9759528153";
	private static final String NAM_MANITOBA_VALIDE = "940114192";
	private static final String NAM_MANITOBA_NON_VALIDE = "9401141";
	private static final String NAM_TERRITOIRES_DU_NORD_OUEST_VALIDE = "9401141";
	private static final String NAM_TERRITOIRES_DU_NORD_OUEST_NON_VALIDE = "9401141023365";
	private static final String NAM_NOUVELLE_ECOSSE_VALIDE = "9401141923";
	private static final String NAM_NOUVELLE_ECOSSE_NON_VALIDE = "9401141926545";
	private static final String NAM_NOUVEAU_BRUNSWICK_VALIDE = "940114192";
	private static final String NAM_NOUVEAU_BRUNSWICK_NON_VALIDE = "9401141";
	private static final String NAM_TERRE_NEUVE_ET_LABRADOR_VALIDE = "940114192698";
	private static final String NAM_TERRE_NEUVE_ET_LABRADOR_NON_VALIDE = "940114192698012";
	private static final String NAM_NUNAVUT_VALIDE = "112345672";
	private static final String NAM_NUNAVUT_NON_VALIDE = "94011419";
	private static final String NAM_ONTARIO_VALIDE = "9401141925";
	private static final String NAM_ONTARIO_NON_VALIDE = "9401141926";
	private static final String NAM_ILE_DU_PRINCE_EDOUARD_VALIDE = "940114195";
	private static final String NAM_ILE_DU_PRINCE_EDOUARD_NON_VALIDE = "9401141";
	private static final String NAM_SASKATCHEWAN_VALIDE = "940114195";
	private static final String NAM_SASKATCHEWAN_NON_VALIDE = "9401141958545";
	private static final String NAM_YUKON_VALIDE = "940114195";
	private static final String NAM_YUKON_NON_VALIDE = "94011419503256"; 
	
	private static final String PROVINCE_NON_VALIDE = "TT";
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@SpyBean
	private ServiceUtilitairesNAM serviceUtilitaireNAM;
	
	@MockBean
	private NumeroAssuranceMaladieQuebecValideSpecification numeroAssuranceMaladieQuebecValideSpecification;
	
	@MockBean
	private NumeroAssuranceMaladieAlbertaValideSpecification numeroAssuranceMaladieAlbertaValideSpecification;
	
	// -------------- Valider un nam ------------------
	@Test
	public void quandJeValideUnNamPourleQuebec_alorsJappelleLaSpecificationPourNamDuQuebecValide() throws UnsupportedEncodingException, ParseException {
		serviceUtilitaireNAM.validerNAM(NAM_QUEBEC_VALIDE, Provinces.QC.toString());
		verify(numeroAssuranceMaladieQuebecValideSpecification, times(1)).estSatisfaitePar(eq(NAM_QUEBEC_VALIDE));
	}
	
	@Test
	public void quandJeValideUnNamPourLeQuebec_alorsJeRetourneSonEtatDeValidite() throws UnsupportedEncodingException, ParseException {
		when(numeroAssuranceMaladieQuebecValideSpecification.estSatisfaitePar(eq(NAM_QUEBEC_VALIDE))).thenReturn(true);
		var resultat = serviceUtilitaireNAM.validerNAM(NAM_QUEBEC_VALIDE, Provinces.QC.toString());
		assertThat(resultat).isTrue();
	}
	
	@Test
	public void quandJeValideUnNamPourAlberta_alorsJappelleLaSpecificationPourNamDeAlbertaValide() throws UnsupportedEncodingException, ParseException {
		serviceUtilitaireNAM.validerNAM(NAM_ALBERTA_VALIDE, Provinces.AB.toString());
		verify(numeroAssuranceMaladieAlbertaValideSpecification, times(1)).estSatisfaitePar(eq(NAM_ALBERTA_VALIDE));
	}
	
	@Test
	public void quandJeValideUnNamPourAlberta_alorsJeRetourneSonEtatDeValidite() throws UnsupportedEncodingException, ParseException {
		when(numeroAssuranceMaladieAlbertaValideSpecification.estSatisfaitePar(eq(NAM_ALBERTA_VALIDE))).thenReturn(true);
		var resultat = serviceUtilitaireNAM.validerNAM(NAM_ALBERTA_VALIDE, Provinces.AB.toString());
		assertThat(resultat).isTrue();
	}
	
	
	
	@Test
	public void quandJeValideUnNamPourLaColombieBritannique_siLeNamEstValide_alorsJeRetourneVrai() throws UnsupportedEncodingException, ParseException {
		assertThat(serviceUtilitaireNAM.validerNAM(NAM_COLOMBIE_BRITANNIQUE_VALIDE, Provinces.BC.toString())).isTrue();
	}
	
	@Test
	public void quandJeValideUnNamDeLaColombieBritannique_siLeNamNEstPasValide_alorsJeRetourneFaux() throws UnsupportedEncodingException, ParseException {
		assertThat(serviceUtilitaireNAM.validerNAM(NAM_COLOMBIE_BRITANNIQUE_NON_VALIDE, Provinces.BC.toString())).isFalse();
	}
	
	@Test
	public void quandJeValideUnNamPourLeManitoba_siLeNamEstValide_alorsJeRetourneVrai() throws UnsupportedEncodingException, ParseException {
		assertThat(serviceUtilitaireNAM.validerNAM(NAM_MANITOBA_VALIDE, Provinces.MB.toString())).isTrue();
	}
	
	@Test
	public void quandJeValideUnNamPourLeManitoba_siLeNamNEstPasValide_alorsJeRetourneFaux() throws UnsupportedEncodingException, ParseException {
		assertThat(serviceUtilitaireNAM.validerNAM(NAM_MANITOBA_NON_VALIDE, Provinces.MB.toString())).isFalse();
	}
	
	@Test
	public void quandJeValideUnNamPourLesTerritoiresDuNordOuest_siLeNamEstValide_alorsJeRetourneVrai() throws UnsupportedEncodingException, ParseException {
		assertThat(serviceUtilitaireNAM.validerNAM(NAM_TERRITOIRES_DU_NORD_OUEST_VALIDE, Provinces.NT.toString())).isTrue();
	}
	
	@Test
	public void quandJeValideUnNamPourLesTerritoiresDuNordOuest_siLeNamNEstPasValide_alorsJeRetourneFaux() throws UnsupportedEncodingException, ParseException {
		assertThat(serviceUtilitaireNAM.validerNAM(NAM_TERRITOIRES_DU_NORD_OUEST_NON_VALIDE, Provinces.NT.toString())).isFalse();
	}
	
	@Test
	public void quandJeValideUnNamPourLaNouvelleEcosse_siLeNamEstValide_alorsJeRetourneVrai() throws UnsupportedEncodingException, ParseException {
		assertThat(serviceUtilitaireNAM.validerNAM(NAM_NOUVELLE_ECOSSE_VALIDE, Provinces.NS.toString())).isTrue();
	}
	
	@Test
	public void quandJeValideUnNamPourLaNouvelleEcosse_siLeNamNEstPasValide_alorsJeRetourneFaux() throws UnsupportedEncodingException, ParseException{
		assertThat(serviceUtilitaireNAM.validerNAM(NAM_NOUVELLE_ECOSSE_NON_VALIDE, Provinces.NS.toString())).isFalse();
	}
	
	@Test
	public void quandJeValideUnNamPourLeNouveauBrunswick_siLeNamEstValide_alorsJeRetourneVrai() throws UnsupportedEncodingException, ParseException {
		assertThat(serviceUtilitaireNAM.validerNAM(NAM_NOUVEAU_BRUNSWICK_VALIDE, Provinces.NB.toString())).isTrue();
	}
	
	@Test
	public void quandJeValideUnNamPourLeNouveauBrunswick_siLeNamNEstPasValide_alorsJeRetourneFaux() throws UnsupportedEncodingException, ParseException {
		assertThat(serviceUtilitaireNAM.validerNAM(NAM_NOUVEAU_BRUNSWICK_NON_VALIDE, Provinces.NB.toString())).isFalse();
	}
	
	@Test
	public void quandJeValideUnNamPourTerreNeuveEtLabrador_siLeNamEstValide_alorsJeRetourneVrai() throws UnsupportedEncodingException, ParseException {
		assertThat(serviceUtilitaireNAM.validerNAM(NAM_TERRE_NEUVE_ET_LABRADOR_VALIDE, Provinces.NL.toString())).isTrue();
	}
	
	@Test
	public void quandJeValideUnNamPourTerreNeuveEtLabrador_siLeNamNEstPasValide_alorsJeRetourneFaux() throws UnsupportedEncodingException, ParseException {
		assertThat(serviceUtilitaireNAM.validerNAM(NAM_TERRE_NEUVE_ET_LABRADOR_NON_VALIDE, Provinces.NL.toString())).isFalse();
	}
	
	@Test
	public void quandJeValideUnNamPourLeNunavut_siLeNamEstValide_alorsJeRetourneVrai() throws UnsupportedEncodingException, ParseException {
		assertThat(serviceUtilitaireNAM.validerNAM(NAM_NUNAVUT_VALIDE, Provinces.NU.toString())).isTrue();
	}
	
	@Test
	public void quandJeValideUnNamPourLeNunavut_siLeNamNEstPasValide_alorsJeRetourneFaux() throws UnsupportedEncodingException, ParseException {
		assertThat(serviceUtilitaireNAM.validerNAM(NAM_NUNAVUT_NON_VALIDE, Provinces.NU.toString())).isFalse();
	}
	
	@Test
	public void quandJeValideUnNamPourLOntario_siLeNamEstValide_alorsJeRetourneVrai() throws UnsupportedEncodingException, ParseException {
		assertThat(serviceUtilitaireNAM.validerNAM(NAM_ONTARIO_VALIDE, Provinces.ON.toString())).isTrue();
	}
	
	@Test
	public void quandJeValideUnNamPourLOntario_siLeNamNEstPasValide_alorsJeRetourneFaux() throws UnsupportedEncodingException, ParseException{
		assertThat(serviceUtilitaireNAM.validerNAM(NAM_ONTARIO_NON_VALIDE, Provinces.ON.toString())).isFalse();
	}
	
	@Test
	public void quandJeValideUnNamPourLIleDuPrinceEdouard_siLeNamEstValide_alorsJeRetourneVrai() throws UnsupportedEncodingException, ParseException {
		assertThat(serviceUtilitaireNAM.validerNAM(NAM_ILE_DU_PRINCE_EDOUARD_VALIDE, Provinces.PE.toString())).isTrue();
	}
	
	@Test
	public void quandJeValideUnNamPourLIleDuPrinceEdouard_siLeNamNEstPasValide_alorsJeRetourneFaux() throws UnsupportedEncodingException, ParseException {
		assertThat(serviceUtilitaireNAM.validerNAM(NAM_ILE_DU_PRINCE_EDOUARD_NON_VALIDE, Provinces.PE.toString())).isFalse();
	}
	
	@Test
	public void quandJeValideUnNamPourLaSaskatchewan_siLeNamEstValide_alorsJeRetourneVrai() throws UnsupportedEncodingException, ParseException {
		assertThat(serviceUtilitaireNAM.validerNAM(NAM_SASKATCHEWAN_VALIDE, Provinces.SK.toString())).isTrue();
	}
	
	@Test
	public void quandJeValideUnNamPourLaSaskatchewan_siLeNamNEstPasValide_alorsJeRetourneFaux() throws UnsupportedEncodingException, ParseException {
		assertThat(serviceUtilitaireNAM.validerNAM(NAM_SASKATCHEWAN_NON_VALIDE, Provinces.SK.toString()));
	}
	
	@Test
	public void quandJeValideUnNamPourLeYukon_siLeNamEstValide_alorsJeRetourneVrai() throws UnsupportedEncodingException, ParseException {
		assertThat(serviceUtilitaireNAM.validerNAM(NAM_YUKON_VALIDE, Provinces.YT.toString())).isTrue();
	}
	
	@Test
	public void quandJeValideUnNamPourLeYukon_siLeNamNEstPasValide_alorsJeRetourneFaux() throws UnsupportedEncodingException, ParseException {
		assertThat(serviceUtilitaireNAM.validerNAM(NAM_YUKON_NON_VALIDE, Provinces.YT.toString())).isFalse();
	}
	
	@Test
	public void quandJeValideUnNam_siLaProvinceEnvoyeeNestPasValide_alorsJeRetourneIllegalArgumentException() throws UnsupportedEncodingException, ParseException {
		exception.expect(IllegalArgumentException.class);
		serviceUtilitaireNAM.validerNAM(NAM_QUEBEC_VALIDE, PROVINCE_NON_VALIDE);
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
