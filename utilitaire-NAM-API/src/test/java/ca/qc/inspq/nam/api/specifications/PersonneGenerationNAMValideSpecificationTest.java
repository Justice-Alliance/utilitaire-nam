package ca.qc.inspq.nam.api.specifications;

import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.time.LocalDate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.qc.inspq.nam.api.modele.Personne;
import ca.qc.inspq.nam.api.modele.Sexe;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonneGenerationNAMValideSpecificationTest {
	
	private static final String PRENOM = "Martin";
	private static final String NOM = "Tremblay";
	private static final LocalDate DATE_NAISSANCE = LocalDate.of(2004, 12, 19);
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Autowired
	private PersonneGenerationNAMValideSpecification spec;
	
	@Test
	public void quandJeVerifiesSiUnePersonneRespecteLaSpecificationPourGenererDesNAMS_siToutesLesInformationsDeLaPersonneSontValides_aLorsJeRetourneRien() 
			throws UnsupportedEncodingException, ParseException {
		var personne = new Personne(PRENOM, NOM, DATE_NAISSANCE, Sexe.MASCULIN);
		spec.estSatisfaitePar(personne);
	}
	
	
	@Test
	public void quandJeVerifieSiUnePersonneRespecteLaSpecificatitionPourGenererDesNAMS_siLePrenomDeLaPersonneNestPasValide_alorsJeRetourneInvalidParameterException() 
			throws UnsupportedEncodingException, ParseException  {
		exception.expect(InvalidParameterException.class);
		var personne = new Personne(null, NOM, DATE_NAISSANCE, Sexe.MASCULIN);
		spec.estSatisfaitePar(personne);
	}
	
	@Test
	public void quandJeVerifieSiUnePersonneRespecteLaSpecificationPourGenererDesNAMS_siLeNomDeLaPersonneNestPasValide_alorsJeRetourneInvalidParameterException() 
			throws UnsupportedEncodingException, ParseException{
		exception.expect(InvalidParameterException.class);
		var personne = new Personne(PRENOM, null, DATE_NAISSANCE, Sexe.MASCULIN);
		spec.estSatisfaitePar(personne);
	}
	
	@Test
	public void quandJeVerifieSiUnePersonneRespecteLaSpecificationPourGenererDesNAMS_siLaDateDeNaissanceNestPasValide_alorsJeRetourneInvalidParameterException() 
			throws UnsupportedEncodingException, ParseException {
		exception.expect(InvalidParameterException.class);
		var personne = new Personne(PRENOM, NOM, null, Sexe.MASCULIN);
		spec.estSatisfaitePar(personne);
	}

	@Test
	public void quandJeVerifieSiUnePersonneRespecteLaSpecificationPourGenererDesNAMS_siLeSexeNestPasValide_alorsJeRetourneInvalidParameterException() 
			throws UnsupportedEncodingException, ParseException {
		exception.expect(InvalidParameterException.class);
		var personne = new Personne(PRENOM, NOM, DATE_NAISSANCE, null);
		spec.estSatisfaitePar(personne);
	}
}
