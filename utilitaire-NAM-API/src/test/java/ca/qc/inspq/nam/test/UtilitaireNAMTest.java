package ca.qc.inspq.nam.test;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import static org.powermock.api.mockito.PowerMockito.*;

import ca.qc.inspq.nam.modele.Sexe;
import ca.qc.inspq.nam.utilitaire.UtilitairesNAM;


@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "ca.qc.inspq.nam.utilitaire.*")
public class UtilitaireNAMTest {


	  @Test 
	  public void doit_RetournerVRAI_Quand_ValiderNAM_EstAppelerAvecNomProvince_QuebecEtUnNAMValide() throws UnsupportedEncodingException, ParseException {
	  
		  //Arrange 
		  String nomProvince = "QC"; 
		  String nam = "TREM04121925"; 
		  boolean resultatAttendu = true;
		  spy(UtilitairesNAM.class);
		  when(UtilitairesNAM.validerNumeroCarteSanteQuebec(nam)).thenReturn(true);
		  
		  // Act 
		  boolean validerNam = UtilitairesNAM.validerNAM(nam, nomProvince);
		  
		  //Assert 
		  assertEquals(validerNam, resultatAttendu);
		  
	 
	 }
	  @Test 
	  public void doit_RetournerFAUX_Quand_ValiderNAM_EstAppelerAvecNomProvince_QuebecEtUnNAMIvalide() throws UnsupportedEncodingException, ParseException {
	  
		  //Arrange 
		  String nomProvince = "QC"; 
		  String nam = "TREM04121935"; 
		  boolean resultatAttendu = false;
		  spy(UtilitairesNAM.class);
		  when(UtilitairesNAM.validerNumeroCarteSanteQuebec(nam)).thenReturn(false);
		  
		  // Act 
		  boolean validerNam = UtilitairesNAM.validerNAM(nam, nomProvince);
		  
		  //Assert 
		  assertEquals(validerNam, resultatAttendu);
	 
	 }
	 
	  @Test 
	  public void doit_RetournerVRAI_Quand_ValiderNAM_EstAppelerAvecNomProvince_Alberta() throws UnsupportedEncodingException, ParseException {
	  
		  //Arrange 
		  String nomProvince = "AB"; 
		  String nam = "940114192"; 
		  boolean resultatAttendu = true;
		  spy(UtilitairesNAM.class);
		  when(UtilitairesNAM.validerStringRegex(nam, UtilitairesNAM.TypeRegex.REGEX_NAM_ALBERTA)).thenReturn(true);
		  
		  // Act 
		  boolean validerNam = UtilitairesNAM.validerNAM(nam, nomProvince);
		  
		  //Assert 
		  assertEquals(validerNam, resultatAttendu);
	 
	 }
	 
	  @Test 
	  public void doit_RetournerVRAI_Quand_ValiderNAM_EstAppelerAvecNomProvince_ColombieBritanique() throws UnsupportedEncodingException, ParseException {
	  
		  //Arrange 
		  String nomProvince = "BC"; 
		  String nam = "9401141925"; 
		  boolean resultatAttendu = true;
		  spy(UtilitairesNAM.class);
		  when(UtilitairesNAM.validerStringRegex(nam, UtilitairesNAM.TypeRegex.REGEX_NAM_COLOMBIE_BRITANNIQUE)).thenReturn(true);
		  when(UtilitairesNAM.validerNumeroCarteSanteColombieBritannique(nam)).thenReturn(true);
		  // Act 
		  boolean validerNam = UtilitairesNAM.validerNAM(nam, nomProvince);
		  
		  //Assert 
		  assertEquals(validerNam, resultatAttendu);
	 
	 }
	  @Test 
	  public void doit_RetournerVRAI_Quand_ValiderNAM_EstAppelerAvecNomProvince_Manitoba() throws UnsupportedEncodingException, ParseException {
	  
		  //Arrange 
		  String nomProvince = "MB"; 
		  String nam = "940114192"; 
		  boolean resultatAttendu = true;
		  spy(UtilitairesNAM.class);
		  when(UtilitairesNAM.validerStringRegex(nam, UtilitairesNAM.TypeRegex.REGEX_NAM_MANITOBA)).thenReturn(true);

		  // Act 
		  boolean validerNam = UtilitairesNAM.validerNAM(nam, nomProvince);
		  
		  //Assert 
		  assertEquals(validerNam, resultatAttendu);
	 
	 }
	  @Test 
	  public void doit_RetournerVRAI_Quand_ValiderNAM_EstAppelerAvecNomProvince_Territoires_DuNord() throws UnsupportedEncodingException, ParseException {
	  
		  //Arrange 
		  String nomProvince = "NT"; 
		  String nam = "9401141"; 
		  boolean resultatAttendu = true;
		  spy(UtilitairesNAM.class);
		  when(UtilitairesNAM.validerStringRegex(nam, UtilitairesNAM.TypeRegex.REGEX_NAM_TERRITOIRES_NO)).thenReturn(true);

		  // Act 
		  boolean validerNam = UtilitairesNAM.validerNAM(nam, nomProvince);
		  
		  //Assert 
		  assertEquals(validerNam, resultatAttendu);
	 
	 }
	 
