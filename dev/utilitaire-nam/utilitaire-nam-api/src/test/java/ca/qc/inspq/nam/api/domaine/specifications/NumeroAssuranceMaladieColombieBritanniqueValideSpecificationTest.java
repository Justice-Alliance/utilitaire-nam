package ca.qc.inspq.nam.api.domaine.specifications;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.qc.inspq.nam.api.domaine.specifications.NumeroAssuranceMaladieColombieBritanniqueValideSpecification;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NumeroAssuranceMaladieColombieBritanniqueValideSpecificationTest {
	
	private static final String NAM_COLOMBIE_BRITANNIQUE_VALIDE = "9759528158";
	private static final String NAM_COLOMBIE_BRITANNIQUE_NON_VALIDE = "9759528153";
	
	@Autowired
	private NumeroAssuranceMaladieColombieBritanniqueValideSpecification spec;
	
	@Test
	public void quandJeVerifieSiUnNamRespecteLaSpecificationDeNamValideDeColombieBritannique_siLeNamEstValide_alorsJeRetourneVrai() throws UnsupportedEncodingException, ParseException {
		assertThat(spec.estSatisfaitePar(NAM_COLOMBIE_BRITANNIQUE_VALIDE)).isTrue();
	}
	
	@Test
	public void quandJeVerifieSiUnNamRespecteLaSpecificationDeNamValideDeColombieBritannique_siLeNamEstInvalide_alorsJeRetourneFaux() throws UnsupportedEncodingException, ParseException {
		assertThat(spec.estSatisfaitePar(NAM_COLOMBIE_BRITANNIQUE_NON_VALIDE)).isFalse();
	}

}
