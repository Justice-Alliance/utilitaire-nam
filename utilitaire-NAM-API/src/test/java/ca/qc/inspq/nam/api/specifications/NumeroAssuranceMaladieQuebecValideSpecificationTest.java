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
public class NumeroAssuranceMaladieQuebecValideSpecificationTest {
	
	private static final String NAM_QUEBEC_VALIDE = "TREM04121925";
	private static final String NAM_QUEBEC_NON_VALIDE = "TREM04121935";
	
	@Autowired
	private NumeroAssuranceMaladieQuebecValideSpecification spec;

	@Test
	public void quandJeVerifieSiUnNamRespecteLaSpecificationDeNamValideDuQuebec_siLeNamEstValide_alorsJeRetourneVrai() throws UnsupportedEncodingException, ParseException {
		assertThat(spec.estSatisfaitePar(NAM_QUEBEC_VALIDE)).isTrue();
	}
	
	@Test
	public void quandJeVerifieSiUnNamRespecteLaSpecificationDeNamValideDuQuebec_siLeNamNestPasValide_alorsJeRetourneFaux() throws UnsupportedEncodingException, ParseException {
		assertThat(spec.estSatisfaitePar(NAM_QUEBEC_NON_VALIDE)).isFalse();
	}
}