	  @Test 
	  public void doit_RetournerVRAI_Quand_ValiderNAM_EstAppelerAvecNomProvince_NouvelleEcosse() throws UnsupportedEncodingException, ParseException {
	  
		  //Arrange 
		  String nomProvince = "NS"; 
		  String nam = "9401141923"; 
		  boolean resultatAttendu = true;
		  spy(UtilitairesNAM.class);
		  when(UtilitairesNAM.validerStringRegex(nam, UtilitairesNAM.TypeRegex.REGEX_NAM_NOUVELLE_ECOSSE)).thenReturn(true);

		  // Act 
		  boolean validerNam = UtilitairesNAM.validerNAM(nam, nomProvince);
		  
		  //Assert 
		  assertEquals(validerNam, resultatAttendu);
	 
	 }
	  @Test 
	  public void doit_RetournerVRAI_Quand_ValiderNAM_EstAppelerAvecNomProvince_NouveauBrunswick() throws UnsupportedEncodingException, ParseException {
	  
		  //Arrange 
		  String nomProvince = "NB"; 
		  String nam = "940114192"; 
		  boolean resultatAttendu = true;
		  spy(UtilitairesNAM.class);
		  when(UtilitairesNAM.validerStringRegex(nam, UtilitairesNAM.TypeRegex.REGEX_NAM_NOUVEAU_BRUNSWICK)).thenReturn(true);

		  // Act 
		  boolean validerNam = UtilitairesNAM.validerNAM(nam, nomProvince);
		  
		  //Assert 
		  assertEquals(validerNam, resultatAttendu);
	 
	 }
	  @Test 
	  public void doit_RetournerVRAI_Quand_ValiderNAM_EstAppelerAvecNomProvince_TerreNeuveLabrador() throws UnsupportedEncodingException, ParseException {
	  
		  //Arrange 
		  String nomProvince = "NL"; 
		  String nam = "940114192698"; 
		  boolean resultatAttendu = true;
		  spy(UtilitairesNAM.class);
		  when(UtilitairesNAM.validerStringRegex(nam, UtilitairesNAM.TypeRegex.REGEX_NAM_TERRE_NEUVE_LABRADOR)).thenReturn(true);

		  // Act 
		  boolean validerNam = UtilitairesNAM.validerNAM(nam, nomProvince);
		  
		  //Assert 
		  assertEquals(validerNam, resultatAttendu);
	 
	 }
	  @Test 
	  public void doit_RetournerVRAI_Quand_ValiderNAM_EstAppelerAvecNomProvince_Nunavut() throws UnsupportedEncodingException, ParseException {
	  
		  //Arrange 
		  String nomProvince = "NU"; 
		  String nam = "94011419"; 
		  boolean resultatAttendu = true;
		  spy(UtilitairesNAM.class);
		  when(UtilitairesNAM.validerStringRegex(nam, UtilitairesNAM.TypeRegex.REGEX_NAM_NUNAVUT)).thenReturn(true);

		  // Act 
		  boolean validerNam = UtilitairesNAM.validerNAM(nam, nomProvince);
		  
		  //Assert 
		  assertEquals(validerNam, resultatAttendu);
	 
	 }
	  @Test 
	  public void doit_RetournerVRAI_Quand_ValiderNAM_EstAppelerAvecNomProvince_Ontario() throws UnsupportedEncodingException, ParseException {
	  
		  //Arrange 
		  String nomProvince = "ON"; 
		  String nam = "9401141925"; 
		  boolean resultatAttendu = true;
		  spy(UtilitairesNAM.class);
		  when(UtilitairesNAM.validerStringRegex(nam, UtilitairesNAM.TypeRegex.REGEX_NAM_ONTARIO)).thenReturn(true);
		  when(UtilitairesNAM.validerNumeroCarteSanteOntario(nam)).thenReturn(true);
		  // Act 
		  boolean validerNam = UtilitairesNAM.validerNAM(nam, nomProvince);
		  
		  //Assert 
		  assertEquals(validerNam, resultatAttendu);
	 
	 }
	  @Test 
	  public void doit_RetournerVRAI_Quand_ValiderNAM_EstAppelerAvecNomProvince_IlePrinceEdouard() throws UnsupportedEncodingException, ParseException {
	  
		  //Arrange 
		  String nomProvince = "PE"; 
		  String nam = "940114195"; 
		  boolean resultatAttendu = true;
		  spy(UtilitairesNAM.class);
		  when(UtilitairesNAM.validerStringRegex(nam, UtilitairesNAM.TypeRegex.REGEX_NAM_ILE_PRINCE_EDOUARD)).thenReturn(true);

		  // Act 
		  boolean validerNam = UtilitairesNAM.validerNAM(nam, nomProvince);
		  
		  //Assert 
		  assertEquals(validerNam, resultatAttendu);
	 
	 }
	  @Test 
	  public void doit_RetournerVRAI_Quand_ValiderNAM_EstAppelerAvecNomProvince_Saskatchwan() throws UnsupportedEncodingException, ParseException {
	  
		  //Arrange 
		  String nomProvince = "SK"; 
		  String nam = "940114195"; 
		  boolean resultatAttendu = true;
		  spy(UtilitairesNAM.class);
		  when(UtilitairesNAM.validerStringRegex(nam, UtilitairesNAM.TypeRegex.REGEX_NAM_SASKATCHEWAN)).thenReturn(true);

		  // Act 
		  boolean validerNam = UtilitairesNAM.validerNAM(nam, nomProvince);
		  
		  //Assert 
		  assertEquals(validerNam, resultatAttendu);
	 
	 }
	  @Test 
	  public void doit_RetournerVRAI_Quand_ValiderNAM_EstAppelerAvecNomProvince_Yukon() throws UnsupportedEncodingException, ParseException {
	  
		  //Arrange 
		  String nomProvince = "YT"; 
		  String nam = "940114195"; 
		  boolean resultatAttendu = true;
		  spy(UtilitairesNAM.class);
		  when(UtilitairesNAM.validerStringRegex(nam, UtilitairesNAM.TypeRegex.REGEX_NAM_YUKON)).thenReturn(true);

		  // Act 
		  boolean validerNam = UtilitairesNAM.validerNAM(nam, nomProvince);
		  
		  //Assert 
		  assertEquals(validerNam, resultatAttendu);
	 
	 }
	  @Test(expected = IllegalArgumentException.class)
	  public void doit_RetournerIllegalArgumentException_Quand_ValiderNAM_EstAppelerAvecNomProvince_CorrespondAAucuneProvince() throws UnsupportedEncodingException, ParseException {
	  
		  //Arrange 
		  IllegalArgumentException exception = new IllegalArgumentException("La province de la carte santé n'est pas valide.");
		  String nomProvince = "TT"; 
		  String nam = "BOUF94011419"; 
		  spy(UtilitairesNAM.class);
		  doThrow(new IllegalArgumentException()).when(UtilitairesNAM.class);
		  UtilitairesNAM.validerNumeroCarteSanteQuebec(nam);

		  // Act 
		  UtilitairesNAM.validerNAM(nam, nomProvince);
		  
		  //Assert 
		  assertEquals("La province de la carte santé n'est pas valide.", exception.getMessage());
	 
	 }
	 
