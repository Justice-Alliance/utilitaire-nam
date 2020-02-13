package ca.qc.inspq.nam.api.domaine.modele;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import ca.qc.inspq.nam.api.domaine.modele.Personne;
import ca.qc.inspq.nam.api.domaine.modele.Sexe;

@RunWith(SpringRunner.class)
public class PersonneTest {
	
	private static final String PRENOM = "Étienne";
	private static final String PRENOM_NORMALISE = "ETIENNE";
	private static final String NOM = "L'Heureux";
	private static final String NOM_NORMALISE = "LHEUREUX";
	
	private static final String NOM_ST_TIRET = "St-Martin";
	private static final String NOM_ST_TIRET_NORMALISE = "STMARTIN";
	private static final String NOM_ST_ESPACE = "St Pierre";
	private static final String NOM_ST_ESPACE_NORMALISE = "STPIERRE";
	private static final String NOM_STE_TIRET = "Ste-Marie";
	private static final String NOM_STE_TIRET_NORMALISE = "STMARIE";
	private static final String NOM_STE_ESPACE = "Ste Sophie";
	private static final String NOM_STE_ESPACE_NORMALISE = "STSOPHIE";
	private static final String NOM_SAINT_TIRET = "Saint-Paul";
	private static final String NOM_SAINT_TIRET_NORMALISE = "STPAUL";
	private static final String NOM_SAINT_ESPACE = "Saint Charles";
	private static final String NOM_SAINT_ESPACE_NORMALISE = "STCHARLES";
	private static final String NOM_SAINTE_TIRET = "Sainte-Foy";
	private static final String NOM_SAINTE_TIRET_NORMALISE = "STFOY";
	private static final String NOM_SAINTE_ESPACE = "Sainte Léa";
	private static final String NOM_SAINTE_ESPACE_NORMALISE = "STLEA";
	private static final String NOM_SAINTS_TIRET = "Saints-Martyr";
	private static final String NOM_SAINTS_TIRET_NORMALISE = "STMARTYR";
	private static final String NOM_SAINTS_ESPACE = "Saints Papes";
	private static final String NOM_SAINTS_ESPACE_NORMALISE = "STPAPES";
	private static final String NOM_SAINTES_TIRET = "Saintes-Dames";
	private static final String NOM_SAINTES_TIRET_NORMALISE = "STDAMES";
	private static final String NOM_SAINTES_ESPACE = "Saintes soeurs";
	private static final String NOM_SAINTES_ESPACE_NORMALISE = "STSOEURS";
	private static final String NOM_COMMENCANT_PAR_STE = "Stellard";
	private static final String NOM_COMMENCANT_PAR_STE_NORMALISE = "STELLARD";
	private static final String NOM_COMMENCANT_PAR_SAINT = "Saintdoux";
	private static final String NOM_COMMENCANT_PAR_SAINT_NORMALISE = "SAINTDOUX";
	private static final String NOM_COMMENCANT_PAR_SAINTE = "Saintetique";
	private static final String NOM_COMMENCANT_PAR_SAINTE_NORMALISE = "SAINTETIQUE";
	private static final String NOM_COMMENCANT_PAR_SAINTS = "Saintsort";
	private static final String NOM_COMMENCANT_PAR_SAINTS_NORMALISE = "SAINTSORT";
	private static final String NOM_COMMENCANT_PAR_SAINTES = "Saintesoie";
	private static final String NOM_COMMENCANT_PAR_SAINTES_NORMALISE = "SAINTESOIE";
	
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
	
	@Test
	public void quandJobtiensLeNomNormaliseDunePersonneDontLeNomCommenceParStSuiviDunTiret_alorsLeNomNormaliseContientSTSansTiret() {
		var personne = new Personne(PRENOM, NOM_ST_TIRET, DATE_NAISSANCE, Sexe.MASCULIN);
		assertThat(personne.getNomNormalise()).isNotNull().isEqualTo(NOM_ST_TIRET_NORMALISE);
	}
	
