package ca.qc.inspq.nam.etapes;

import org.springframework.beans.factory.annotation.Autowired;

import ca.qc.inspq.nam.api.ObtenirInformations;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Quand;

public class ObtenirInformationsEtapes {
	
	private static final String NAM_NON_VALIDE = "dsfadsfdsf";

	@Autowired
	private ObtenirInformations api;
	
	@Quand("je demande d'obtenir les informations contenues dans le numéro d'assurance maladie {string}")
	public void je_demande_d_assurance_maladie(String nam) {
		api.obtenirLesInformationsContenuesDansLeNam(nam);
	}
	
	@Quand("je demande d'obtenir les informations contenues dans un numéro d'assurance maladie non valide")
	public void je_demande_d_assurance_maladie_non_valide() {
		api.obtenirLesInformationsContenuesDansLeNam(NAM_NON_VALIDE);
	}
	
	@Alors("je reçois l'information que la date de naissance est {string}")
	public void je_reçois_l_information_que_la_date_de_naissance_est(String dateNaissanceAttendue) {
		api.verifierQueLaDateDeNaissanceObtenueEst(dateNaissanceAttendue);
	}

	@Alors("je reçois l'information que le sexe est {string}")
	public void je_reçois_l_information_que_le_sexe_est(String sexeAttendu) {
		api.verifierQueLeSexeObtenuEst(sexeAttendu);
	}
	
	@Alors("je reçois un avertissement que mon numéro d'assurance maladie n'est pas valide")
	public void je_reçois_un_avertissement_que_mon_numéro_d_est_pas_valide() {
		api.verifierQuunAvertissementEstRecu();
	}
}
