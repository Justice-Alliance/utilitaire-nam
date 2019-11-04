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
import java.util.regex.Pattern;

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
import ca.qc.inspq.nam.api.utilitaire.ServiceUtilitairesNAM;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceUtilitaireNAMTest {
	
	private static final SimpleDateFormat FORMATTEUR_DATE = new SimpleDateFormat("yyyy-MM-dd");
	
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
	
	private static final String AUCUN_PRENOM = null;
	private static final String PRENOM = "Martin";
	private static final String PRENOM_NON_NORMALISE = "Étienne";
	private static final String NOM = "Tremblay";
	private static final String NOM_PAS_NORMALISE = "L'Heureux";
	private static final String NOM_TROP_COURT = "Li";
	private static final String SEXE = "M";
	private static final String SEXE_FEMININ = "F";
	private static final String DATE_NAISSANCE = "2004-12-19";
	private static final String DATE_NAISSANCE_JANVIER = "2004-01-11";
	private static final String DATE_NAISSANCE_JOUR_SEPT = "2004-10-07";
	
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
	
	// --------------- Obtenir une liste de NAM -------------------------
	
	// ça prend les 4 entrants, sinon InvalidParameterException (ne pas utiliser cette exception...)
	@Test
	public void quandJeDemandeDobtenirTousLesNAMSPossiblesPourUnePersonne_siLesParametresNeSontPasTousFournis_alorsInvalidParameterException()
			throws UnsupportedEncodingException, ParseException {
		exception.expect(InvalidParameterException.class);
		serviceUtilitaireNAM.obtenirCombinaisonsValidesDeNAM(AUCUN_PRENOM, NOM, FORMATTEUR_DATE.parse(DATE_NAISSANCE), SEXE);
	}
	
	// nom normalisé RAMQ dans le NAM
	// TODO est-ce que la normalisation devrait vraiment se trouver ici?
	@Test
	public void quandJeDemandeDobtenirTousLesNAMSPossiblesPourUnePersonne_alorsLeNomEstNormaliseDansLesNAMS() throws UnsupportedEncodingException, ParseException {
		var resultat = serviceUtilitaireNAM.obtenirCombinaisonsValidesDeNAM(PRENOM, NOM_PAS_NORMALISE, FORMATTEUR_DATE.parse(DATE_NAISSANCE), SEXE);
		assertThat(resultat).isNotNull().hasSize(9).noneMatch(nam -> nam.startsWith(NOM_PAS_NORMALISE.toUpperCase().substring(0, 3)));
	}
	
	// prénom normalisé RAMQ dans le NAM
	@Test
	public void quandJeDemandeDobtenirTousLesNAMSPossiblesPourUnePersonne_AlorsLePrenomEstNormaliseDansLesNAMS() throws UnsupportedEncodingException, ParseException {
		var resultat = serviceUtilitaireNAM.obtenirCombinaisonsValidesDeNAM(PRENOM_NON_NORMALISE, NOM, FORMATTEUR_DATE.parse(DATE_NAISSANCE), SEXE);
		assertThat(resultat).isNotNull().hasSize(9).noneMatch(nam -> nam.startsWith(PRENOM_NON_NORMALISE.toUpperCase().substring(0, 1), 3));
	}
	
	// le nam contient les 3 premieres lettres du nom de famille à ses 3 premiers caracteres
	@Test
	public void quandJeDemandeDobtenirTousLesNAMSPossiblesPourUnePersonne_alorsTousLesNAMSCommencentParLes3PremieresLettresDeSonNomEnMajuscules() throws UnsupportedEncodingException, ParseException {
		var resultat = serviceUtilitaireNAM.obtenirCombinaisonsValidesDeNAM(PRENOM, NOM, FORMATTEUR_DATE.parse(DATE_NAISSANCE), SEXE);
		assertThat(resultat).isNotNull().hasSize(9).allMatch(nam -> nam.startsWith(NOM.toUpperCase().substring(0, 3)));
	}
	
	// si nom plus petit que 3 caractères, on pad right avec des X
	@Test
	public void quandJeDemandeDobtenirTousLesNAMSPossiblesPourUnePersonneDontLeNomADeuxCaracteresOuMoins_alorsLesTroisPremiersCaracteresDeTousLesNAMSSontLeNomSuiviDeX() throws UnsupportedEncodingException, ParseException {
		var resultat = serviceUtilitaireNAM.obtenirCombinaisonsValidesDeNAM(PRENOM, NOM_TROP_COURT, FORMATTEUR_DATE.parse(DATE_NAISSANCE), SEXE);
		assertThat(resultat).isNotNull().hasSize(9).allMatch(nam -> nam.startsWith(NOM_TROP_COURT.toUpperCase() + "X"));
	}
	
	// le nam contient la première lettre du prénom à son 4e caracteres
	@Test
	public void quandJeDemandeDobtenirTousLesNAMSPossiblesPourUnePersonneDontLeNomADeuxCaracteresOuMoins_alorsLeQuatriemeCaractereDeTousLesNAMSEstLaPremiereLettreDuPrenom() throws UnsupportedEncodingException, ParseException {
		var resultat = serviceUtilitaireNAM.obtenirCombinaisonsValidesDeNAM(PRENOM, NOM, FORMATTEUR_DATE.parse(DATE_NAISSANCE), SEXE);
		assertThat(resultat).isNotNull().hasSize(9).allMatch(nam -> nam.startsWith(PRENOM.toUpperCase().substring(0, 1), 3));
	}
	// le nam contient les deux derniers chiffres de l'année à ses caractères 5 et 6
	@Test
	public void quandJeDemandeDobtenirTousLesNAMSPossiblesPourUnePersonne_alorsLesCaracteres5Et6SontLesDeuxDerniersChiffresDeLanneeDeNaissancePourTousLesNAMS () throws UnsupportedEncodingException, ParseException {
		var resultat = serviceUtilitaireNAM.obtenirCombinaisonsValidesDeNAM(PRENOM, NOM, FORMATTEUR_DATE.parse(DATE_NAISSANCE), SEXE);
		assertThat(resultat).isNotNull().hasSize(9).allMatch(nam -> nam.startsWith(DATE_NAISSANCE.substring(2, 4), 4));
	}
	
	// si le mois est < 10, le nam contient le mois pad de 0 à gauche à ses caractères 7 et 8
	@Test
	public void quandJeDemandeDobtenirTousLesNAMSPossiblesPourUnePersonneDontLeMoisDeNaissanceEstPlusPetitQueDix_alorsLesCaracteres7Et8SontZeroEtLeMoisDeNaissancePourTousLesNAMS() throws UnsupportedEncodingException, ParseException {
		var resultat = serviceUtilitaireNAM.obtenirCombinaisonsValidesDeNAM(PRENOM, NOM, FORMATTEUR_DATE.parse(DATE_NAISSANCE_JANVIER), SEXE);
		assertThat(resultat).isNotNull().hasSize(9).allMatch(nam -> nam.startsWith(DATE_NAISSANCE_JANVIER.substring(5, 7), 6));
	}
	
	// si le mois est >= 10, le nam contient le mois à ses caractères 7 et 8
	@Test
	public void quandJeDemandeDobtenirTousLesNAMSPossiblesPourUnePersonneDontLeMoisDeNaissanceEstPlusGrandQueNeuf_alorsLesCaracteres7Et8SontLeMoisDeNaissancePourTousLesNAMS() throws UnsupportedEncodingException, ParseException {
		var resultat = serviceUtilitaireNAM.obtenirCombinaisonsValidesDeNAM(PRENOM, NOM, FORMATTEUR_DATE.parse(DATE_NAISSANCE), SEXE);
		assertThat(resultat).isNotNull().hasSize(9).allMatch(nam -> nam.startsWith(DATE_NAISSANCE.substring(5, 7), 6));
	}
	
	// si le sexe est féminin, le mois dans le nam est le mois de naissance + 50
	@Test
	public void quandJeDemandeDobtenirTousLesNAMSPossiblesPourUnePersonneDontLeSexeEstFeminin_alorsLesCaracteres7Et8SontLeMoisdeNaissancePlus50PourTousLesNAMS() throws UnsupportedEncodingException, ParseException {
		var resultat = serviceUtilitaireNAM.obtenirCombinaisonsValidesDeNAM(PRENOM, NOM, FORMATTEUR_DATE.parse(DATE_NAISSANCE), SEXE_FEMININ);
		assertThat(resultat).isNotNull().hasSize(9).allMatch(nam -> nam.startsWith(Integer.toString(Integer.parseInt(DATE_NAISSANCE.substring(5, 7)) + 50), 6));
	}
	
	// si le jour est < 10, le nam contient le jour pad de 0 à gauche à ses caractères 9 et 10
	@Test
	public void quandJeDemandeDobtenirTousLesNAMSPossiblesPourUnePersonneDontLeJourDeNaissanceEstPlusPetitQueDix_alorsLesCaracteres9Et10SontZeroEtLeJourDeNaissancePourTousLesNAMS() throws UnsupportedEncodingException, ParseException {
		var resultat = serviceUtilitaireNAM.obtenirCombinaisonsValidesDeNAM(PRENOM, NOM, FORMATTEUR_DATE.parse(DATE_NAISSANCE_JOUR_SEPT), SEXE);
		assertThat(resultat).isNotNull().hasSize(9).allMatch(nam -> nam.startsWith(DATE_NAISSANCE_JOUR_SEPT.substring(8), 8));	
	}
	
	// si le jour est >= 10, le nam contient le jour à ses caractères 9 et 10
	@Test
	public void quandJeDemandeDobtenirTousLesNAMSPossiblesPourUnePersonneDontLeJourDeNaissanceEstPlusGrandQueNeuf_alorsLesCaracteres9Et10SontLeJourDeNaissancePourTousLesNAMS() throws UnsupportedEncodingException, ParseException {
		var resultat = serviceUtilitaireNAM.obtenirCombinaisonsValidesDeNAM(PRENOM, NOM, FORMATTEUR_DATE.parse(DATE_NAISSANCE), SEXE);
		assertThat(resultat).isNotNull().hasSize(9).allMatch(nam -> nam.startsWith(DATE_NAISSANCE.substring(8), 8));
	}
	
	// le nam contient l'indicateur de jumeaux à son caractère 11
	@Test
	public void quandJeDemandeDobtenirTousLesNAMSPossiblesPourUnePersonne_alorsLeCaractere11EstComprisEntre1Et9() throws UnsupportedEncodingException, ParseException {
		var resultat = serviceUtilitaireNAM.obtenirCombinaisonsValidesDeNAM(PRENOM, NOM, FORMATTEUR_DATE.parse(DATE_NAISSANCE), SEXE);
		assertThat(resultat).isNotNull().hasSize(9).extracting(nam -> nam.substring(10, 11)).containsExactly("1", "2", "3", "4", "5", "6", "7", "8", "9");
	}
	
	
	// le nam contient les caracteres validateurs à son caractère 12
	@Test
	public void quandJeDemandeDobtenirTousLesNAMSPossiblesPourUnePersonne_alorsLeCaractere12estLeChiffreValidateur() throws UnsupportedEncodingException, ParseException {
		var resultat = serviceUtilitaireNAM.obtenirCombinaisonsValidesDeNAM(PRENOM, NOM, FORMATTEUR_DATE.parse(DATE_NAISSANCE), SEXE);
		//assertThat(resultat).isNotNull().allMatch(nam -> Pattern.matches("^[012456789]{1}$", nam.substring(11)));
		assertThat(resultat).isNotNull().hasSize(9).extracting(x -> x.substring(11)).containsExactly("4", "5", "6", "7", "8", "9", "0", "1", "2");
	}
	
	// si les entrants sont valides, on reçoit tous les nams possibles pour ces entrants
	@Test
	public void quandJeDemandeDobtenirTousLesNAMSPossiblesPourUnePersonne_alorsOnRetourneTousLesNAMSPossiblesPourCettePersonne() throws UnsupportedEncodingException, ParseException {
		var resultat = serviceUtilitaireNAM.obtenirCombinaisonsValidesDeNAM(PRENOM, NOM, FORMATTEUR_DATE.parse(DATE_NAISSANCE), SEXE);
		assertThat(resultat).isNotNull().hasSize(9).containsExactly("TREM04121914", 
				"TREM04121925", 
				"TREM04121936", 
				"TREM04121947", 
				"TREM04121958", 
				"TREM04121969", 
				"TREM04121970", 
				"TREM04121981", 
				"TREM04121992");
	}
	
	// -------------------------------------------------------------------

	

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
