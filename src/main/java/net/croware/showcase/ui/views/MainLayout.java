package net.croware.showcase.ui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinServlet;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import net.croware.showcase.app.security.SecurityUtils;
import net.croware.showcase.app.util.ShowcaseConstants;
import net.croware.showcase.ui.views.components.AppLogo;
import net.croware.showcase.ui.views.components.LocaleSelector;

/**
 * This serves as layout class for the application.
 *
 * @author christian
 *
 */
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class MainLayout extends AppLayout implements LocaleChangeObserver {

    private static final long serialVersionUID = ShowcaseConstants.SERIAL_VERSION_UID;

    private final AppLogo appLogo;
    private final Label logoutLabel;
    private final Label titleAdminArea;
    private final Label titleItemsArea;

    public MainLayout() {
        this.appLogo = new AppLogo();
        this.logoutLabel = new Label();
        this.titleAdminArea = new Label();
        this.titleItemsArea = new Label();

        this.appLogo.addClassName("hide-on-mobile");
        setDrawerOpened(false);

        addToNavbar(getNavBar());

        final ConfirmDialog confirmDialog = new ConfirmDialog();
        confirmDialog.setCancelable(true);
        confirmDialog.setConfirmButtonTheme("raised tertiary error");
        confirmDialog.setCancelButtonTheme("raised tertiary");
        this.getElement().appendChild(confirmDialog.getElement());

        getElement().addEventListener("search-focus", e -> {
            getElement().getClassList().add("hide-navbar");
        });
        getElement().addEventListener("search-blur", e -> {
            getElement().getClassList().remove("hide-navbar");
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void localeChange(LocaleChangeEvent arg0) {
        this.titleAdminArea.setText(getTranslation("label.area.admin"));
        this.titleItemsArea.setText(getTranslation("label.area.items"));
        this.logoutLabel.setText(getTranslation("label.logout"));
    }

    private VerticalLayout getAppLogo() {
        final VerticalLayout layout = new VerticalLayout();
        layout.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, this.appLogo);
        layout.add(this.appLogo);
        return layout;
    }

    private HorizontalLayout getAppLogoMenuTabsLocaleSelector() {
        final VerticalLayout appLogo = getAppLogo();
        final Tabs menuTabs = getMenuTabs();
        final HorizontalLayout localeSelector = new LocaleSelector();

        final HorizontalLayout layout = new HorizontalLayout();
        layout.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER, appLogo);
        layout.setVerticalComponentAlignment(FlexComponent.Alignment.END, menuTabs);
        layout.setVerticalComponentAlignment(FlexComponent.Alignment.END, localeSelector);
        layout.add(appLogo);
        layout.add(menuTabs);
        layout.add(localeSelector);
        return layout;
    }

    private Anchor getLogoutLink(String contextPath) {
        final Anchor a = getMenuTabLink(this.logoutLabel, VaadinIcon.ARROW_RIGHT, new Anchor());
        // inform vaadin-router that it should ignore this link
        a.setHref(contextPath + "/logout");
        a.getElement().setAttribute("router-ignore", true);
        return a;
    }

    private Tab getMenuTab(Component content) {
        final Tab tab = new Tab();
        tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        tab.add(content);
        return tab;
    }

    private <T extends HasComponents> T getMenuTabLink(Label title, VaadinIcon icon, T a) {
        a.add(icon.create());
        a.add(title);
        return a;
    }

    private Tabs getMenuTabs() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.HORIZONTAL);

        if (SecurityUtils.isAccessGranted(UsersView.class)) {
            tabs.add(getMenuTab(
                    getMenuTabLink(this.titleAdminArea, VaadinIcon.USER, new RouterLink(null, UsersView.class))));
        }
        tabs.add(getMenuTab(
                getMenuTabLink(this.titleItemsArea, VaadinIcon.BULLETS, new RouterLink(null, FrontView.class))));

        tabs.add(getMenuTab(getLogoutLink(VaadinServlet.getCurrent().getServletContext().getContextPath())));
        return tabs;
    }

    private VerticalLayout getNavBar() {

        final HorizontalLayout appLogoMenuTabsLocaleSelector = getAppLogoMenuTabsLocaleSelector();

        final VerticalLayout navBar = new VerticalLayout();
        navBar.setPadding(false);
        navBar.setHorizontalComponentAlignment(FlexComponent.Alignment.START, appLogoMenuTabsLocaleSelector);
        navBar.add(appLogoMenuTabsLocaleSelector);
        return navBar;
    }

}
