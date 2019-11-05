package ca.qc.inspq.nam.api.specifications;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NumeroAssuranceMaladieNunavutValideSpecificationTest {
	
	private static final String NAM_NUNAVUT_VALIDE = "112345672";
	private static final String NAM_NUNAVUT_NON_VALIDE = "94011419";
	
	@Autowired
	private NumeroAssuranceMaladieNunavutValideSpecification spec;
	
	@Test
	public void quandJeValideUnNamPourNunavut_siLeNamEstValide_alorsJeRetourneVrai() throws UnsupportedEncodingException, ParseException {
		assertThat(spec.estSatisfaitePar(NAM_NUNAVUT_VALIDE)).isTrue();
	}
	
	@Test
	public void quandJeValideUnNamPourNunavut_siLeNamNEstPasValide_alorsJeRetourneFaux() throws UnsupportedEncodingException, ParseException{
		assertThat(spec.estSatisfaitePar(NAM_NUNAVUT_NON_VALIDE)).isFalse();
	}

}
