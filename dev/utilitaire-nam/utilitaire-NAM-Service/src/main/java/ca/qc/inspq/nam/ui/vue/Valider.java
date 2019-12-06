package ca.qc.inspq.nam.ui.vue;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import ca.qc.inspq.nam.api.domaine.modele.Provinces;
import ca.qc.inspq.nam.api.service.ServiceNAM;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextField;

@SpringView(name = Valider.NOM_VUE)
@SuppressWarnings("serial")
public class Valider extends TabAbstrait {
    public static final String NOM_VUE = "valider";

    @Autowired
    private ServiceNAM serviceNAM;

    @Override
    public void init() {
        // Layout
        super.init();
        TextField txtNam = new TextField("NAM");
        ComboBox<Provinces> cbbProvince = new ComboBox<Provinces>("Province");
        cbbProvince.setItems(Provinces.values());
        cbbProvince.setItemCaptionGenerator(Provinces::getLibelle);
        cbbProvince.setValue(Provinces.QC);
        cbbProvince.setPageLength(Provinces.values().length + 1);

        super.conteneurFormulaire.addComponent(txtNam);
        super.conteneurFormulaire.addComponent(cbbProvince);

        super.boutonAction.setCaption("Valider");
        super.boutonAction.addClickListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                try {
                    String nam = txtNam.getValue();
                    Provinces province = (Provinces) cbbProvince.getValue();
                    boolean valide = false;
                    if (!StringUtils.isEmpty(nam) && province != null) {
                        valide = serviceNAM.valider(nam, province);
                        if (valide) {
                            afficherMessage("Le NAM est valide!", Niveau.INFO);
                        } else {
                            afficherMessage("Le NAM est invalide!", Niveau.AVERTISSEMENT);
                        }
                    } else {
                        afficherMessage("Vous devez saisir un NAM et sélectionner une province!", Niveau.AVERTISSEMENT);
                    }
                } catch (UnsupportedEncodingException | ParseException e) {
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
