package ca.qc.inspq.nam.api.domaine.specifications;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.qc.inspq.nam.api.domaine.specifications.NumeroAssuranceMaladieNouvelleEcosseValideSpecification;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NumeroAssuranceMaladieNouvelleEcosseValideSpecificationTest {
	
	private static final String NAM_NOUVELLE_ECOSSE_VALIDE = "9401141923";
	private static final String NAM_NOUVELLE_ECOSSE_NON_VALIDE = "9401141926545";
	
	@Autowired
	private NumeroAssuranceMaladieNouvelleEcosseValideSpecification spec;
	
	@Test
	public void quandJeValideUnNamPourLaNouvelleEcosse_siLeNamEstValide_alorsJeRetourneVrai() throws UnsupportedEncodingException, ParseException {
		assertThat(spec.estSatisfaitePar(NAM_NOUVELLE_ECOSSE_VALIDE)).isTrue();
	}
	
	@Test
	public void quandJeValideUnNamPourLaNouvelleEcosse_siLeNamNEstPasValide_alorsJeRetourneFaux() throws UnsupportedEncodingException, ParseException{
		assertThat(spec.estSatisfaitePar(NAM_NOUVELLE_ECOSSE_NON_VALIDE)).isFalse();
	}

}
