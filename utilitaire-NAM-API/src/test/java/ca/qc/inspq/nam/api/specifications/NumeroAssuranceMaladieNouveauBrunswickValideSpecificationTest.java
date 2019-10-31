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
public class NumeroAssuranceMaladieNouveauBrunswickValideSpecificationTest {
	
	private static final String NAM_NOUVEAU_BRUNSWICK_VALIDE 		= "940114192";
	private static final String NAM_NOUVEAU_BRUNSWICK_NON_VALIDE 	= "9401141";
	
	@Autowired
	private NumeroAssuranceMaladieNouveauBrunswickValideSpecification spec;
	
	@Test
	public void quandJeValideUnNamPourLeNouveauBrunswick_siLeNamEstValide_alorsJeRetourneVrai() throws UnsupportedEncodingException, ParseException {
		assertThat(spec.estSatisfaitePar(NAM_NOUVEAU_BRUNSWICK_VALIDE)).isTrue();
	}
	
	@Test
	public void quandJeValideUnNamPourLeNouveauBrunswick_siLeNamNEstPasValide_alorsJeRetourneFaux() throws UnsupportedEncodingException, ParseException{
		assertThat(spec.estSatisfaitePar(NAM_NOUVEAU_BRUNSWICK_NON_VALIDE)).isFalse();
	}

}
