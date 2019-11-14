package ca.qc.inspq.nam.api.impl.domaine;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.qc.inspq.nam.api.ObtenirInformations;
import ca.qc.inspq.nam.api.domaine.ServiceUtilitairesNAM;
import ca.qc.inspq.nam.api.domaine.modele.Sexe;

@Component
public class ObtenirInformationsDomaine implements ObtenirInformations {
	
	private boolean erreurObtentionObtenue;
	
	private Sexe sexeUsager;
	private LocalDate dateNaissanceUsager;
	
	@Autowired
	private ServiceUtilitairesNAM utilitairesNAM;

	@Override
	public void obtenirLesInformationsContenuesDansLeNam(String nam) {
		try {
			var informations = utilitairesNAM.obtenirInformationsContenuesDansLeNam(nam);
			this.dateNaissanceUsager = informations.getDateNaissance();
			this.sexeUsager = informations.getSexe();
			erreurObtentionObtenue = false;
		} catch (IllegalArgumentException | UnsupportedEncodingException | ParseException e) {
			erreurObtentionObtenue = true;
		}
	}

	@Override
	public void verifierQueLaDateDeNaissanceObtenueEst(String dateNaissanceAttendue) {
		assertThat(dateNaissanceAttendue).isEqualTo(this.dateNaissanceUsager.toString());
		assertThat(erreurObtentionObtenue).isFalse();
	}

	@Override
	public void verifierQueLeSexeObtenuEst(String sexeAttendu) {
		assertThat(sexeAttendu).isEqualTo(this.sexeUsager.toString().toLowerCase());
		assertThat(erreurObtentionObtenue).isFalse();
	}

	@Override
	public void verifierQuunAvertissementEstRecu() {
		assertThat(erreurObtentionObtenue).isTrue();
	}

}
