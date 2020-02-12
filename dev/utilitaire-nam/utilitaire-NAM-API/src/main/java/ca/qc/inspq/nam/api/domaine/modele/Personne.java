package ca.qc.inspq.nam.api.domaine.modele;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Personne {
	
	private final String prenom;
	private final String nom;
	
	@Getter
	private final LocalDate dateNaissance;
	
	@Getter
	private final Sexe sexe;
	
	public String getPrenomNormalise() {
		return normaliserRAMQ(prenom);
	}
	
	public String getNomNormalise() {
		String nomTransforme = transformerNom(nom);
		return normaliserRAMQ(nomTransforme);
	}	
	
	private String normaliserRAMQ(String text) {
        if (text == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer(text.toLowerCase());
        sb = replaceAll(sb, "[áàâä]", "a");
        sb = replaceAll(sb, "[éèêë]", "e");
        sb = replaceAll(sb, "[íìîï]", "i");
        sb = replaceAll(sb, "[óòôö]", "o");
        sb = replaceAll(sb, "[úùûü]", "u");
        sb = replaceAll(sb, "[ç]", "c");
        sb = replaceAll(sb, "[ñ]", "n");
        sb = replaceAll(sb, "[^0-9a-z%]", "");
        return sb.toString().toUpperCase();
    }
	
	private StringBuffer replaceAll(StringBuffer sb, String patron, String remplacant) {

        Pattern p = Pattern.compile(patron);
        Matcher m = p.matcher(sb);
        StringBuffer sbOut = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sbOut, remplacant);
        }
        m.appendTail(sbOut);

        return sbOut;
    }
	
	private String transformerNom(String texte) {
		try {
			String texteTransforme = texte.toUpperCase().replace("STE ", "ST").replace("STE-", "ST").replace("SAINT ", "ST").replace("SAINT-", "ST")
					.replace("SAINTE ", "ST").replace("SAINTE-", "ST");
			return texteTransforme;
		}
		catch (NullPointerException e) {
			return null;
		}
	}
}