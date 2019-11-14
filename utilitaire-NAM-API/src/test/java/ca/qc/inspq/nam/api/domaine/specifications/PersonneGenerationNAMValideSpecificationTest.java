package ca.qc.inspq.nam.api.domaine.specifications;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.qc.inspq.nam.api.domaine.modele.Personne;
import ca.qc.inspq.nam.api.domaine.modele.Sexe;
import ca.qc.inspq.nam.api.domaine.specifications.PersonneGenerationNAMValideSpecification;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonneGenerationNAMValideSpecificationTest {
	
	private static final String PRENOM = "Martin";
	private static final String NOM = "Tremblay";
	private static final LocalDate DATE_NAISSANCE = LocalDate.of(2004, 12, 19);
	
	@Autowired
	private PersonneGenerationNAMValideSpecification spec;
	
	@Test
	public void quandJeVerifiesSiUnePersonneRespecteLaSpecificationPourGenererDesNAMS_siToutesLesInformationsDeLaPersonneSontValides_aLorsJeRetourneRien() 
			throws UnsupportedEncodingException, ParseException {
		var personne = new Personne(PRENOM, NOM, DATE_NAISSANCE, Sexe.MASCULIN);
		assertThat(spec.estSatisfaitePar(personne)).isTrue();
	}
	
	
	@Test
	public void quandJeVerifieSiUnePersonneRespecteLaSpecificatitionPourGenererDesNAMS_siLePrenomDeLaPersonneNestPasValide_alorsJeRetourneInvalidParameterException() 
			throws UnsupportedEncodingException, ParseException  {
		var personne = new Personne(null, NOM, DATE_NAISSANCE, Sexe.MASCULIN);
		assertThat(spec.estSatisfaitePar(personne)).isFalse();
	}
	
	@Test
	public void quandJeVerifieSiUnePersonneRespecteLaSpecificationPourGenererDesNAMS_siLeNomDeLaPersonneNestPasValide_alorsJeRetourneInvalidParameterException() 
			throws UnsupportedEncodingException, ParseException{
		var personne = new Personne(PRENOM, null, DATE_NAISSANCE, Sexe.MASCULIN);
		assertThat(spec.estSatisfaitePar(personne)).isFalse();
	}
	
	@Test
	public void quandJeVerifieSiUnePersonneRespecteLaSpecificationPourGenererDesNAMS_siLaDateDeNaissanceNestPasValide_alorsJeRetourneInvalidParameterException() 
			throws UnsupportedEncodingException, ParseException {
		var personne = new Personne(PRENOM, NOM, null, Sexe.MASCULIN);
		assertThat(spec.estSatisfaitePar(personne)).isFalse();
	}

	@Test
	public void quandJeVerifieSiUnePersonneRespecteLaSpecificationPourGenererDesNAMS_siLeSexeNestPasValide_alorsJeRetourneInvalidParameterException() 
			throws UnsupportedEncodingException, ParseException {
		var personne = new Personne(PRENOM, NOM, DATE_NAISSANCE, null);
		assertThat(spec.estSatisfaitePar(personne)).isFalse();
	}
}
