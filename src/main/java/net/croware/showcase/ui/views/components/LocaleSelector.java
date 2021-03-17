package net.croware.showcase.ui.views.components;

import java.util.Locale;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import net.croware.showcase.app.util.ShowcaseConstants;

/**
 * This is the layout for the locale selector.
 *
 * @author christian
 *
 */
@CssImport(ShowcaseConstants.CSS_SHARED_FILE)
public class LocaleSelector extends HorizontalLayout {

    private static final long serialVersionUID = ShowcaseConstants.SERIAL_VERSION_UID;

    public LocaleSelector() {
        final HorizontalLayout germFlagWrap = getImageWrapper(Locale.GERMANY, "flagGermanyIcon");
        final HorizontalLayout usFlagWrap = getImageWrapper(Locale.US, "flagUsIcon");

        setPadding(false);

        add(germFlagWrap);
        add(usFlagWrap);
    }

    private HorizontalLayout getImageWrapper(Locale locale, String cssClassName) {
        final Image image = new Image();
        image.addClickListener(e -> UI.getCurrent().setLocale(locale));
        image.setClassName(cssClassName);

        final HorizontalLayout layout = new HorizontalLayout();
        layout.setPadding(false);
        layout.add(image);
        return layout;
    }

}
