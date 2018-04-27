package ca.qc.inspq.nam.ui.vue;

import ca.qc.inspq.nam.modele.Sexe;
import ca.qc.inspq.nam.service.ServiceNAM;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;

@SpringView(name = Generer.NOM_VUE)
@SuppressWarnings("serial")
public class Generer extends TabAbstrait {
    public static final String NOM_VUE = "generer";

    @Autowired
    private ServiceNAM serviceNAM;

    @Override
    public void init() {
        // Layout
        super.init();
        TextField txtPrenom = new TextField("Prénom");
        TextField txtNom = new TextField("Nom");
        DateField dfDateNaissance = new DateField("Date de naissance");
        ComboBox cbbSexe = new ComboBox("Sexe");

        super.conteneurFormulaire.addComponent(txtPrenom);
        super.conteneurFormulaire.addComponent(txtNom);
        super.conteneurFormulaire.addComponent(dfDateNaissance);
        super.conteneurFormulaire.addComponent(cbbSexe);

        dfDateNaissance.setDateFormat("yyyy-MM-dd");
        cbbSexe.addItems((Object[]) Sexe.values());

        super.boutonAction.setCaption("Générer");
        super.boutonAction.addClickListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                try {
                    String prenom = txtPrenom.getValue();
                    String nom = txtNom.getValue();
                    Date dateNaissance = dfDateNaissance.getValue();
                    Sexe sexe = (Sexe) cbbSexe.getValue();

                    StringBuilder sb = new StringBuilder();
                    serviceNAM.generer(prenom, nom, ServiceNAM.FORMAT.format(dateNaissance), sexe.code)
                        .stream().forEach(nam -> {
                            sb.append(nam).append("\n");
                        });
                    if (sb.length() > 0) {
                        afficherMessage(sb.toString(), Niveau.INFO);
                    } else {
                        afficherMessage("Aucun NAM n'a pu être généré.\nVeuillez vérifier l'information fournie et recommencer.",
                                Niveau.AVERTISSEMENT);
                    }
                } catch (UnsupportedEncodingException | ParseException e) {
                    afficherMessage(
                            "Une erreur s'est produite lors de la validation des données fournies.\nVeuillez vérifier l'information et recommencer.",
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
