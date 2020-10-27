package net.croware.showcase.ui.views.components;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;

import net.croware.showcase.app.util.ShowcaseConstants;

public class AppLogo extends VerticalLayout implements LocaleChangeObserver {

    private static final long serialVersionUID = ShowcaseConstants.SERIAL_VERSION_UID;

    private final Label appName;

    public AppLogo() {
        this.appName = new Label();
        this.appName.setClassName("label-24px");

        final HorizontalLayout imageWrap = getImageWrapper();

        setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, imageWrap);
        setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, this.appName);

        add(imageWrap);
        add(this.appName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void localeChange(LocaleChangeEvent event) {
        this.appName.setText(getTranslation("label.appname"));
    }

    private HorizontalLayout getImageWrapper() {
        final Image image = new Image();
        image.setClassName("appLogo");

        final HorizontalLayout layout = new HorizontalLayout();
        layout.add(image);
        layout.setPadding(false);
        return layout;
    }

}