	  @Test(expected = IllegalArgumentException.class) 
	  public void doit_RetournerIllegalArgumentException_Quand_ValiderNAM_EstAppelerAvecNomProvince_ColombieBritanique_EtNombreCharactersEstDifferentDe10() throws UnsupportedEncodingException, ParseException {
	  
		  //Arrange 
		  IllegalArgumentException exception = new IllegalArgumentException("Le numero de carte santé spécifié n'a pas la bonne taille");
		  String nomProvince = "BC"; 
		  String nam = "9401141925"; 
		  spy(UtilitairesNAM.class);
		  when(UtilitairesNAM.validerStringRegex(nam, UtilitairesNAM.TypeRegex.REGEX_NAM_COLOMBIE_BRITANNIQUE)).thenReturn(true);
		  doThrow(exception).when(UtilitairesNAM.class);
		  UtilitairesNAM.validerNumeroCarteSanteColombieBritannique(nam);
		 
		  // Act 
		  UtilitairesNAM.validerNAM(nam, nomProvince);
		  
		  //Assert 
		  assertEquals("Le numero de carte santé spécifié n'a pas la bonne taille", exception.getMessage());
	 
	 }
	  @Test
	  public void doit_RetournerVRAI_Quand_ValiderNumeroCarteSanteQuebec_EstAppelerAvecCorrecteNAM() throws UnsupportedEncodingException, ParseException {
	  
		  //Arrange 
		  String nam = "TREM04121914"; 
		  boolean resultatAttendu = true;
		  spy(UtilitairesNAM.class);
		  when(UtilitairesNAM.validerStringRegex(nam, UtilitairesNAM.TypeRegex.REGEX_NAM_QUEBEC)).thenReturn(true);

		  // Act 
		  boolean validerNam = UtilitairesNAM.validerNumeroCarteSanteQuebec(nam);
		  
		  //Assert 
		  assertEquals(validerNam, resultatAttendu);
	 
	 }
	  @Test 
	  public void doit_RetournerFAUX_Quand_ValiderNumeroCarteSanteQuebec_EstAppelerAvecIncorrectNAM() throws UnsupportedEncodingException, ParseException {
	  
		  //Arrange 
		  String nam = "TREM04121934"; 
		  boolean resultatAttendu = false;
		  spy(UtilitairesNAM.class);
		  when(UtilitairesNAM.validerStringRegex(nam, UtilitairesNAM.TypeRegex.REGEX_NAM_QUEBEC)).thenReturn(false);

		  // Act 
		  boolean validerNam = UtilitairesNAM.validerNumeroCarteSanteQuebec(nam);
		  
		  //Assert 
		  assertEquals(validerNam, resultatAttendu);
	 
	 }
	  @Test 
	  public void doit_RetournerVRAI_Quand_ValiderStringRegex_EstAppelerAvecBonChaineCharacteres() throws UnsupportedEncodingException, ParseException {
	  
		  //Arrange 
		  String chaineTexte = "BOUF94011419"; 
		  boolean resultatAttendu = true;

		  // Act 
		  boolean validerChaine = UtilitairesNAM.validerStringRegex(chaineTexte, UtilitairesNAM.TypeRegex.REGEX_NAM_QUEBEC);
		  
		  //Assert 
		  assertEquals(validerChaine, resultatAttendu);
	 
	 }
	  @Test 
	  public void doit_RetournerFAUX_Quand_ValiderStringRegex_EstAppelerAvecChaineCharacteresNull() throws UnsupportedEncodingException, ParseException {
	  
		  //Arrange 
		  String chaineTexte = null; 
		  boolean resultatAttendu = false ;

		  // Act 
		  boolean validerChaine = UtilitairesNAM.validerStringRegex(chaineTexte, UtilitairesNAM.TypeRegex.REGEX_NAM_QUEBEC);
		  
		  //Assert 
		  assertEquals(validerChaine, resultatAttendu);
	 
	 }
	  @Test 
	  public void doit_RetournerVRAI_Quand_ValiderNumeroCarteSanteOntario_EstAppelerAvecCorrectNAM() throws UnsupportedEncodingException, ParseException {
	  
		  //Arrange 
		  String nam = "9401141925";  
		  boolean resultatAttendu = true;

		  // Act 
		  boolean validerNam = UtilitairesNAM.validerNumeroCarteSanteOntario(nam);
		  
		  //Assert 
		  assertEquals(validerNam, resultatAttendu);
	 
	 }
	  @Test(expected = IllegalArgumentException.class) 
	  public void doit_RetournerIllegalArgumentException_Quand_ValiderNumeroCarteSanteOntario_EstAppelerAvecNAMDeLongueurDifferentDe10() throws UnsupportedEncodingException, ParseException {
	  
		  //Arrange 
		  String nam = "940114192536";  
		  IllegalArgumentException exception = new IllegalArgumentException("Le numero de carte santé spécifié n'a pas la bonne taille");
		 
		  // Act 
		  UtilitairesNAM.validerNumeroCarteSanteOntario(nam);
		  
		  //Assert 
		  assertEquals("Le numero de carte santé spécifié n'a pas la bonne taille", exception.getMessage());
	 
	 }
	  @Test 
	  public void doit_RetournerVRAI_Quand_ValiderNumeroCarteSanteColombieBritannique_EstAppelerAvecCorrectNAM() throws UnsupportedEncodingException, ParseException {
	  
		  //Arrange 
		  String nam = "9759528158";  
		  boolean resultatAttendu = true;

		  // Act 
		  boolean validerNam = UtilitairesNAM.validerNumeroCarteSanteColombieBritannique(nam);
		  
		  //Assert 
		  assertEquals(validerNam, resultatAttendu);
	 
	 }
	  @Test(expected = IllegalArgumentException.class) 
	  public void doit_RetournerIllegalArgumentException_Quand_ValiderNumeroCarteSanteColombieBritannique_EstAppelerAvecNAMDeLongueurDifferentDe10() throws UnsupportedEncodingException, ParseException {
	  
		  //Arrange 
		  String nam = "940114192536";  
		  IllegalArgumentException exception = new IllegalArgumentException("Le numero de carte santé spécifié n'a pas la bonne taille");
		 
		  // Act 
		  UtilitairesNAM.validerNumeroCarteSanteColombieBritannique(nam);
		  
		  //Assert 
		  assertEquals("Le numero de carte santé spécifié n'a pas la bonne taille", exception.getMessage());
	 
	 }
	  @Test 
	  public void doit_RetournerUneListeDeNAMValide_Quand_ObtenirCombinaisonsValidesDeNAM_EstAppeleAvecLesBonsParametres() throws UnsupportedEncodingException, ParseException {
	  
		  //Arrange 
		  List<String> valideNams = new ArrayList<>();
		  String prenom = "Martin" ;
		  String nom = "Tremblay";
		  Date dateNaissance = new Date(2004-12-19);
		  String sexe = "M";
		  // Act 
		  valideNams = UtilitairesNAM.obtenirCombinaisonsValidesDeNAM(prenom, nom, dateNaissance, sexe);
		  
		  //Assert 
		  assertEquals(valideNams.size(), 9);
	 
	 }
	  @Test(expected = InvalidParameterException.class) 
	  public void doit_RetournerInvalidParameterException_Quand_ObtenirCombinaisonsValidesDeNAM_EstAppeleAvecUnDesParametreNullOuVide() throws UnsupportedEncodingException, ParseException {
	  
		  //Arrange   
		  InvalidParameterException exception = new InvalidParameterException("Vous devez fournir un prénom, un nom et une date de naissance.");
		  String prenom = "" ;
		  String nom = "";
		  Date dateNaissance = null;
		  String sexe = "";
		  // Act 
		  UtilitairesNAM.obtenirCombinaisonsValidesDeNAM(prenom, nom, dateNaissance, sexe);
		  
		  //Assert 
		  assertEquals("Vous devez fournir un prénom, un nom et une date de naissance.", exception.getMessage());
	 
	 }
	  @Test 
	  public void doit_RetournerLeSexeDeLaPersonne_Quand_ObtenirSexe_EstAppeleAvecUnNAMValide() throws UnsupportedEncodingException, ParseException {
	  
		  //Arrange 
		  String nam = "TREM04121925";
		  spy(UtilitairesNAM.class);
		  when(UtilitairesNAM.validerNAM(nam, "QC")).thenReturn(true);
		  Sexe sexeAttendu = Sexe.MASCULIN; 
		  // Act 
		  Sexe sexe = UtilitairesNAM.obtenirSexe(nam);
		  
		  //Assert 
		  assertEquals(sexe, sexeAttendu);
	 
	 }
	  @Test(expected = InvalidParameterException.class) 
	  public void doit_RetournerInvalidParameterException_Quand_ObtenirSexe_EstAppeleAvecUnNAMInValide() throws UnsupportedEncodingException, ParseException {
	  
		  //Arrange   
		  InvalidParameterException exception = new InvalidParameterException("Le NAM est invalide");
		  String nam = "TREM04121935";
		  spy(UtilitairesNAM.class);
		  when(UtilitairesNAM.validerNAM(nam, "QC")).thenReturn(false);
		  // Act 
		  UtilitairesNAM.obtenirSexe(nam);
		  
		  //Assert 
		  assertEquals("Le NAM est invalide", exception.getMessage());
	 
	 }
	  @Test 
	  public void doit_RetournerUneDateNaissanceValide_Quand_TrouverDateNaissance_EstAppeleAvecUnNAMValide() throws UnsupportedEncodingException, ParseException {
	  
		  //Arrange 
		  String nam = "TREM04121925";
		  spy(UtilitairesNAM.class);
		  when(UtilitairesNAM.validerNAM(nam, "QC")).thenReturn(true);
		  Date dateNaissanceAttendu =  new SimpleDateFormat("yyyy-MM-dd").parse(String.format("%s-%d-%s", 2004, 12, 19));
		  // Act 
		  Date dateNaissance = UtilitairesNAM.trouverDateNaissance(nam);
		  
		  //Assert 
		  assertEquals(dateNaissance, dateNaissanceAttendu);
	 
	 }
	  @Test(expected = InvalidParameterException.class) 
	  public void doit_RetournerInvalidParameterException_Quand_TrouverDateNaissance_EstAppeleAvecUnNAMInValide() throws UnsupportedEncodingException, ParseException {
	  
		  //Arrange   
		  InvalidParameterException exception = new InvalidParameterException("Le NAM est invalide");
		  String nam = "TREM04121935";
		  spy(UtilitairesNAM.class);
		  when(UtilitairesNAM.validerNAM(nam, "QC")).thenReturn(false);
		  // Act 
		  UtilitairesNAM.trouverDateNaissance(nam);
		  
		  //Assert 
		  assertEquals("Le NAM est invalide", exception.getMessage());
	 
	 }
	 @Test
	 public void doit_RetournerNull_Quand_NormaliserRAMQ_EstAppeleAvecUnParametreStringNull() {
		 
		 // Arrange
		 String texte = null;
		 
		 // Act
		 String resultat = UtilitairesNAM.normaliserRAMQ(texte);
		 
		 // Assert
		 
		 assertNull(resultat);
		 	 
	 }
	
	 

}
