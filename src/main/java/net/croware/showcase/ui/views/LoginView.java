package net.croware.showcase.ui.views;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import net.croware.showcase.app.security.SecurityUtils;
import net.croware.showcase.app.util.ShowcaseConstants;
import net.croware.showcase.ui.views.components.AppLogo;
import net.croware.showcase.ui.views.components.LocaleSelector;

/**
 * The layout for the login page.
 *
 * @author christian
 *
 */

@Route("")
@Tag("login-view")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@CssImport(ShowcaseConstants.CSS_SHARED_FILE)
public class LoginView extends VerticalLayout
        implements AfterNavigationObserver, BeforeEnterObserver, LocaleChangeObserver {

    private static final long serialVersionUID = ShowcaseConstants.SERIAL_VERSION_UID;

    private final LoginForm loginForm;

    public LoginView() {
        this.loginForm = new LoginForm();
        this.loginForm.setAction(ShowcaseConstants.ROUTERPATH_LOGIN);

        final HorizontalLayout centeredContent = getCenteredContent();

        final VerticalLayout body = new VerticalLayout();
        body.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, centeredContent);
        body.setPadding(false);
        body.setSpacing(false);
        body.add(centeredContent);

        add(body);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        this.loginForm.setError(event.getLocation().getQueryParameters().getParameters().containsKey("error"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (SecurityUtils.isUserLoggedIn() && SecurityUtils.isAccessGranted(event.getNavigationTarget())) {
            event.forwardTo(ShowcaseConstants.ROUTERPATH_FRONTPAGE);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void localeChange(LocaleChangeEvent event) {
        UI.getCurrent().getPage().setTitle(getTranslation("label.appname", event.getLocale()));

        final LoginI18n i18n = LoginI18n.createDefault();
        i18n.setHeader(new LoginI18n.Header());
//        i18n.getHeader().setTitle(getTranslation("label.appname", event.getLocale()));
        i18n.getHeader().setDescription(getTranslation("label.login.description", event.getLocale()));
        i18n.getForm().setUsername(getTranslation("label.login.username", event.getLocale()));
        i18n.getForm().setPassword(getTranslation("label.login.password", event.getLocale()));
        i18n.getForm().setSubmit(getTranslation("label.btn.submit", event.getLocale()));
//        i18n.getForm().setTitle(getTranslation("label.appname", event.getLocale()));
        i18n.getForm().setForgotPassword(getTranslation("label.login.password.forgot", event.getLocale()));
        i18n.getErrorMessage().setTitle(getTranslation("error.login.title", event.getLocale()));
        i18n.getErrorMessage().setMessage(getTranslation("error.login.msg", event.getLocale()));

        this.loginForm.setI18n(i18n);
    }

    private HorizontalLayout getCenteredContent() {
        final VerticalLayout content = getContentWrapper();

        final HorizontalLayout layoutWrap = new HorizontalLayout();
        layoutWrap.setWidth("19%");
        layoutWrap.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER,
                content);
        layoutWrap.setPadding(false);
        layoutWrap.setSpacing(false);
        layoutWrap.add(content);
        return layoutWrap;
    }

    private VerticalLayout getContentWrapper() {
        final VerticalLayout appLogo = new AppLogo();

        final HorizontalLayout localeSelector = new LocaleSelector();

        final VerticalLayout layoutWrap = new VerticalLayout();
        layoutWrap.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, appLogo);
        layoutWrap.setHorizontalComponentAlignment(FlexComponent.Alignment.END, localeSelector);
        layoutWrap.setHorizontalComponentAlignment(FlexComponent.Alignment.END, this.loginForm);
        layoutWrap.setPadding(false);
        layoutWrap.setSpacing(false);
        layoutWrap.add(appLogo);
        layoutWrap.add(localeSelector);
        layoutWrap.add(this.loginForm);
        return layoutWrap;
    }

}