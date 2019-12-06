package ca.qc.inspq.nam.api.impl.domaine;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.qc.inspq.nam.api.Generer;
import ca.qc.inspq.nam.api.domaine.ServiceUtilitairesNAM;
import ca.qc.inspq.nam.api.domaine.modele.Personne;

@Component
public class GenererDomaine implements Generer {
	
	private List<String> listeNamsGeneres;
	private boolean erreurGenerationObtenue;
	
	@Autowired
	private ServiceUtilitairesNAM utilitairesNAM;

	@Override
	public void genererUneListeDeNumerosDassuranceMaladiePour(Personne personne) {
		try {
			listeNamsGeneres = utilitairesNAM.obtenirCombinaisonsValidesDeNAM(personne);
			erreurGenerationObtenue = false;
		} catch (UnsupportedEncodingException | IllegalArgumentException e) {
			erreurGenerationObtenue = true;
		}
	}

	@Override
	public void verifierQueLesNumerosDassuranceMaladieGeneresSont(List<String> namsGeneresAttendus) {
		assertThat(listeNamsGeneres).containsExactlyElementsOf(namsGeneresAttendus);
		assertThat(erreurGenerationObtenue).isFalse();
	}

	@Override
	public void verifierQuunAvertissementEstRecu() {
		assertThat(erreurGenerationObtenue).isTrue();
	}

}
