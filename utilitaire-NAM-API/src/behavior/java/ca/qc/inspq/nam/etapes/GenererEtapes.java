/**
 * 
 */
package ca.qc.inspq.nam.etapes;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import ca.qc.inspq.nam.api.Generer;
import ca.qc.inspq.nam.api.UtilitaireNamApiApplication;
import ca.qc.inspq.nam.api.domaine.modele.Personne;
import ca.qc.inspq.nam.api.domaine.modele.Sexe;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Quand;

@ContextConfiguration(classes = { UtilitaireNamApiApplication.class })
public class GenererEtapes {
	
	private static final String AUCUN_PRENOM = null;
	private static final String AUCUN_NOM = null;
	private static final LocalDate AUCUN_DATE_NAISSANCE = null;
	private static final Sexe AUCUN_SEXE = null;
	
	@Autowired
	private Generer api;
		
	@Quand("je demande de générer une liste de numéros d'assurance maladie pour {string} {string}, né le {string}, de sexe {string}")
	public void je_demande_de_générer_une_liste_de_numéros_d_assurance_maladie_pour_né_le_de_sexe(String prenom, String nom, String dateNaissance, String sexe) {
		var personne = new Personne(prenom, nom, LocalDate.parse(dateNaissance, DateTimeFormatter.ofPattern("yyyy-MM-dd")), Sexe.fromString(sexe.equals("masculin") ? "M" : "F"));
		api.genererUneListeDeNumerosDassuranceMaladiePour(personne);
	}
	
	@Quand("je demande de générer une liste de numéros d'assurance maladie en fournissant des informations non valides")
	public void je_demande_de_générer_une_liste_de_numéros_d_assurance_maladie_en_fournissant_des_informations_non_valides() throws UnsupportedEncodingException {
		var personne = new Personne(AUCUN_PRENOM, AUCUN_NOM, AUCUN_DATE_NAISSANCE, AUCUN_SEXE);
		api.genererUneListeDeNumerosDassuranceMaladiePour(personne);
	}	
	
	@Alors("je reçois la liste de numéros d'assurance maladie suivante:")
	public void je_reçois_la_liste_de_numéros_d_assurance_maladie_suivante(List<String> namsGeneresAttendus) {
		api.verifierQueLesNumerosDassuranceMaladieGeneresSont(namsGeneresAttendus);
	}
	
	@Alors("je reçois un avertissement que les informations que j'ai fourni ne sont pas valides")
	public void je_reçois_un_avertissement_que_les_informations_que_j_ai_fourni_ne_sont_pas_valides() {
	    api.verifierQuunAvertissementEstRecu();
	}
}
