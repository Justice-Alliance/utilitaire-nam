package ca.qc.inspq.nam.service.spring;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.qc.inspq.nam.modele.NAMInfo;
import ca.qc.inspq.nam.service.ServiceNAM;
import ca.qc.inspq.nam.utilitaire.UtilitairesNAM;

import com.vaadin.spring.annotation.SpringComponent;

@RequestMapping("/nam")
@RestController
@SpringComponent
public class ServiceNAMSpring implements ServiceNAM {
    public static final DateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    @RequestMapping("/valider")
    public Boolean valider(@RequestParam(value="nam") String nam, @RequestParam(value="province", defaultValue="QC") String province) 
    		throws UnsupportedEncodingException, ParseException {
    	return UtilitairesNAM.validerNAM(nam, province);
    }

    @Override
    @RequestMapping("/generer")
    public Collection<String> generer(@RequestParam(value="prenom") String prenom,
            @RequestParam(value="nom") String nom,
            @RequestParam(value="datenaissance") String dateNaissance,
            @RequestParam(value="sexe") String sexe) throws UnsupportedEncodingException, ParseException {
        return UtilitairesNAM.obtenirCombinaisonsValidesDeNAM(prenom, nom, FORMAT.parse(dateNaissance), sexe);
    }

    @Override
    @RequestMapping("/info")
    public NAMInfo obtenirInfo(@RequestParam(value="nam") String nam) throws UnsupportedEncodingException, ParseException {
        return new NAMInfo(FORMAT.format(UtilitairesNAM.trouverDateNaissance(nam)), UtilitairesNAM.obtenirSexe(nam));
    }
}
