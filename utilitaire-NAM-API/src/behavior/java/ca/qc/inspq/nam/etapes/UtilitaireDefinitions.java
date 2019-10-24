/**
 * 
 */
package ca.qc.inspq.nam.etapes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Assert;

import ca.qc.inspq.nam.api.modele.Provinces;
import ca.qc.inspq.nam.api.modele.Sexe;
import ca.qc.inspq.nam.api.utilitaire.UtilitairesNAM;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Etantdonné;
import io.cucumber.java.fr.Quand;
import io.cucumber.java.fr.Étantdonné;
import io.cucumber.java.fr.Étantdonnée;
import io.cucumber.java.fr.Étantdonnéque;

public class UtilitaireDefinitions {

	private boolean resultat;
	private boolean erreurValidationObtenue;
	private boolean erreurObtentionObtenue;
	
	private Sexe sexeUsager;
	private Date dateNaissanceUsager;

	@Deprecated
	private String province;

	@Deprecated
	private String nam;

	@Deprecated
	private String reponseAttendu;
	
	@Deprecated
	private Exception exceptionMessage;
	private String prenom;
	private String nom;
	
	@Deprecated
	private Date dateNaissance;
	
	@Deprecated
	private String sexe;
	
	private String numeroNAM;
	
	private List<String> listeNam = new ArrayList<>();

	private UtilitairesNAM utilitairesNAM = new UtilitairesNAM();

	@Quand("je valide le numéro d'assurance maladie {string} émis par la province {string}")
	public void je_valide_le_numéro_d_assurance_maladie_émis_par_la_province(String nam, String province)
			throws UnsupportedEncodingException, ParseException {
		resultat = utilitairesNAM.validerNAM(nam, Provinces.fromString(province).toString());
		erreurValidationObtenue = false;
	}
	
	@Quand("j'effectue une validation en ne fournissant pas les informations nécessaires")
	public void j_effectue_une_validation_en_ne_fournissant_pas_les_informations_nécessaires() {
		try {
			resultat = utilitairesNAM.validerNAM(null, null);
			erreurValidationObtenue = false;
		} catch (Exception exception) {
			erreurValidationObtenue = true;
		}
	}
	
	@Quand("je demande d'obtenir les informations contenues dans le numéro d'assurance maladie {string}")
	public void je_demande_d_assurance_maladie(String nam) throws UnsupportedEncodingException, ParseException {
		this.dateNaissanceUsager = utilitairesNAM.trouverDateNaissance(nam);
		this.sexeUsager = utilitairesNAM.obtenirSexe(nam);
		erreurObtentionObtenue = false;
	}
	
	@Quand("je demande d'obtenir les informations contenues dans un numéro d'assurance maladie non valide")
	public void je_demande_d_assurance_maladie_non_valide() {
		try {
			this.dateNaissanceUsager = utilitairesNAM.trouverDateNaissance(null);
			this.sexeUsager = utilitairesNAM.obtenirSexe(null);
			erreurObtentionObtenue = false;
		} catch (Exception exception) {
			erreurObtentionObtenue = true;
		}
	}
	
