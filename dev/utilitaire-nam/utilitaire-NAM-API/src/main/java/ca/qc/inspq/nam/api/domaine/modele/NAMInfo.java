package ca.qc.inspq.nam.api.domaine.modele;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class NAMInfo {

    private final LocalDate dateNaissance;
    private final Sexe sexe;

    public NAMInfo(LocalDate dateNaissance, Sexe sexe) {
        this.dateNaissance = dateNaissance;
        this.sexe = sexe;
    }
}
