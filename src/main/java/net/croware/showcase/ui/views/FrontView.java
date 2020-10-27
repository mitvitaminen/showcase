package net.croware.showcase.ui.views;

import org.springframework.security.access.annotation.Secured;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.router.Route;

import net.croware.showcase.app.util.ShowcaseConstants;

/**
 * This is the layout for the front of the app.
 *
 * @author christian
 *
 */
@Tag("items-view")
@Route(value = ShowcaseConstants.ROUTERPATH_FRONTPAGE, layout = MainLayout.class)
@CssImport(ShowcaseConstants.CSS_SHARED_FILE)
@Secured("USER")
public class FrontView extends VerticalLayout implements LocaleChangeObserver {
    private static final long serialVersionUID = ShowcaseConstants.SERIAL_VERSION_UID;

    private final Label lab1;
    private final Label lab2;
    private final Paragraph par1;
    private final Paragraph par2;

    public FrontView() {
        this.lab1 = new Label();
        this.lab2 = new Label();
        this.par1 = new Paragraph();
        this.par2 = new Paragraph();

        final HorizontalLayout upperLayout = new HorizontalLayout();
        upperLayout.add(this.lab1, this.par1);
        upperLayout.setPadding(true);

        final HorizontalLayout lowerLayout = new HorizontalLayout();
        lowerLayout.add(this.lab2, this.par2);
        lowerLayout.setPadding(true);

        add(upperLayout, lowerLayout);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void localeChange(LocaleChangeEvent arg0) {
        this.lab1.setText(getTranslation("dummy.one"));
        this.par1.setText(getTranslation("dummy.paragraph.one"));
        this.lab2.setText(getTranslation("dummy.two"));
        this.par2.setText(getTranslation("dummy.paragraph.two"));
    }

}
