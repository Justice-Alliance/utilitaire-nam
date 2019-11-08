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
import java.time.LocalDate;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import ca.qc.inspq.nam.api.modele.Personne;
import ca.qc.inspq.nam.api.modele.Provinces;
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
import ca.qc.inspq.nam.api.utilitaire.ServiceUtilitairesNAM;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceUtilitaireNAMTest {
		
	private static final String NAM_QUEBEC = "TREM04121925";
	private static final String NAM_ALBERTA = "940114192";
	private static final String NAM_COLOMBIE_BRITANNIQUE = "9759528158";
	private static final String NAM_MANITOBA = "940114192";
	private static final String NAM_TERRITOIRES_DU_NORD_OUEST = "9401141";
	private static final String NAM_NOUVELLE_ECOSSE = "9401141923";
	private static final String NAM_NOUVEAU_BRUNSWICK = "940114192";
	private static final String NAM_TERRE_NEUVE_ET_LABRADOR = "940114192698";
	private static final String NAM_NUNAVUT = "112345672";
	private static final String NAM_ONTARIO = "9401141925";
	private static final String NAM_ILE_DU_PRINCE_EDOUARD = "940114195";
	private static final String NAM_SASKATCHEWAN = "940114195";
	private static final String NAM_YUKON = "940114195";
	
	private static final String PRENOM = "Martin";
	private static final String PRENOM_ATTENDU_DANS_NAM = "M";
	private static final String NOM = "Tremblay";
	private static final String NOM_ATTENDU_DANS_NAM = "TRE";
	private static final String NOM_TROP_COURT = "Li";
	private static final String NOM_TROP_COURT_ATTENDU_DANS_NAM = "LIX";

	private static final LocalDate DATE_NAISSANCE = LocalDate.of(2004, 12, 19);
	private static final String ANNEE_ATTENDUE_DANS_NAM = "04";
	private static final String MOIS_ATTENDU_DANS_NAM = "12";
	private static final String MOIS_ATTENDU_DANS_NAM_POUR_FEMME = "62";
	private static final String JOUR_ATTENDU_DANS_NAM = "19";
	private static final LocalDate DATE_NAISSANCE_JANVIER = LocalDate.of(2004, 01, 11);
	private static final String MOIS_JANVIER_ATTENDU_DANS_NAM = "01";
	private static final LocalDate DATE_NAISSANCE_JOUR_SEPT = LocalDate.of(2004, 10, 07);
	private static final String JOUR_SEPT_ATTENDU_DANS_NAM = "07";
	
	private static final int NOMBRE_NAMS_ATTENDUS = 9;
	private static final int DEBUT_NOM_DANS_NAM = 0;
	private static final int FIN_NOM_DANS_NAM = 3;
	private static final int DEBUT_PRENOM_DANS_NAM = 3;
	private static final int FIN_PRENOM_DANS_NAM = 4;
	private static final int DEBUT_ANNEE_NAISSANCE_DANS_NAM = 4;
	private static final int FIN_ANNEE_NAISSANCE_DANS_NAM = 6;
	private static final int DEBUT_MOIS_NAISSANCE_DANS_NAM = 6;
	private static final int FIN_MOIS_NAISSANCE_DANS_NAM = 8;
	private static final int DEBUT_JOUR_NAISSANCE_DANS_NAM = 8;
	private static final int FIN_JOUR_NAISSANCE_DANS_NAM = 10;
	private static final int DEBUT_CARACTERE_DISTINCTION_DANS_NAM = 10;
	private static final int FIN_CARACTERE_DISTINCTION_DANS_NAM = 11;
	private static final int DEBUT_CARACTERE_VALIDATEUR_DANS_NAM = 11;
	
	private static final String PROVINCE_NON_VALIDE = "TT";
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@SpyBean
	private ServiceUtilitairesNAM serviceUtilitaireNAM;
	
	@MockBean
	private NumeroAssuranceMaladieQuebecValideSpecification numeroAssuranceMaladieQuebecValideSpecification;
	
	@MockBean
	private NumeroAssuranceMaladieAlbertaValideSpecification numeroAssuranceMaladieAlbertaValideSpecification;
	
	@MockBean
	private NumeroAssuranceMaladieColombieBritanniqueValideSpecification numeroAssuranceMaladieColombieBritanniqueValideSpecification;
	
	@MockBean
	private NumeroAssuranceMaladieManitobaValideSpecification numeroAssuranceMaladieManitobaValideSpecification;
	
	@MockBean
	private NumeroAssuranceMaladieTerritoiresDuNordOuestValideSpecification numeroAssuranceMaladieTerritoiresDuNordOuestValideSpecification;
	
	@MockBean
	private NumeroAssuranceMaladieNouvelleEcosseValideSpecification numeroAssuranceMaladieNouvelleEcosseValideSpecification;
	
	@MockBean
	private NumeroAssuranceMaladieNouveauBrunswickValideSpecification numeroAssuranceMaladieNouveauBrunswickValideSpecification;
	
	@MockBean
	private NumeroAssuranceMaladieTerreNeuveEtLabradorValideSpecification numeroAssuranceMaladieTerreNeuveEtLabradorValideSpecification;
	
	@MockBean
	private NumeroAssuranceMaladieNunavutValideSpecification numeroAssuranceMaladieNunavutValideSpecification;
	
	@MockBean
	private NumeroAssuranceMaladieOntarioValideSpecification numeroAssuranceMaladieOntarioValideSpecification;
	
	@MockBean
	private NumeroAssuranceMaladieIleDuPrinceEdouardValideSpecification numeroAssuranceMaladieIleDuPrinceEdouardValideSpecification;
	
	@MockBean
	private NumeroAssuranceMaladieSaskatchewanValideSpecification numeroAssuranceMaladieSaskatchewanValideSpecification;
	
	@MockBean
	private NumeroAssuranceMaladieYukonValideSpecification numeroAssuranceMaladieYukonValideSpecification;
	
	@SpyBean
	private PersonneGenerationNAMValideSpecification personneGenerationNAMValideSpecification;
	
	@Test
	public void quandJeValideUnNamPourleQuebec_alorsJappelleLaSpecificationPourNamDuQuebecValide() throws UnsupportedEncodingException, ParseException {
		serviceUtilitaireNAM.validerNAM(NAM_QUEBEC, Provinces.QC.toString());
		verify(numeroAssuranceMaladieQuebecValideSpecification, times(1)).estSatisfaitePar(eq(NAM_QUEBEC));
	}
	
	@Test
	public void quandJeValideUnNamPourLeQuebec_alorsJeRetourneSonEtatDeValidite() throws UnsupportedEncodingException, ParseException {
		when(numeroAssuranceMaladieQuebecValideSpecification.estSatisfaitePar(eq(NAM_QUEBEC))).thenReturn(true);
		var resultat = serviceUtilitaireNAM.validerNAM(NAM_QUEBEC, Provinces.QC.toString());
		assertThat(resultat).isTrue();
	}
	
	@Test
	public void quandJeValideUnNamPourAlberta_alorsJappelleLaSpecificationPourNamDeAlbertaValide() throws UnsupportedEncodingException, ParseException {
		serviceUtilitaireNAM.validerNAM(NAM_ALBERTA, Provinces.AB.toString());
		verify(numeroAssuranceMaladieAlbertaValideSpecification, times(1)).estSatisfaitePar(eq(NAM_ALBERTA));
	}
	
	@Test
	public void quandJeValideUnNamPourAlberta_alorsJeRetourneSonEtatDeValidite() throws UnsupportedEncodingException, ParseException {
		when(numeroAssuranceMaladieAlbertaValideSpecification.estSatisfaitePar(eq(NAM_ALBERTA))).thenReturn(true);
		var resultat = serviceUtilitaireNAM.validerNAM(NAM_ALBERTA, Provinces.AB.toString());
		assertThat(resultat).isTrue();
	}
	
	@Test
	public void quandJeValideUnNamPourColombieBritannique_alorsJappelleLaSpecificationPourNamdeColombieBritanniqueValide() throws UnsupportedEncodingException, ParseException {
		serviceUtilitaireNAM.validerNAM(NAM_COLOMBIE_BRITANNIQUE, Provinces.BC.toString());
		verify(numeroAssuranceMaladieColombieBritanniqueValideSpecification, times(1)).estSatisfaitePar(eq(NAM_COLOMBIE_BRITANNIQUE));
	}
	
	@Test
	public void quandJeValideUnNamPourColombieBritannique_alorsJeRetourneSonEtatDeValidite() throws UnsupportedEncodingException, ParseException {
		when(numeroAssuranceMaladieColombieBritanniqueValideSpecification.estSatisfaitePar(eq(NAM_COLOMBIE_BRITANNIQUE))).thenReturn(true);
		var resultat = serviceUtilitaireNAM.validerNAM(NAM_COLOMBIE_BRITANNIQUE, Provinces.BC.toString());
		assertThat(resultat).isTrue();
	}
	
	@Test
	public void quandJeValideUnNamPourManitoba_alorsJApelleLaSpecificationPourNamDeManitobaValide() throws UnsupportedEncodingException, ParseException {
		serviceUtilitaireNAM.validerNAM(NAM_MANITOBA, Provinces.MB.toString());
		verify(numeroAssuranceMaladieManitobaValideSpecification, times(1)).estSatisfaitePar(eq(NAM_MANITOBA));
	}
	
	@Test
	public void quandJeValideUnNamPourManitoba_alorsJeRetourneSonEtatDeValidite() throws UnsupportedEncodingException, ParseException {
		when(numeroAssuranceMaladieManitobaValideSpecification.estSatisfaitePar(eq(NAM_MANITOBA))).thenReturn(true);
		var resultat = serviceUtilitaireNAM.validerNAM(NAM_MANITOBA, Provinces.MB.toString());
		assertThat(resultat).isTrue();
	}
	
	@Test
	public void quandJeValideUnNamPourTerritoiresDuNordOuest_alorsJapelleLaSpecificationPourNamDeTerritoiresDuNordOuest() throws UnsupportedEncodingException, ParseException {
		serviceUtilitaireNAM.validerNAM(NAM_TERRITOIRES_DU_NORD_OUEST, Provinces.NT.toString());
		verify(numeroAssuranceMaladieTerritoiresDuNordOuestValideSpecification, times(1)).estSatisfaitePar(NAM_TERRITOIRES_DU_NORD_OUEST);
	}
	
	@Test
	public void quandJeValideUnNamPourTerritoiresDuNordOuest_alorsJeRetourneSonEtatDeValidite() throws UnsupportedEncodingException, ParseException {
		when(numeroAssuranceMaladieTerritoiresDuNordOuestValideSpecification.estSatisfaitePar(NAM_TERRITOIRES_DU_NORD_OUEST)).thenReturn(true);
		var resultat = serviceUtilitaireNAM.validerNAM(NAM_TERRITOIRES_DU_NORD_OUEST, Provinces.NT.toString());
		assertThat(resultat).isTrue();
	}
	
	@Test
	public void quandJeValideUnNamPourNouvelleEcosse_alorsJapelleLaSpecificationPourNamDeNouvelleEcosse() throws UnsupportedEncodingException, ParseException {
		serviceUtilitaireNAM.validerNAM(NAM_NOUVELLE_ECOSSE, Provinces.NS.toString());
		verify(numeroAssuranceMaladieNouvelleEcosseValideSpecification, times(1)).estSatisfaitePar(NAM_NOUVELLE_ECOSSE);
	}
	
	@Test
	public void quandJeValideUnNamPourNouvelleEcosse_alorsJeRetournesSonEtatDeValidite() throws UnsupportedEncodingException, ParseException {
		when(numeroAssuranceMaladieNouvelleEcosseValideSpecification.estSatisfaitePar(NAM_NOUVELLE_ECOSSE)).thenReturn(true);
		var resultat = serviceUtilitaireNAM.validerNAM(NAM_NOUVELLE_ECOSSE, Provinces.NS.toString());
		assertThat(resultat).isTrue();
	}
	
	@Test
	public void quandJeValideUnNamPourNouveauBrunswick_alorsJapelleLaSpecificationPourNamdeNouveauBrunswick() throws UnsupportedEncodingException, ParseException {
		serviceUtilitaireNAM.validerNAM(NAM_NOUVEAU_BRUNSWICK, Provinces.NB.toString());
		verify(numeroAssuranceMaladieNouveauBrunswickValideSpecification, times(1)).estSatisfaitePar(NAM_NOUVEAU_BRUNSWICK);
	}
	
	@Test
	public void quandJeValideUnNamPourNouveauBrunswick_alorsJeRetourneSonEtatDeValidite() throws UnsupportedEncodingException, ParseException {
		when(numeroAssuranceMaladieNouveauBrunswickValideSpecification.estSatisfaitePar(NAM_NOUVEAU_BRUNSWICK)).thenReturn(true);
		var resultat = serviceUtilitaireNAM.validerNAM(NAM_NOUVEAU_BRUNSWICK, Provinces.NB.toString());
		assertThat(resultat).isTrue();
	}
	
	@Test
	public void quandJeValideUnNamPourTerreNeuveEtLabrador_alorsJapelleLaSpecificationPourNamdeTerreNeuveEtLabrador() throws UnsupportedEncodingException, ParseException {
		serviceUtilitaireNAM.validerNAM(NAM_TERRE_NEUVE_ET_LABRADOR, Provinces.NL.toString());
		verify(numeroAssuranceMaladieTerreNeuveEtLabradorValideSpecification, times(1)).estSatisfaitePar(NAM_TERRE_NEUVE_ET_LABRADOR);
	}
	
	@Test
	public void quandJeValideUnNamPourTerreNeuveEtLabrador_alorsJeRetourneSonEtatDeValidite() throws UnsupportedEncodingException, ParseException {
		when(numeroAssuranceMaladieTerreNeuveEtLabradorValideSpecification.estSatisfaitePar(NAM_TERRE_NEUVE_ET_LABRADOR)).thenReturn(true);
		var resultat = serviceUtilitaireNAM.validerNAM(NAM_TERRE_NEUVE_ET_LABRADOR, Provinces.NL.toString());
		assertThat(resultat).isTrue();
	}
	
	@Test
	public void quandJeValideUnNamPourLeNunavut_alorsJapelleLaSpecificationPourNamDuNunavut() throws UnsupportedEncodingException, ParseException {
		serviceUtilitaireNAM.validerNAM(NAM_NUNAVUT, Provinces.NU.toString());
		verify(numeroAssuranceMaladieNunavutValideSpecification, times(1)).estSatisfaitePar(NAM_NUNAVUT);
	}
	
	@Test
	public void quandJeValideUnNamPourNunavut_alorsJeRetourneSonEtatDeValidite() throws UnsupportedEncodingException, ParseException {
		when(numeroAssuranceMaladieNunavutValideSpecification.estSatisfaitePar(NAM_NUNAVUT)).thenReturn(true);
		var resultat = serviceUtilitaireNAM.validerNAM(NAM_NUNAVUT, Provinces.NU.toString());
		assertThat(resultat).isTrue();
	}
	
	@Test
	public void quandJeValideUnNamPourLOntario_alorsJapelleLaSpecificationPourNamDeLOntario() throws UnsupportedEncodingException, ParseException {
		serviceUtilitaireNAM.validerNAM(NAM_ONTARIO, Provinces.ON.toString());
		verify(numeroAssuranceMaladieOntarioValideSpecification, times(1)).estSatisfaitePar(NAM_ONTARIO);
	}
	
	@Test
	public void quandJeValideUnNamPourOntario_alorsJeRetourneSonEtatDeValidite() throws UnsupportedEncodingException, ParseException {
		when(numeroAssuranceMaladieOntarioValideSpecification.estSatisfaitePar(NAM_ONTARIO)).thenReturn(true);
		var resultat = serviceUtilitaireNAM.validerNAM(NAM_ONTARIO, Provinces.ON.toString());
		assertThat(resultat).isTrue();
	}
	
	@Test
	public void quandJeValideUnNamPourLIleDuPrinceEdouard_alorsJapelleLaSpecificationPourNamDeLIleDuPrinceEdouard() throws UnsupportedEncodingException, ParseException {
		serviceUtilitaireNAM.validerNAM(NAM_ILE_DU_PRINCE_EDOUARD, Provinces.PE.toString());
		verify(numeroAssuranceMaladieIleDuPrinceEdouardValideSpecification, times(1)).estSatisfaitePar(NAM_ILE_DU_PRINCE_EDOUARD);
	}	
	
	@Test
	public void quandJeValideUnNamPourIleDuPrinceEdouard_alorsJeRetourneSonEtatDeValidite() throws UnsupportedEncodingException, ParseException {
		when(numeroAssuranceMaladieIleDuPrinceEdouardValideSpecification.estSatisfaitePar(NAM_ILE_DU_PRINCE_EDOUARD)).thenReturn(true);
		var resultat = serviceUtilitaireNAM.validerNAM(NAM_ILE_DU_PRINCE_EDOUARD, Provinces.PE.toString());
		assertThat(resultat).isTrue();
	}
	
	@Test
	public void quandJeValideUnNamPourLaSaskatchewan_alorsJapelleLaSpecificationPourNamDeLaSaskatchewan() throws UnsupportedEncodingException, ParseException {
		serviceUtilitaireNAM.validerNAM(NAM_SASKATCHEWAN, Provinces.SK.toString());
		verify(numeroAssuranceMaladieSaskatchewanValideSpecification, times(1)).estSatisfaitePar(NAM_SASKATCHEWAN);
	}		
	
	@Test
	public void quandJeValideUnNamPourSaskatchewan_alorsJeRetourneSonEtatDeValidite() throws UnsupportedEncodingException, ParseException {
		when(numeroAssuranceMaladieSaskatchewanValideSpecification.estSatisfaitePar(NAM_SASKATCHEWAN)).thenReturn(true);
		var resultat = serviceUtilitaireNAM.validerNAM(NAM_SASKATCHEWAN, Provinces.SK.toString());
		assertThat(resultat).isTrue();
	}
	
	@Test
	public void quandJeValideUnNamPourLeYukon_alorsJapelleLaSpecificationPourNamDeLeYukon() throws UnsupportedEncodingException, ParseException {
		serviceUtilitaireNAM.validerNAM(NAM_YUKON, Provinces.YT.toString());
		verify(numeroAssuranceMaladieYukonValideSpecification, times(1)).estSatisfaitePar(NAM_YUKON);
	}	
	
	@Test
	public void quandJeValideUnNamPourYukon_alorsJeRetourneSonEtatDeValidite() throws UnsupportedEncodingException, ParseException {
		when(numeroAssuranceMaladieYukonValideSpecification.estSatisfaitePar(NAM_YUKON)).thenReturn(true);
		var resultat = serviceUtilitaireNAM.validerNAM(NAM_YUKON, Provinces.YT.toString());
		assertThat(resultat).isTrue();
	}
	
	@Test
	public void quandJeValideUnNam_siLaProvinceEnvoyeeNestPasValide_alorsJeRetourneIllegalArgumentException() throws UnsupportedEncodingException, ParseException {
		exception.expect(IllegalArgumentException.class);
		serviceUtilitaireNAM.validerNAM(NAM_QUEBEC, PROVINCE_NON_VALIDE);
	}
	
	@Test
	public void quandJeDemandeDobtenirTousLesNAMSPossiblesPourUnePersonne_alorsJappelleLaSpecificationPourPersonneDeGenerationDeNAM() throws UnsupportedEncodingException, ParseException {
		var personne = new Personne(PRENOM, NOM, DATE_NAISSANCE, Sexe.MASCULIN);
		serviceUtilitaireNAM.obtenirCombinaisonsValidesDeNAM(personne);
		verify(personneGenerationNAMValideSpecification, times(1)).estSatisfaitePar(personne);
	}
	
	@Test
	public void quandJeDemandDobtenirTousLesNAMSPossiblesPourUnePersonne_siLaPersonneNestPasValide_alorsJeRetourneInvalidParameterException() throws UnsupportedEncodingException, ParseException  {
		exception.expect(InvalidParameterException.class);
		var personne = new Personne(null, null, null, null);
		serviceUtilitaireNAM.obtenirCombinaisonsValidesDeNAM(personne);
	}
	
	@Test
	public void quandJeDemandeDobtenirTousLesNAMSPossiblesPourUnePersonne_alorsTousLesNAMSCommencentParLes3PremieresLettresDeSonNomEnMajuscules() throws UnsupportedEncodingException, ParseException {
		var personne = new Personne(PRENOM, NOM, DATE_NAISSANCE, Sexe.MASCULIN);
		var resultat = serviceUtilitaireNAM.obtenirCombinaisonsValidesDeNAM(personne);
		assertThat(resultat).isNotNull().hasSize(NOMBRE_NAMS_ATTENDUS)
				.extracting(nam -> nam.substring(DEBUT_NOM_DANS_NAM, FIN_NOM_DANS_NAM))
				.containsOnly(NOM_ATTENDU_DANS_NAM);
	}
	
	@Test
	public void quandJeDemandeDobtenirTousLesNAMSPossiblesPourUnePersonneDontLeNomADeuxCaracteresOuMoins_alorsLesTroisPremiersCaracteresDeTousLesNAMSSontLeNomSuiviDeX() throws UnsupportedEncodingException, ParseException {
		var personne = new Personne(PRENOM, NOM_TROP_COURT, DATE_NAISSANCE, Sexe.MASCULIN);
		var resultat = serviceUtilitaireNAM.obtenirCombinaisonsValidesDeNAM(personne);
		assertThat(resultat).isNotNull().hasSize(NOMBRE_NAMS_ATTENDUS).allMatch(nam -> nam.startsWith(NOM_TROP_COURT_ATTENDU_DANS_NAM));
	}
	
	@Test
	public void quandJeDemandeDobtenirTousLesNAMSPossiblesPourUnePersonneDontLeNomADeuxCaracteresOuMoins_alorsLeQuatriemeCaractereDeTousLesNAMSEstLaPremiereLettreDuPrenom() throws UnsupportedEncodingException, ParseException {
		var personne = new Personne(PRENOM, NOM, DATE_NAISSANCE, Sexe.MASCULIN);
		var resultat = serviceUtilitaireNAM.obtenirCombinaisonsValidesDeNAM(personne);
		assertThat(resultat).isNotNull().hasSize(NOMBRE_NAMS_ATTENDUS)
		.extracting(nam -> nam.substring(DEBUT_PRENOM_DANS_NAM, FIN_PRENOM_DANS_NAM))
		.containsOnly(PRENOM_ATTENDU_DANS_NAM);
	}
	
	@Test
	public void quandJeDemandeDobtenirTousLesNAMSPossiblesPourUnePersonne_alorsLesCaracteres5Et6SontLesDeuxDerniersChiffresDeLanneeDeNaissancePourTousLesNAMS () throws UnsupportedEncodingException, ParseException {
		var personne = new Personne(PRENOM, NOM, DATE_NAISSANCE, Sexe.MASCULIN);
		var resultat = serviceUtilitaireNAM.obtenirCombinaisonsValidesDeNAM(personne);
		assertThat(resultat).isNotNull().hasSize(NOMBRE_NAMS_ATTENDUS)
		.extracting(nam -> nam.substring(DEBUT_ANNEE_NAISSANCE_DANS_NAM, FIN_ANNEE_NAISSANCE_DANS_NAM))
		.containsOnly(ANNEE_ATTENDUE_DANS_NAM);
	}
	
	@Test
	public void quandJeDemandeDobtenirTousLesNAMSPossiblesPourUnePersonneDontLeMoisDeNaissanceEstPlusPetitQueDix_alorsLesCaracteres7Et8SontZeroEtLeMoisDeNaissancePourTousLesNAMS() throws UnsupportedEncodingException, ParseException {
		var personne = new Personne(PRENOM, NOM, DATE_NAISSANCE_JANVIER, Sexe.MASCULIN);
		var resultat = serviceUtilitaireNAM.obtenirCombinaisonsValidesDeNAM(personne);
		assertThat(resultat).isNotNull().hasSize(NOMBRE_NAMS_ATTENDUS)
		.extracting(nam -> nam.substring(DEBUT_MOIS_NAISSANCE_DANS_NAM, FIN_MOIS_NAISSANCE_DANS_NAM))
		.containsOnly(MOIS_JANVIER_ATTENDU_DANS_NAM);
	}
	
	@Test
	public void quandJeDemandeDobtenirTousLesNAMSPossiblesPourUnePersonneDontLeMoisDeNaissanceEstPlusGrandQueNeuf_alorsLesCaracteres7Et8SontLeMoisDeNaissancePourTousLesNAMS() throws UnsupportedEncodingException, ParseException {
		var personne = new Personne(PRENOM, NOM, DATE_NAISSANCE, Sexe.MASCULIN);
		var resultat = serviceUtilitaireNAM.obtenirCombinaisonsValidesDeNAM(personne);
		assertThat(resultat).isNotNull().hasSize(NOMBRE_NAMS_ATTENDUS)
		.extracting(nam -> nam.substring(DEBUT_MOIS_NAISSANCE_DANS_NAM, FIN_MOIS_NAISSANCE_DANS_NAM))
		.containsOnly(MOIS_ATTENDU_DANS_NAM);
	}
	
	@Test
	public void quandJeDemandeDobtenirTousLesNAMSPossiblesPourUnePersonneDontLeSexeEstFeminin_alorsLesCaracteres7Et8SontLeMoisdeNaissancePlus50PourTousLesNAMS() throws UnsupportedEncodingException, ParseException {
		var personne = new Personne(PRENOM, NOM, DATE_NAISSANCE, Sexe.FEMININ);
		var resultat = serviceUtilitaireNAM.obtenirCombinaisonsValidesDeNAM(personne);
		assertThat(resultat).isNotNull().hasSize(NOMBRE_NAMS_ATTENDUS)
		.extracting(nam -> nam.substring(DEBUT_MOIS_NAISSANCE_DANS_NAM, FIN_MOIS_NAISSANCE_DANS_NAM))
		.containsOnly(MOIS_ATTENDU_DANS_NAM_POUR_FEMME);
	}
	
	@Test
	public void quandJeDemandeDobtenirTousLesNAMSPossiblesPourUnePersonneDontLeJourDeNaissanceEstPlusPetitQueDix_alorsLesCaracteres9Et10SontZeroEtLeJourDeNaissancePourTousLesNAMS() throws UnsupportedEncodingException, ParseException {
		var personne = new Personne(PRENOM, NOM, DATE_NAISSANCE_JOUR_SEPT, Sexe.MASCULIN);
		var resultat = serviceUtilitaireNAM.obtenirCombinaisonsValidesDeNAM(personne);
		assertThat(resultat).isNotNull().hasSize(NOMBRE_NAMS_ATTENDUS)
		.extracting(nam -> nam.substring(DEBUT_JOUR_NAISSANCE_DANS_NAM, FIN_JOUR_NAISSANCE_DANS_NAM))
		.containsOnly(JOUR_SEPT_ATTENDU_DANS_NAM);	
	}
	
	@Test
	public void quandJeDemandeDobtenirTousLesNAMSPossiblesPourUnePersonneDontLeJourDeNaissanceEstPlusGrandQueNeuf_alorsLesCaracteres9Et10SontLeJourDeNaissancePourTousLesNAMS() throws UnsupportedEncodingException, ParseException {
		var personne = new Personne(PRENOM, NOM, DATE_NAISSANCE, Sexe.MASCULIN);
		var resultat = serviceUtilitaireNAM.obtenirCombinaisonsValidesDeNAM(personne);
		assertThat(resultat).isNotNull().hasSize(NOMBRE_NAMS_ATTENDUS)
		.extracting(nam -> nam.substring(DEBUT_JOUR_NAISSANCE_DANS_NAM, FIN_JOUR_NAISSANCE_DANS_NAM))
		.containsOnly(JOUR_ATTENDU_DANS_NAM);
	}
	
	@Test
	public void quandJeDemandeDobtenirTousLesNAMSPossiblesPourUnePersonne_alorsLeCaractere11EstComprisEntre1Et9() throws UnsupportedEncodingException, ParseException {
		var personne = new Personne(PRENOM, NOM, DATE_NAISSANCE, Sexe.MASCULIN);
		var resultat = serviceUtilitaireNAM.obtenirCombinaisonsValidesDeNAM(personne);
		assertThat(resultat).isNotNull().hasSize(NOMBRE_NAMS_ATTENDUS)
		.extracting(nam -> nam.substring(DEBUT_CARACTERE_DISTINCTION_DANS_NAM, FIN_CARACTERE_DISTINCTION_DANS_NAM))
		.containsExactly("1", "2", "3", "4", "5", "6", "7", "8", "9");
	}
	
	@Test
	public void quandJeDemandeDobtenirTousLesNAMSPossiblesPourUnePersonne_alorsLeCaractere12estLeChiffreValidateur() throws UnsupportedEncodingException, ParseException {
		var personne = new Personne(PRENOM, NOM, DATE_NAISSANCE, Sexe.MASCULIN);
		var resultat = serviceUtilitaireNAM.obtenirCombinaisonsValidesDeNAM(personne);
		assertThat(resultat).isNotNull().hasSize(NOMBRE_NAMS_ATTENDUS)
		.extracting(x -> x.substring(DEBUT_CARACTERE_VALIDATEUR_DANS_NAM))
		.containsExactly("4", "5", "6", "7", "8", "9", "0", "1", "2");
	}
	
	@Test
	public void quandJeDemandeDobtenirTousLesNAMSPossiblesPourUnePersonne_alorsOnRetourneTousLesNAMSPossiblesPourCettePersonne() throws UnsupportedEncodingException, ParseException {
		var personne = new Personne(PRENOM, NOM, DATE_NAISSANCE, Sexe.MASCULIN);
		var resultat = serviceUtilitaireNAM.obtenirCombinaisonsValidesDeNAM(personne);
		assertThat(resultat).isNotNull().hasSize(NOMBRE_NAMS_ATTENDUS).containsExactly("TREM04121914", 
				"TREM04121925", 
				"TREM04121936", 
				"TREM04121947", 
				"TREM04121958", 
				"TREM04121969", 
				"TREM04121970", 
				"TREM04121981", 
				"TREM04121992");
	}
	
	// ------------ obtenir informations contenues dans nam --------------
	
	// quand on obtient le sexe, alors le nam est valider avec le validateur QC
	
	// quand on obtient le sexe, si le mois dans le nam est > 50, alors on retourne feminin
	
	// quand on obtient le sexe, si le mois dans le nam est < 50, alors on retourne masculin
	@Test
	public void quandJeDemandeDobtenirLeSexeContenuDansUnNAM_siLeNamEstUnNamDhomme_alorsJeRetourneMasculin()
			throws UnsupportedEncodingException, ParseException {
		when(numeroAssuranceMaladieQuebecValideSpecification.estSatisfaitePar(eq(NAM_QUEBEC))).thenReturn(true);
		var sexeObtenu = serviceUtilitaireNAM.obtenirSexe(NAM_QUEBEC);
		assertThat(sexeObtenu).isEqualTo(Sexe.MASCULIN);
	}
	
	// quand on obtient le sexe, si le nam n'est pas valide, alors on lance InvalidParameterException
	@Test
	public void quandJeDemandeDobtenirLeSexeContenuDansUnNam_siLeNamNestPasValide_alorsInvalidParameterExceptionEstLancee()
			throws UnsupportedEncodingException, ParseException {
		exception.expect(InvalidParameterException.class);
		when(numeroAssuranceMaladieQuebecValideSpecification.estSatisfaitePar(eq(NAM_QUEBEC))).thenReturn(false);
		serviceUtilitaireNAM.obtenirSexe(NAM_QUEBEC);
	}
	
	// TODO Fred: fait le même exercice pour la date de naissance
	
	// -------------------------------------------------------------------

	

	

	

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
}
