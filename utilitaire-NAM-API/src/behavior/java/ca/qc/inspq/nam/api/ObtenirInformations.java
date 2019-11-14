package ca.qc.inspq.nam.api;

public interface ObtenirInformations {
	void obtenirLesInformationsContenuesDansLeNam(String nam);
	
	void verifierQueLaDateDeNaissanceObtenueEst(String dateNaissanceAttendue);
	void verifierQueLeSexeObtenuEst(String sexeAttendu);
	void verifierQuunAvertissementEstRecu();
}
