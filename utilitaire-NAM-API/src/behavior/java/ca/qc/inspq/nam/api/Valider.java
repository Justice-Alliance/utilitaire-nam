package ca.qc.inspq.nam.api;

public interface Valider {

	void validerLeNumeroDassuranceMaladieDuneProvince(String nam, String province);
	
	void verifierQuuneConfirmationQueNamValideEstRecue();
	void verifierQuunAvertissementEstRecu();
}
