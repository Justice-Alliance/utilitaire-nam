package ca.qc.inspq.nam.ui;

import ca.qc.inspq.nam.ui.vue.Entete;
import ca.qc.inspq.nam.ui.vue.Generer;
import ca.qc.inspq.nam.ui.vue.ObtenirInfo;
import ca.qc.inspq.nam.ui.vue.PiedDePage;
import ca.qc.inspq.nam.ui.vue.Valider;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Viewport;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.PushStateNavigation;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@SpringUI(path="/ui")
@Viewport("user-scalable=no,initial-scale=1.0")
@Theme("inspq")
@PushStateNavigation
public class UtilitaireNAMUI extends UI {
    public static final String APP_TITLE = "Utilitaire NAM";
    public static final String LANG_SESSION_KEY = "client.lang";
    public static final Locale DEFAULT_LOCALE = Locale.CANADA_FRENCH;
    private static final String VUE_PAR_DEFAUT = Valider.NOM_VUE;

    @Autowired
    private SpringViewProvider viewProvider;

    @Override
    protected void init(VaadinRequest request) {
        this.getPage().setTitle(APP_TITLE);
        String lang = request.getParameter(LANG_SESSION_KEY);
        if (lang == null) {
            lang = (String) getSession().getAttribute(LANG_SESSION_KEY);
        }
        if (lang == null) {
            lang = DEFAULT_LOCALE.toString();
        }

        setDefaultLocale(lang);

        final VerticalLayout racine = new VerticalLayout();
        racine.setSizeFull();
        racine.setMargin(true);
        racine.setSpacing(true);
        setContent(racine);

        racine.addComponent(new Entete());
        final CssLayout barreNavigation = new CssLayout();
        barreNavigation.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        barreNavigation.addComponent(creerBoutonNavigation("Valider", Valider.NOM_VUE));
        barreNavigation.addComponent(creerBoutonNavigation("Générer NAM", Generer.NOM_VUE));
        barreNavigation.addComponent(creerBoutonNavigation("Obtenir information sur le NAM", ObtenirInfo.NOM_VUE));
        racine.addComponent(barreNavigation);

        final Panel conteneurVue = new Panel();
        conteneurVue.setSizeFull();
        racine.addComponent(conteneurVue);
        racine.setExpandRatio(conteneurVue, 1.0f);

        racine.addComponent(new PiedDePage());

        Navigator navigator = new Navigator(this, conteneurVue);
        navigator.addProvider(viewProvider);

        getUI().getNavigator().navigateTo(VUE_PAR_DEFAUT);
    }
    
    private Component creerBoutonNavigation(String libelle, String nomVue) {
        Button button = new Button(libelle);
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        button.addClickListener(event -> {
            getUI().getNavigator().navigateTo(nomVue);
        });
        return button;
    }

    /**
     * Détermine la {@link Locale} pour l'application
     * 
     * @param lang Identifiant de la locale à utiliser (défaut fr_ca)
     */
    public void setDefaultLocale(String lang) {
        Locale locale = DEFAULT_LOCALE;
        if (lang != null) {
            locale = new Locale(lang.toLowerCase());
        }
        setLocale(locale);
        VaadinSession.getCurrent().setLocale(locale);
    }
}
