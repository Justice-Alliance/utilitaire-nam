package ca.qc.inspq.nam.api.domaine.specifications;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.qc.inspq.nam.api.domaine.specifications.NumeroAssuranceMaladieTerreNeuveEtLabradorValideSpecification;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NumeroAssuranceMaladieTerreNeuveEtLabradorValideSpecificationTest {
	
	private static final String NAM_TERRE_NEUVE_ET_LABRADOR_VALIDE = "940114192698";
	private static final String NAM_TERRE_NEUVE_ET_LABRADOR_NON_VALIDE = "940114192698012";
	
	@Autowired
	private NumeroAssuranceMaladieTerreNeuveEtLabradorValideSpecification spec;
	
	@Test
	public void quandJeValideUnNamPourTerreNeuveEtLabrador_siLeNamEstValide_alorsJeRetourneVrai() throws UnsupportedEncodingException, ParseException {
		assertThat(spec.estSatisfaitePar(NAM_TERRE_NEUVE_ET_LABRADOR_VALIDE)).isTrue();
	}
	
	@Test
	public void quandJeValideUnNamPourTerreNeuveEtLabrador_siLeNamNEstPasValide_alorsJeRetourneFaux() throws UnsupportedEncodingException, ParseException{
		assertThat(spec.estSatisfaitePar(NAM_TERRE_NEUVE_ET_LABRADOR_NON_VALIDE)).isFalse();
	}

}
