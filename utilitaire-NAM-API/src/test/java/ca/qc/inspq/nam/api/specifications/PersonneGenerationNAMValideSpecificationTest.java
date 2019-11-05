package ca.qc.inspq.nam.api.specifications;

import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;
import java.text.ParseException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.qc.inspq.nam.api.modele.Personne;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonneGenerationNAMValideSpecificationTest {
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Autowired
	private PersonneGenerationNAMValideSpecification spec;
	
	@Test
	public void quandJeVerifieSiUnePersonneRespecteLaSpecificatitionPourGenererDesNAMS_siLaPersonneNestPasValide_alorsJeRetourneInvalidParameterException() 
			throws UnsupportedEncodingException, ParseException  {
		exception.expect(InvalidParameterException.class);
		var personne = new Personne(null, null, null, null);
		spec.estSatisfaitePar(personne);
	}

}
