/**
 * 
 */
package ca.qc.inspq.nam.etapes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import ca.qc.inspq.nam.api.UtilitaireNamApiApplication;
import ca.qc.inspq.nam.api.modele.Provinces;
import ca.qc.inspq.nam.api.modele.Sexe;
import ca.qc.inspq.nam.api.utilitaire.ServiceUtilitairesNAM;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Quand;

@ContextConfiguration(classes = { UtilitaireNamApiApplication.class })
public class UtilitaireDefinitions {

	private boolean resultat;
	private boolean erreurValidationObtenue;
	private boolean erreurObtentionObtenue;
	private boolean erreurGenerationObtenue;
	
	private Sexe sexeUsager;
	private Date dateNaissanceUsager;
	
	private List<String> listeNamsGeneres;

	@Autowired
	private ServiceUtilitairesNAM utilitairesNAM;

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
	public void je_demande_de_générer_une_liste_de_numéros_d_assurance_maladie_pour_né_le_de_sexe(String prenom, String nom, String dateNaissance, String sexe) throws UnsupportedEncodingException, ParseException {
		listeNamsGeneres = utilitairesNAM.obtenirCombinaisonsValidesDeNAM(prenom, nom, new SimpleDateFormat("yyyy-MM-dd").parse(dateNaissance), Sexe.valueOf(sexe.toUpperCase()).getCode());
		erreurGenerationObtenue = false;
	}
	
	@Quand("je demande de générer une liste de numéros d'assurance maladie en fournissant des informations non valides")
	public void je_demande_de_générer_une_liste_de_numéros_d_assurance_maladie_en_fournissant_des_informations_non_valides() throws UnsupportedEncodingException {
		try {
			listeNamsGeneres = utilitairesNAM.obtenirCombinaisonsValidesDeNAM(null, null, null, null);
			erreurGenerationObtenue = false;
		} catch (InvalidParameterException e) {
			erreurGenerationObtenue = true;
		}
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
		assertThat(listeNamsGeneres).containsExactlyElementsOf(namsGeneresAttendus);
		assertThat(erreurGenerationObtenue).isFalse();
	}
	
	@Alors("je reçois un avertissement que les informations que j'ai fourni ne sont pas valides")
	public void je_reçois_un_avertissement_que_les_informations_que_j_ai_fourni_ne_sont_pas_valides() {
	    assertThat(erreurGenerationObtenue).isTrue();
	}
}
