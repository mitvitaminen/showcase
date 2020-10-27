package net.croware.showcase.ui.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import net.croware.showcase.app.util.ShowcaseConstants;
import net.croware.showcase.backend.data.User;
import net.croware.showcase.backend.services.IRoleService;
import net.croware.showcase.backend.services.IUserService;
import net.croware.showcase.ui.views.components.EditUserComponent;
import net.croware.showcase.ui.views.components.UserFilterField;
import net.croware.showcase.ui.views.components.UserFilterValue;

/**
 * Layout class for the overview of users. Secured for admins only.
 *
 * @author christian
 *
 */
@PageTitle(ShowcaseConstants.ROUTERPATH_ADMINAREA_USERS)
@Route(value = ShowcaseConstants.ROUTERPATH_ADMINAREA_USERS, layout = MainLayout.class)
@CssImport(ShowcaseConstants.CSS_SHARED_FILE)
@Secured("ADMIN")
public class UsersView extends VerticalLayout implements LocaleChangeObserver {

    private static final long serialVersionUID = ShowcaseConstants.SERIAL_VERSION_UID;

    private final Grid<User> grid;
    private final UserFilterField userFilterField;

    @Autowired
    public UsersView(final IRoleService roleService, IUserService userService) {

        final ConfigurableFilterDataProvider<User, Void, UserFilterValue> dataProvider = getDataProvider(
                userService);

        final EditUserComponent editUserComponent = new EditUserComponent(userService, roleService, dataProvider);

        this.grid = getGrid();
        this.grid.setItems(dataProvider);
        this.grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                editUserComponent.setUser(event.getValue());
                editUserComponent.setVisible(true);
            } else {
                editUserComponent.setVisible(false);
            }
        });

        this.userFilterField = new UserFilterField(roleService);
        this.userFilterField.addValueChangeListener(event -> {
            this.userFilterField.setPresentationValue(event.getValue());
            dataProvider.setFilter(event.getValue());
        });

        add(editUserComponent);
        add(this.userFilterField);
        add(this.grid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void localeChange(LocaleChangeEvent event) {
        this.userFilterField.setLabel(getTranslation("label.filter"));
        this.grid.getColumns().forEach(c -> c.setHeader(getTranslation(c.getId().orElse(""))));
    }

    private ConfigurableFilterDataProvider<User, Void, UserFilterValue> getDataProvider(IUserService userService) {
        final DataProvider<User, UserFilterValue> dataProvider = DataProvider.fromFilteringCallbacks(
                query -> userService.findByUserFilter(query.getFilter(),
                        PageRequest.of(query.getOffset(), query.getLimit())),
                query -> Math.toIntExact(userService.countByUserFilter(query.getFilter())));

        final ConfigurableFilterDataProvider<User, Void, UserFilterValue> filteredDataProvider = dataProvider
                .withConfigurableFilter();
        return filteredDataProvider;
    }

    private Grid<User> getGrid() {
        final Grid<User> grid = new Grid<>();
        grid.addColumn(User::getEmail).setId("label.user.email");
        grid.addColumn(User::getLastname).setId("label.user.lastname");
        grid.addColumn(User::getFirstname).setId("label.user.firstname");
        grid.addColumn(User::getRolesAsString).setId("label.user.roles");
        grid.addColumn(
                new LocalDateRenderer<>(User::getAccountExpireDate, ShowcaseConstants.DATEPATTERN_YYYY_MM_DD))
                .setId("label.user.account.expiredate");
        grid.addColumn(new LocalDateRenderer<>(User::getLockDate, ShowcaseConstants.DATEPATTERN_YYYY_MM_DD))
                .setId("label.user.account.lockdate");
        grid.addColumn(
                new LocalDateRenderer<>(User::getCredentialsExpireDate, ShowcaseConstants.DATEPATTERN_YYYY_MM_DD))
                .setId("label.user.credentials.expiredate");
        return grid;
    }

}
