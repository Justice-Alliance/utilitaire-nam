package ca.qc.inspq.nam.service.spring;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vaadin.spring.annotation.SpringComponent;

import ca.qc.inspq.nam.api.domaine.ServiceUtilitairesNAM;
import ca.qc.inspq.nam.api.domaine.modele.NAMInfo;
import ca.qc.inspq.nam.api.domaine.modele.Personne;
import ca.qc.inspq.nam.api.domaine.modele.Provinces;
import ca.qc.inspq.nam.api.domaine.modele.Sexe;
import ca.qc.inspq.nam.api.service.ServiceNAM;

@RequestMapping("/nam")
@RestController
@SpringComponent
public class ServiceNAMSpring implements ServiceNAM {
	
	private String PATTERN_DATE ="yyyy-MM-dd";
    
    @Autowired
    private ServiceUtilitairesNAM utilitairesNAM;

    @Override
    @RequestMapping("/valider")
    public Boolean valider(@RequestParam(value="nam") String nam, @RequestParam(value="province", defaultValue="QC") String province) 
    		throws UnsupportedEncodingException, ParseException {
    	return utilitairesNAM.validerNAM(nam, Provinces.fromString(province));
    }

    @Override
    @RequestMapping("/generer")
    public Collection<String> generer(@RequestParam(value="prenom") String prenom,
            @RequestParam(value="nom") String nom,
            @RequestParam(value="datenaissance") String dateNaissance,
            @RequestParam(value="sexe") String sexe) throws UnsupportedEncodingException, ParseException {
    	var personne = new Personne(prenom, nom, LocalDate.parse(dateNaissance, DateTimeFormatter.ofPattern(PATTERN_DATE)), Sexe.fromString(sexe));
        return utilitairesNAM.obtenirCombinaisonsValidesDeNAM(personne);
    }

    @Override
    @RequestMapping("/info")
    public NAMInfo obtenirInfo(@RequestParam(value="nam") String nam) throws UnsupportedEncodingException, ParseException {
        return utilitairesNAM.obtenirInformationsContenuesDansLeNam(nam);
    }
}
