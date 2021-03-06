package ca.qc.inspq.nam.api.domaine.specifications;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.qc.inspq.nam.api.domaine.specifications.NumeroAssuranceMaladieTerritoiresDuNordOuestValideSpecification;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NumeroAssuranceMaladieTerritoiresDuNordOuestValideSpecificationTest {
	
	private static final String NAM_TERRITOIRES_DU_NORD_OUEST_VALIDE = "9401141";
	private static final String NAM_TERRITOIRES_DU_NORD_OUEST_NON_VALIDE = "9401141023365";
	
	@Autowired
	private NumeroAssuranceMaladieTerritoiresDuNordOuestValideSpecification spec;
	
	@Test
	public void quandJeVerifieSiUnNamRespecteLaSpecificationDeNamValideDeTerritoiresDuNordOuest_siLeNamEstValide_alorsJeRetourneVrai() throws UnsupportedEncodingException, ParseException {
		assertThat(spec.estSatisfaitePar(NAM_TERRITOIRES_DU_NORD_OUEST_VALIDE)).isTrue();
	}
	
	@Test
	public void quandJeVerifieSiUnNamRespecteLaSpecificationDeNamValideDeTerritoiresDuNordOuest_siLeNamNEstPasValide_alorsJeRetourneFaux() throws UnsupportedEncodingException, ParseException {
		assertThat(spec.estSatisfaitePar(NAM_TERRITOIRES_DU_NORD_OUEST_NON_VALIDE)).isFalse();
	}

}
