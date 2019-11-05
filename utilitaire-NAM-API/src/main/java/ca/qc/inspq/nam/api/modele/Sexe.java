package ca.qc.inspq.nam.api.modele;

public enum Sexe {
    MASCULIN("M"),
    FEMININ("F");

    public final String code;

    Sexe(String code) {
        this.code = code;
    }
    
    public String getCode() {
    	return this.code;
    }
    
    public static Sexe fromString(String libelle) {
		for (Sexe sexe : Sexe.values()) {
			if (sexe.code.equalsIgnoreCase(libelle)) {
				return sexe;
			}
		}
		return null;
	}
}
