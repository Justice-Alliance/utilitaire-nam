/**
 * 
 */
package ca.qc.inspq.nam.etapes;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Assert;

import ca.qc.inspq.nam.api.modele.Sexe;
import ca.qc.inspq.nam.api.utilitaire.UtilitairesNAM;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * @author diamam01
 *
 */
public class UtilitaireDefinitions {
	
	private String province;
	private String nam;
	private String reponseAttendu;
	private Exception exceptionMessage;
	private String prenom;
	private String nom;
	private Date dateNaissance;
	private String sexe;
	private Sexe sexeUsager;
	private String numeroNAM;
	private Date dateNaissanceUsager;
	private List<String> listeNam = new ArrayList<>();
	
	private UtilitairesNAM utilitairesNAM = new UtilitairesNAM();
	
	@Given("^Etant donne une \"([^\"]*)\"$")
	public void etant_donne_une(String province) throws Exception {
	   this.province = province;
	}

	@Given("^Un \"([^\"]*)\" fournie qui est valide$")
	public void un_fournie_qui_est_valide(String nam) throws Exception {
	   this.nam = nam;
	}

	@When("^Quand je clique sur le bouton valider$")
	public void quand_je_clique_sur_le_bouton_valider() throws Exception {
		boolean resultat = utilitairesNAM.validerNAM(this.nam, this.province);
		if(resultat) {
			this.reponseAttendu = "Le NAM est valide!";
		}
		else {
			this.reponseAttendu = "Le NAM est invalide!";
		}
	
	}

	@Then("^Je dois voir afficher \"([^\"]*)\"$")
	public void je_dois_voir_afficher(String reponse) throws Exception {
	   assertEquals(reponse, this.reponseAttendu);
	}
	

	@Given("^La champ \"([^\"]*)\" est vide$")
	public void la_champ_est_vide(String province) throws Exception {
		province = null;
	}

	@Given("^Le champ \"([^\"]*)\" est vide$")
	public void le_champ_est_vide(String nam) throws Exception {
		nam = null;
	}
	

	@When("^Quand je clique sur valider NAM$")
	public void quand_je_clique_sur_valider_NAM() throws Exception {
		try {
			@SuppressWarnings("unused")
			boolean resultat = utilitairesNAM.validerNAM(null, null);
		} catch(Exception exception) {
			this.exceptionMessage = exception;
		}
			
	}

	@Then("^Je dois voir un message \"([^\"]*)\"$")
	public void je_dois_voir_un_message(String message) throws Exception {
		Assert.assertNotEquals(message, this.exceptionMessage.getMessage());
	}
	
	
	@Given("^Etant donne un \"([^\"]*)\" and Un \"([^\"]*)\"$")
	public void etant_donne_un_and_Un(String prenom, String nom) throws Exception {
		this.prenom = prenom;
		 this.nom = nom;
	}

	@Given("^Une \"([^\"]*)\" and Un \"([^\"]*)\"$")
	public void une_and_Un(String dateNaissance, String sexe) throws Exception {
		this.dateNaissance =  new SimpleDateFormat("yyyy-MM-dd").parse(String.format("%s", dateNaissance));
		this.sexe = sexe;
	}
	

	@When("^quand je clique sur Generer$")
	public void quand_je_clique_sur_Generer() throws Exception {
	  
		this.listeNam = utilitairesNAM.obtenirCombinaisonsValidesDeNAM(this.prenom, this.nom, this.dateNaissance, this.sexe);
	}

	@Then("^Je dois afficher une liste ([^\"]*) valide pour cet usager$")
	public void je_dois_afficher_une_liste_TREM_TREM_TREM_TREM_TREM_TREM_TREM_TREM_TREM_valide_pour_cet_usager(String nams) throws Exception {
		Assert.assertEquals(nams.split(",").length, this.listeNam.size());
	}
	
	@Given("^La champ \"([^\"]*)\" est vide and le champ \"([^\"]*)\" est vide$")
	public void la_champ_est_vide_and_le_champ_est_vide(String prenom, String nom) throws Exception {
	   prenom =null;
	   nom = null;
	}

	@Given("^Le champ \"([^\"]*)\" est vide and le champ \"([^\"]*)\" est vide$")
	public void le_champ_est_vide_and_le_champ_est_vide(String dateNaissance, String sexe) throws Exception {
	   dateNaissance = null;
	   sexe = null;
	}

	@When("^Quand je clique sur Generer NAM$")
	public void quand_je_clique_sur_Generer_NAM() throws Exception {
		try {
			this.listeNam = utilitairesNAM.obtenirCombinaisonsValidesDeNAM(null, null, null, null);
		} catch(Exception exception) {
			this.exceptionMessage = exception;
		}
	}

	@Then("^Je dois afficher un message pour l'utilisateur$")
	public void je_dois_afficher_un_message_pour_l_utilisateur() throws Exception {

	   Assert.assertNotEquals("Une erreur inconnue s'est produite avec Utilitaire NAM.\r\n" + 
	   		"Veuillez contacter l'administrateur.",this.exceptionMessage.getMessage());
	}
	
	@Given("^Le \"([^\"]*)\" d'un usager$")
	public void le_d_un_usager(String nam) throws Exception {
	   this.numeroNAM = nam;
	}

	@When("^Quand je clique sur le bouton Obtenir l'information$")
	public void quand_je_clique_sur_le_bouton_Obtenir_l_information() throws Exception {
		this.dateNaissanceUsager = utilitairesNAM.trouverDateNaissance(this.numeroNAM);
	    this.sexeUsager = utilitairesNAM.obtenirSexe(this.numeroNAM);
	}

	@Then("^Je dois afficher la \"([^\"]*)\" and \"([^\"]*)\" de l'usager$")
	public void je_dois_afficher_la_and_de_l_usager(String dateNaissance, String sexe) throws Exception {
	    assertEquals(dateNaissance, DateFormatUtils.format(this.dateNaissanceUsager, "yyyy-MM-dd"));
	    assertEquals(sexe, this.sexeUsager.toString());
	}
	
	@Given("^Le champ \"([^\"]*)\" de l'usager est vide$")
	public void le_champ_de_l_usager_est_vide(String namUsager) throws Exception {
	   namUsager = null;
	}

	@When("^Quand je clique sur obtenir information sur le NAM$")
	public void quand_je_clique_sur_obtenir_information_sur_le_NAM() throws Exception {
	    
		try {
			this.dateNaissanceUsager = utilitairesNAM.trouverDateNaissance(null);
		    this.sexeUsager = utilitairesNAM.obtenirSexe(null);
		} catch(Exception exception) {
			this.exceptionMessage = exception;
		}
	}

	@Then("^Un message doit etre afficher pour l'usager$")
	public void un_message_doit_etre_afficher_pour_l_usager() throws Exception {
	   Assert.assertNotEquals("Vous devez saisir un NAM!", this.exceptionMessage.getMessage());
	}

}
