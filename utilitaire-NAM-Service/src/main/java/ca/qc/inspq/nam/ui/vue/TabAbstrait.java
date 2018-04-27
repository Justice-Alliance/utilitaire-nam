package ca.qc.inspq.nam.ui.vue;

import javax.annotation.PostConstruct;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public abstract class TabAbstrait extends VerticalLayout implements View {
    protected enum Niveau {
        INFO("info"),
        AVERTISSEMENT("avertissement"),
        ERREUR("erreur");

        public final String style;

        private Niveau(String style) {
            this.style = style;
        }
    }

    protected Layout conteneurFormulaire = new FormLayout();
    protected Button boutonAction = new Button();
    private Panel conteneurResultat = new Panel();

    @PostConstruct
    public void init() {
        // Layout
        addComponent(conteneurFormulaire);
        addComponent(boutonAction);
        addComponent(conteneurResultat);

        // Apparence
        this.setSizeFull();
        this.setSpacing(true);
        this.setMargin(true);
        conteneurResultat.setHeight(100f, Unit.PERCENTAGE);
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }

    protected void afficherMessage (String message, Niveau niveau) {
        TextArea txtMessage = new TextArea();
        conteneurResultat.setContent(txtMessage);
        txtMessage.setValue(message);
        txtMessage.setSizeFull();
        txtMessage.setStyleName(niveau.style);
        txtMessage.setReadOnly(true);
    }
}