	@Quand("je demande de générer une liste de numéros d'assurance maladie pour {string} {string}, né le {string}, de sexe {string}")
	public void je_demande_de_générer_une_liste_de_numéros_d_assurance_maladie_pour_né_le_de_sexe(String prenom, String nom, String dateNaissance, String sexe) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}
	
	@Quand("je demande de générer une liste de numéros d'assurance maladie en fournissant des informations non valides")
	public void je_demande_de_générer_une_liste_de_numéros_d_assurance_maladie_en_fournissant_des_informations_non_valides() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Alors("je reçois la confirmation que le numéro d'assurance maladie est valide")
	public void je_reçois_la_confirmation_que_le_numéro_d_assurance_maladie_est_valide() {
		assertThat(resultat).isTrue();
		assertThat(erreurValidationObtenue).isFalse();
	}

	@Alors("je reçois un avertissement que le numéro d'assurance maladie n'est pas valide")
	public void je_reçois_un_avertissement_que_le_numéro_d_est_pas_valide() {
		assertThat(resultat).isFalse();
		assertThat(erreurValidationObtenue).isFalse();
	}

	@Alors("je reçois un avertissement qu'il me manque de l'information pour pouvoir procéder à la validation")
	public void je_reçois_un_avertissement_qu_information_pour_pouvoir_procéder_à_la_validation() {
	    assertThat(erreurValidationObtenue).isTrue();
	}
	
	@Alors("je reçois l'information que la date de naissance est {string}")
	public void je_reçois_l_information_que_la_date_de_naissance_est(String dateNaissanceAttendue) {
		assertEquals(dateNaissanceAttendue, DateFormatUtils.format(this.dateNaissanceUsager, "yyyy-MM-dd"));
		assertThat(erreurObtentionObtenue).isFalse();
	}

	@Alors("je reçois l'information que le sexe est {string}")
	public void je_reçois_l_information_que_le_sexe_est(String sexeAttendu) {
		assertEquals(sexeAttendu, this.sexeUsager.toString().toLowerCase());
		assertThat(erreurObtentionObtenue).isFalse();
	}
	
	@Alors("je reçois un avertissement que mon numéro d'assurance maladie n'est pas valide")
	public void je_reçois_un_avertissement_que_mon_numéro_d_est_pas_valide() {
		assertThat(erreurObtentionObtenue).isTrue();
	}
	
	@Alors("je reçois la liste de numéros d'assurance maladie suivante:")
	public void je_reçois_la_liste_de_numéros_d_assurance_maladie_suivante(List<String> namsGeneresAttendus) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}
	
	@Alors("je reçois un avertissement que les informations que j'ai fourni ne sont pas valides")
	public void je_reçois_un_avertissement_que_les_informations_que_j_ai_fourni_ne_sont_pas_valides() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Étantdonnée("la province {string}")
	@Deprecated
	public void la_province(String province) {
		this.province = province;
	}

	@Étantdonnée("le numéro d'assurance maladie {string} fourni qui est valide")
	@Deprecated
	public void le_numéro_d_assurance_maladie_fourni_qui_est_valide(String nam) {
		this.nam = nam;
	}

	@Quand("je clique sur le bouton valider")
	public void quand_je_clique_sur_le_bouton_valider() throws Exception {
		boolean resultat = utilitairesNAM.validerNAM(this.nam, this.province);
		if (resultat) {
			this.reponseAttendu = "Le NAM est valide!";
		} else {
			this.reponseAttendu = "Le NAM est invalide!";
		}

	}

	@Alors("je dois voir afficher la réponse {string}")
	@Deprecated
	public void je_dois_voir_afficher_la_réponse(String reponse) {
		assertEquals(reponse, this.reponseAttendu);
	}

	@Étantdonnéque("le champ province est vide")
	@Deprecated
	public void le_champ_province_est_vide() {
		this.province = null;
	}

	@Étantdonnéque("le champ nam est vide")
	@Deprecated
	public void le_champ_nam_est_vide() {
		this.nam = null;
	}

	@Quand("je clique sur valider NAM")
	@Deprecated
	public void je_clique_sur_valider_NAM() {
		try {
			@SuppressWarnings("unused")
			boolean resultat = utilitairesNAM.validerNAM(null, null);
		} catch (Exception exception) {
			this.exceptionMessage = exception;
		}

	}

	@Alors("je dois voir un message {string}")
	@Deprecated
	public void je_dois_voir_un_message(String message) {
		Assert.assertNotEquals(message, this.exceptionMessage.getMessage());
	}

	@Etantdonné("le prénom {string} et le nom {string}")
	public void le_prénom_et_le_nom(String prenom, String nom) {
		this.prenom = prenom;
		this.nom = nom;
	}

	@Etantdonné("la date de naissance {string} et le sexe {string}")
	public void la_date_de_naissance_et_le_sexe(String dateNaissance, String sexe) throws ParseException {
		this.dateNaissance = new SimpleDateFormat("yyyy-MM-dd").parse(String.format("%s", dateNaissance));
		this.sexe = sexe;
	}

	@Quand("je clique sur Generer")
	public void je_clique_sur_Generer() throws UnsupportedEncodingException {
		this.listeNam = utilitairesNAM.obtenirCombinaisonsValidesDeNAM(this.prenom, this.nom, this.dateNaissance,
				this.sexe);
	}

	@Alors("^je dois afficher une liste ([^\"]*) valide pour cet usager$")
	public void je_dois_afficher_une_liste_TREM_TREM_TREM_TREM_TREM_TREM_TREM_TREM_TREM_valide_pour_cet_usager(
			String nams) throws Exception {
		Assert.assertEquals(nams.split(",").length, this.listeNam.size());
	}

	@Étantdonné("le champ prenom est vide et le champ nom est vide")
	public void le_champ_prenom_est_vide_et_le_champ_nom_est_vide() {
		this.prenom = null;
		this.nom = null;
	}

	@Étantdonné("le champ dateNaissance est vide et le champ sexe est vide")
	public void le_champ_dateNaissance_est_vide_et_le_champ_sexe_est_vide() {
		this.dateNaissance = null;
		this.sexe = null;
	}

	@Quand("je clique sur Generer NAM")
	public void je_clique_sur_Generer_NAM() {
		try {
			this.listeNam = utilitairesNAM.obtenirCombinaisonsValidesDeNAM(null, null, null, null);
		} catch (Exception exception) {
			this.exceptionMessage = exception;
		}
	}

	@Alors("je dois afficher un message pour l'utilisateur")
	public void je_dois_afficher_un_message_pour_l_utilisateur() {
		Assert.assertNotEquals(
				"Une erreur inconnue s'est produite avec Utilitaire NAM.\r\n" + "Veuillez contacter l'administrateur.",
				this.exceptionMessage.getMessage());
	}

	@Étantdonné("le numéro d'assurance maladie {string} d'un usager")
	@Deprecated
	public void le_numéro_d_un_usager(String nam) {
		this.numeroNAM = nam;
	}

	@Quand("je clique sur le bouton Obtenir l'information")
	@Deprecated
	public void je_clique_sur_le_bouton_Obtenir_l_information() throws UnsupportedEncodingException, ParseException {
		this.dateNaissanceUsager = utilitairesNAM.trouverDateNaissance(this.numeroNAM);
		this.sexeUsager = utilitairesNAM.obtenirSexe(this.numeroNAM);
	}

	@Alors("je dois afficher la date de naissance {string} et le sexe {string} de l'usager")
	@Deprecated
	public void je_dois_afficher_la_date_de_naissance_et_le_sexe_de_l_usager(String dateNaissance, String sexe) {
		assertEquals(dateNaissance, DateFormatUtils.format(this.dateNaissanceUsager, "yyyy-MM-dd"));
		assertEquals(sexe, this.sexeUsager.toString());
	}

	@Étantdonné("le champ nam de l'usager est vide")
	@Deprecated
	public void le_champ_nam_de_l_usager_est_vide() {
		this.nam = null;
	}

	@Quand("je clique sur obtenir information sur le NAM")
	@Deprecated
	public void je_clique_sur_obtenir_information_sur_le_NAM() {
		try {
			this.dateNaissanceUsager = utilitairesNAM.trouverDateNaissance(null);
			this.sexeUsager = utilitairesNAM.obtenirSexe(null);
		} catch (Exception exception) {
			this.exceptionMessage = exception;
		}
	}

	@Alors("un message doit etre afficher pour l'usager")
	public void un_message_doit_etre_afficher_pour_l_usager() {
		Assert.assertNotEquals("Vous devez saisir un NAM!", this.exceptionMessage.getMessage());
	}
}