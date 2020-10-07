package ca.qc.inspq.nam.api.service;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import ca.qc.inspq.nam.api.domaine.modele.NAMInfo;
import ca.qc.inspq.nam.api.domaine.modele.Provinces;

@Path("/nam")
@Produces({ MediaType.APPLICATION_JSON })
public interface ServiceNAM {
    @Path("/valider")
    @GET
    public Boolean valider(@QueryParam("nam") String nam, @QueryParam("province") Provinces province) 
    		throws UnsupportedEncodingException, ParseException;

    @Path("/generer")
    @GET
    public Collection<String> generer(@QueryParam("prenom") String prenom,
            @QueryParam("nom") String nom,
            @QueryParam("datenaissance") String dateNaissance,
            @QueryParam("sexe") String sexe) throws UnsupportedEncodingException, ParseException;

    @Path("/info")
    @GET
    public NAMInfo obtenirInfo(@QueryParam("nam") String nam) throws UnsupportedEncodingException, ParseException;
}
