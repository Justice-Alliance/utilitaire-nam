package ca.qc.inspq.nam.api.impl.domaine;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.qc.inspq.nam.api.Valider;
import ca.qc.inspq.nam.api.domaine.ServiceUtilitairesNAM;
import ca.qc.inspq.nam.api.domaine.modele.Provinces;

@Component
public class ValiderDomaine implements Valider {
	
	@Autowired
	private ServiceUtilitairesNAM utilitairesNAM;
	
	private boolean resultat;
	private boolean erreurValidationObtenue;

	@Override
	public void validerLeNumeroDassuranceMaladieDuneProvince(String nam, String province) {

		try {
			resultat = utilitairesNAM.validerNAM(nam, Provinces.fromString(province));
			erreurValidationObtenue = false;
		} catch (IllegalArgumentException e) {
			erreurValidationObtenue = true;
		}
	}

	@Override
	public void verifierQuuneConfirmationQueNamValideEstRecue() {
		assertThat(resultat).isTrue();
		assertThat(erreurValidationObtenue).isFalse();
	}

	@Override
	public void verifierQuunAvertissementEstRecu() {
		assertThat(resultat).isFalse();
		assertThat(erreurValidationObtenue).isFalse();
	}

}
