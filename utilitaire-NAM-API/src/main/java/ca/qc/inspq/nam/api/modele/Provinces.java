package ca.qc.inspq.nam.api.modele;

public enum Provinces {
	AB("Alberta"),
	BC("Colombie-Britannique"),
	PE("Île-du-Prince-Édouard"),
	MB("Manitoba"),
	NB("Nouveau-Brunswick"),
	NS("Nouvelle-Écosse"),
	NU("Nunavut"),
	ON("Ontario"),
	QC("Québec"),
	SK("Saskatchewan"),
	NL("Terre-Neuve"),
	NT("Territoires du Nord-Ouest"),
	YT("Yukon");
	
	public final String libelle;
	
	private Provinces(String libelle) {
		this.libelle = libelle;
	}
	
	public String getLibelle() {
		return libelle;
	}
}
