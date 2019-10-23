package ca.qc.inspq.nam.api.modele;


public class NAMInfo {

    public final String dateNaissance;
    public final Sexe sexe;

    public NAMInfo(String dateNaissance, Sexe sexe) {
        this.dateNaissance = dateNaissance;
        this.sexe = sexe;
    }
}
