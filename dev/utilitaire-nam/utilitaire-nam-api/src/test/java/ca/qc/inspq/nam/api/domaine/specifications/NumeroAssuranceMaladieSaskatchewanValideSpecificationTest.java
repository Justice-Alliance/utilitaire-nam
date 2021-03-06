package ca.qc.inspq.nam.api.domaine.specifications;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.qc.inspq.nam.api.domaine.specifications.NumeroAssuranceMaladieSaskatchewanValideSpecification;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NumeroAssuranceMaladieSaskatchewanValideSpecificationTest {
	
	private static final String NAM_SASKATCHEWAN_VALIDE = "940114195";
	private static final String NAM_SASKATCHEWAN_NON_VALIDE = "9401141958545";
	
	@Autowired
	private NumeroAssuranceMaladieSaskatchewanValideSpecification spec;
	
	@Test
	public void quandJeValideUnNamPourSaskatchewan_siLeNamEstValide_alorsJeRetourneVrai() throws UnsupportedEncodingException, ParseException {
		assertThat(spec.estSatisfaitePar(NAM_SASKATCHEWAN_VALIDE)).isTrue();
	}
	
	@Test
	public void quandJeValideUnNamPourSaskatchewan_siLeNamNEstPasValide_alorsJeRetourneFaux() throws UnsupportedEncodingException, ParseException{
		assertThat(spec.estSatisfaitePar(NAM_SASKATCHEWAN_NON_VALIDE)).isFalse();
	}
	
}
