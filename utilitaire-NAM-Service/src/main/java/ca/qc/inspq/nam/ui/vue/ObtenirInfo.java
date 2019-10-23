package ca.qc.inspq.nam.ui.vue;

import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import ca.qc.inspq.nam.api.modele.NAMInfo;
import ca.qc.inspq.nam.api.service.ServiceNAM;

import com.vaadin.ui.TextField;

@SpringView(name = ObtenirInfo.NOM_VUE)
@SuppressWarnings("serial")
public class ObtenirInfo extends TabAbstrait {
    public static final String NOM_VUE = "info";

    @Autowired
    private ServiceNAM serviceNAM;

    @Override
    public void init() {
        // Layout
        super.init();
        TextField txtNam = new TextField("NAM");

        super.conteneurFormulaire.addComponent(txtNam);

        super.boutonAction.setCaption("Obtenir l'information");
        super.boutonAction.addClickListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                try {
                    String nam = txtNam.getValue();
                    if (!StringUtils.isEmpty(nam)) {
                        NAMInfo namInfo = serviceNAM.obtenirInfo(nam);
                        afficherMessage(
                                String.format("Date de naissance: %s\nSexe: %s", namInfo.dateNaissance, namInfo.sexe),
                                Niveau.INFO);
                    } else {
                        afficherMessage("Vous devez saisir un NAM!", Niveau.AVERTISSEMENT);
                    }
                } catch (UnsupportedEncodingException | ParseException | InvalidParameterException e) {
                    afficherMessage(
                            "Une erreur s'est produite lors de la validation du NAM.\nVeuillez vérifier le NAM fourni et recommencer.",
                            Niveau.AVERTISSEMENT);
                } catch (Exception e) {
                    afficherMessage(
                            "Une erreur inconnue s'est produite avec Utilitaire NAM.\nVeuillez contacter l'administrateur.",
                            Niveau.ERREUR);
                    e.printStackTrace();
                }
            }
        });
    }
}
