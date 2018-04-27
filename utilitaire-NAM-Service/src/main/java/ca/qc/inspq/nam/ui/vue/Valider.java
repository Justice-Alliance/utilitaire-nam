package ca.qc.inspq.nam.ui.vue;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import ca.qc.inspq.nam.service.ServiceNAM;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextField;

@SpringView(name = Valider.NOM_VUE)
@SuppressWarnings("serial")
public class Valider extends TabAbstrait {
    public static final String NOM_VUE = "valider";

    @Autowired
    private ServiceNAM serviceNAM;
    
    public enum Provinces {
    	AB("Alberta"),
    	BC("Colombie-Britannique"),
    	PE("Île-du-Prince-Édouard"),
    	MB("Manitoba"),
    	NB("Nouveau-Brunswick"),
    	NS("Nouvelle-Écosse"),
    	NU("Nunavut"),
    	ON("Ontario"),
    	QC("Québec"),
    	SK("Saskatchewan"),
    	NL("Terre-Neuve"),
    	NT("Territoires du Nord-Ouest"),
    	YT("Yukon");
    	
    	public final String libelle;
    	
    	private Provinces(String libelle) {
    		this.libelle = libelle;
    	}
    	
    	public String getLibelle() {
    		return libelle;
    	}
    }

    @Override
    public void init() {
        // Layout
        super.init();
        TextField txtNam = new TextField("NAM");
        ComboBox cbbProvince = new ComboBox("Province");
        BeanItemContainer<Provinces> datasource = new BeanItemContainer<Provinces>(Provinces.class, Arrays.asList(Provinces.values()));
        cbbProvince.setContainerDataSource(datasource);
        cbbProvince.setItemCaptionPropertyId("libelle");
        cbbProvince.setValue(Provinces.QC);
        cbbProvince.setPageLength(datasource.size() + 1);

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
                        valide = serviceNAM.valider(nam, province.name());
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
