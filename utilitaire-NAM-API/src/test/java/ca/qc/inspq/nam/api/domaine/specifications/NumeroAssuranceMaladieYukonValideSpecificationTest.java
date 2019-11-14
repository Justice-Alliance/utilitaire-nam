package ca.qc.inspq.nam.api.domaine.specifications;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.qc.inspq.nam.api.domaine.specifications.NumeroAssuranceMaladieYukonValideSpecification;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NumeroAssuranceMaladieYukonValideSpecificationTest {
	
	private static final String NAM_YUKON_VALIDE = "940114195";
	private static final String NAM_YUKON_NON_VALIDE = "94011419503256"; 
	
	@Autowired
	private NumeroAssuranceMaladieYukonValideSpecification spec;
	
	@Test
	public void quandJeValideUnNamPourYukon_siLeNamEstValide_alorsJeRetourneVrai() throws UnsupportedEncodingException, ParseException {
		assertThat(spec.estSatisfaitePar(NAM_YUKON_VALIDE)).isTrue();
	}
	
	@Test
	public void quandJeValideUnNamPourYukon_siLeNamNEstPasValide_alorsJeRetourneFaux() throws UnsupportedEncodingException, ParseException{
		assertThat(spec.estSatisfaitePar(NAM_YUKON_NON_VALIDE)).isFalse();
	}

}
