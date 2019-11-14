package ca.qc.inspq.nam.api;

import java.util.List;

import ca.qc.inspq.nam.api.domaine.modele.Personne;

public interface Generer {

	void genererUneListeDeNumerosDassuranceMaladiePour(Personne personne);
	
	void verifierQueLesNumerosDassuranceMaladieGeneresSont(List<String> namsGeneresAttendus);
	void verifierQuunAvertissementEstRecu();
}
