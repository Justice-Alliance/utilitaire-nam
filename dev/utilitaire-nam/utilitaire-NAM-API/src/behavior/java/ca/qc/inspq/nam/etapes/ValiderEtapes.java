package ca.qc.inspq.nam.etapes;

import org.springframework.beans.factory.annotation.Autowired;

import ca.qc.inspq.nam.api.Valider;
import ca.qc.inspq.nam.api.domaine.modele.Provinces;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Quand;

public class ValiderEtapes {
	
	private static final String AUCUN_NAM = null;

	@Autowired
	private Valider api;
	
	@Quand("je valide le numéro d'assurance maladie {string} émis par la province {string}")
	public void je_valide_le_numéro_d_assurance_maladie_émis_par_la_province(String nam, String province) {
		api.validerLeNumeroDassuranceMaladieDuneProvince(nam, province);
	}
	
	@Quand("j'effectue une validation en ne fournissant pas les informations nécessaires")
	public void j_effectue_une_validation_en_ne_fournissant_pas_les_informations_nécessaires() {
		api.validerLeNumeroDassuranceMaladieDuneProvince(AUCUN_NAM, Provinces.QC.getLibelle());
	}
	
	@Alors("je reçois la confirmation que le numéro d'assurance maladie est valide")
	public void je_reçois_la_confirmation_que_le_numéro_d_assurance_maladie_est_valide() {
		api.verifierQuuneConfirmationQueNamValideEstRecue();
	}

	@Alors("je reçois un avertissement que le numéro d'assurance maladie n'est pas valide")
	public void je_reçois_un_avertissement_que_le_numéro_d_est_pas_valide() {
		api.verifierQuunAvertissementEstRecu();
	}

	@Alors("je reçois un avertissement qu'il me manque de l'information pour pouvoir procéder à la validation")
	public void je_reçois_un_avertissement_qu_information_pour_pouvoir_procéder_à_la_validation() {
		api.verifierQuunAvertissementEstRecu();
	}
}