	@Test
	public void quandJobtiensLeNomNormaliseDunePersonneDontLeNomCommenceParStSuiviDunEspace_alorsLeNomNormaliseContientSTSansEspace () {
		var personne = new Personne(PRENOM, NOM_ST_ESPACE, DATE_NAISSANCE, Sexe.MASCULIN);
		assertThat(personne.getNomNormalise()).isNotNull().isEqualTo(NOM_ST_ESPACE_NORMALISE);
	}
	
	@Test
	public void quandJobtiensLeNomNormaliseDunePersonneDontLeNomCommenceParSteSuiviDunTiret_alorsLeNomNormaliseContientSTSansTiret() {
		var personne = new Personne(PRENOM, NOM_STE_TIRET, DATE_NAISSANCE, Sexe.MASCULIN);
		assertThat(personne.getNomNormalise()).isNotNull().isEqualTo(NOM_STE_TIRET_NORMALISE);
	}
	
	@Test
	public void quandJobtiensLeNomNormaliseDunePersonneDontLeNomCommenceParSteSuiviDunEspace_alorsLeNomNormaliseContientSTSansEspace() {
		var personne = new Personne(PRENOM, NOM_STE_ESPACE, DATE_NAISSANCE, Sexe.MASCULIN);
		assertThat(personne.getNomNormalise()).isNotNull().isEqualTo(NOM_STE_ESPACE_NORMALISE);
	}
	
	@Test
	public void quandJobtiensLeNomNormaliseDunePersonneDontLeNomCommenceParSaintSuiviDunTiret_alorsLeNomNormaliseContientSTSansTiret() {
		var personne = new Personne(PRENOM, NOM_SAINT_TIRET, DATE_NAISSANCE, Sexe.MASCULIN);
		assertThat(personne.getNomNormalise()).isNotNull().isEqualTo(NOM_SAINT_TIRET_NORMALISE);
	}
	
	@Test
	public void quandJobtiensLeNomNormaliseDunePersonneDontLeNomCommenceParSaintSuiviDunEspace_alorsLeNomNormaliseContientSTSansEspace() {
		var personne = new Personne(PRENOM, NOM_SAINT_ESPACE, DATE_NAISSANCE, Sexe.MASCULIN);
		assertThat(personne.getNomNormalise()).isNotNull().isEqualTo(NOM_SAINT_ESPACE_NORMALISE);
	}
	
	@Test
	public void quandJobtiensLeNomNormaliseDunePersonneDontLeNomCommenceParSainteSuiviDunTiret_alorsLeNomNormaliseContientSTSansTiret() {
		var personne = new Personne(PRENOM, NOM_SAINTE_TIRET, DATE_NAISSANCE, Sexe.MASCULIN);
		assertThat(personne.getNomNormalise()).isNotNull().isEqualTo(NOM_SAINTE_TIRET_NORMALISE);
	}
	
	@Test
	public void quandJobtiensLeNomNormaliseDunePersonneDontLeNomCommenceParSainteSuiviDunEspace_alorsLeNomNormaliseContientSTSansEspace() {
		var personne = new Personne(PRENOM, NOM_SAINTE_ESPACE, DATE_NAISSANCE, Sexe.MASCULIN);
		assertThat(personne.getNomNormalise()).isNotNull().isEqualTo(NOM_SAINTE_ESPACE_NORMALISE);
	}
	
	@Test
	public void quandJobtiensLeNomNormaliseDunePersonneDontLeNomCommenceParSaintsSuiviDunTiret_alorsLeNomNormaliseContientSTSansTiret() {
		var personne = new Personne(PRENOM, NOM_SAINTS_TIRET, DATE_NAISSANCE, Sexe.MASCULIN);
		assertThat(personne.getNomNormalise()).isNotNull().isEqualTo(NOM_SAINTS_TIRET_NORMALISE);
	}
	
