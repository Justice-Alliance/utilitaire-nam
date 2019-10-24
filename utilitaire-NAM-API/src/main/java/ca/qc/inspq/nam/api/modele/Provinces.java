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
	NL("Terre-Neuve-et-Labrador"),
	NT("Territoires du Nord-Ouest"),
	YT("Yukon");
	
	public final String libelle;
	
	private Provinces(String libelle) {
		this.libelle = libelle;
	}
	
	public String getLibelle() {
		return libelle;
	}
	
	public static Provinces fromString(String libelle) {
		for (Provinces province : Provinces.values()) {
			if (province.libelle.equalsIgnoreCase(libelle)) {
				return province;
			}
		}
		return null;
	}
}
