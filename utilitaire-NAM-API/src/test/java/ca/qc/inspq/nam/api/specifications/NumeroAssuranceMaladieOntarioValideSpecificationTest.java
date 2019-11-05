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
public class NumeroAssuranceMaladieOntarioValideSpecificationTest {
	
	private static final String NAM_ONTARIO_VALIDE = "9401141925";
	private static final String NAM_ONTARIO_NON_VALIDE = "9401141926";
	
	@Autowired
	private NumeroAssuranceMaladieOntarioValideSpecification spec;
	
	@Test
	public void quandJeValideUnNamPourOntario_siLeNamEstValide_alorsJeRetourneVrai() throws UnsupportedEncodingException, ParseException {
		assertThat(spec.estSatisfaitePar(NAM_ONTARIO_VALIDE)).isTrue();
	}
	
	@Test
	public void quandJeValideUnNamPourOntario_siLeNamNEstPasValide_alorsJeRetourneFaux() throws UnsupportedEncodingException, ParseException{
		assertThat(spec.estSatisfaitePar(NAM_ONTARIO_NON_VALIDE)).isFalse();
	}

}