	@Test
	public void quandJobtiensLeNomNormaliseDunePersonneDontLeNomCommenceParSaintsSuiviDunEspace_alorsLeNomNormaliseContientSTSansEspace() {
		var personne = new Personne(PRENOM, NOM_SAINTS_ESPACE, DATE_NAISSANCE, Sexe.MASCULIN);
		assertThat(personne.getNomNormalise()).isNotNull().isEqualTo(NOM_SAINTS_ESPACE_NORMALISE);
	}
	
	@Test
	public void quandJobtiensLeNomNormaliseDunePersonneDontLeNomCommenceParSaintesSuiviDunTiret_alorsLeNomNormaliseContientSTSansTiret() {
		var personne = new Personne(PRENOM, NOM_SAINTES_TIRET, DATE_NAISSANCE, Sexe.MASCULIN);
		assertThat(personne.getNomNormalise()).isNotNull().isEqualTo(NOM_SAINTES_TIRET_NORMALISE);
	}
	
	@Test
	public void quandJobtiensLeNomNormaliseDunePersonneDontLeNomCommenceParSaintesSuiviDunEspace_alorsLeNomNormaliseContientSTSansEspace() {
		var personne = new Personne(PRENOM, NOM_SAINTES_ESPACE, DATE_NAISSANCE, Sexe.MASCULIN);
		assertThat(personne.getNomNormalise()).isNotNull().isEqualTo(NOM_SAINTES_ESPACE_NORMALISE);
	}
	
	@Test
	public void quandJobtiensLeNomNormaliseDunePersonneDontLeNomCommenceParSte_alorsLeNomNormaliseCommenceParSTE() {
		var personne = new Personne(PRENOM, NOM_COMMENCANT_PAR_STE, DATE_NAISSANCE, Sexe.MASCULIN);
		assertThat(personne.getNomNormalise()).isNotNull().isEqualTo(NOM_COMMENCANT_PAR_STE_NORMALISE);
	}
	
	@Test
	public void quandJobtiensLeNomNormaliseDunePersonneDontLeNomCommenceParSaint_alorsLeNomNormaliseCommenceParSAI() {
		var personne = new Personne(PRENOM, NOM_COMMENCANT_PAR_SAINT, DATE_NAISSANCE, Sexe.MASCULIN);
		assertThat(personne.getNomNormalise()).isNotNull().isEqualTo(NOM_COMMENCANT_PAR_SAINT_NORMALISE);
	}
	
	@Test
	public void quandJobtiensLeNomNormaliseDunePersonneDontLeNomCommenceParSainte_alorsLeNomNormaliseCommenceParSAI() {
		var personne = new Personne(PRENOM, NOM_COMMENCANT_PAR_SAINTE, DATE_NAISSANCE, Sexe.MASCULIN);
		assertThat(personne.getNomNormalise()).isNotNull().isEqualTo(NOM_COMMENCANT_PAR_SAINTE_NORMALISE);
	}
	
	@Test
	public void quandJobtiensLeNomNormaliseDunePersonneDontLeNomCommenceParSaints_alorsLeNomNormaliseCommenceParSAI() {
		var personne = new Personne(PRENOM, NOM_COMMENCANT_PAR_SAINTS, DATE_NAISSANCE, Sexe.MASCULIN);
		assertThat(personne.getNomNormalise()).isNotNull().isEqualTo(NOM_COMMENCANT_PAR_SAINTS_NORMALISE);
	}
	
	@Test
	public void quandJobtisnLeNomNormaliseDunePersonneDontLeNomCommenceParSaintes_alorsLeNomNormaliseCommenceParSAI() {
		var personne = new Personne(PRENOM, NOM_COMMENCANT_PAR_SAINTES, DATE_NAISSANCE, Sexe.MASCULIN);
		assertThat(personne.getNomNormalise()).isNotNull().isEqualTo(NOM_COMMENCANT_PAR_SAINTES_NORMALISE);
	}
}