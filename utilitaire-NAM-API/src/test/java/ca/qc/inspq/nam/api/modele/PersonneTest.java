package ca.qc.inspq.nam.api.modele;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class PersonneTest {
	
	private static final String PRENOM = "Étienne";
	private static final String PRENOM_NORMALISE = "ETIENNE";
	private static final String NOM = "L'Heureux";
	private static final String NOM_NORMALISE = "LHEUREUX";
	
	private static final int ANNEE = 1978;
	private static final int MOIS = 5;
	private static final int JOUR = 24;
	
	private static final LocalDate DATE_NAISSANCE = LocalDate.of(ANNEE, MOIS, JOUR);
	
	@Test
	public void quandJobtiensLePrenomNormaliseDunePersonne_alorsLePrenomNormaliseEstRetourneEnMajuscules() {
		var personne = new Personne(PRENOM, NOM, DATE_NAISSANCE, Sexe.MASCULIN);
		assertThat(personne.getPrenomNormalise()).isNotNull().isEqualTo(PRENOM_NORMALISE);
	}
	
	@Test
	public void quandJobtiensLeNomNormaliseDunePersonne_alorsLeNomNormaliseEstRetourneEnMajuscules() {
		var personne = new Personne(PRENOM, NOM, DATE_NAISSANCE, Sexe.MASCULIN);
		assertThat(personne.getNomNormalise()).isNotNull().isEqualTo(NOM_NORMALISE);
	}
}
