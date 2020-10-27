package net.croware.showcase.ui.views.components;

import javax.validation.ConstraintViolationException;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;

import net.croware.showcase.app.util.ShowcaseConstants;
import net.croware.showcase.backend.data.Role;
import net.croware.showcase.backend.data.User;
import net.croware.showcase.backend.services.IRoleService;
import net.croware.showcase.backend.services.IUserService;

public class EditUserComponent extends HorizontalLayout implements LocaleChangeObserver {

    private static final long serialVersionUID = ShowcaseConstants.SERIAL_VERSION_UID;

    private final EmailField emailField;
    private final TextField lastnameTextField;
    private final TextField firstnameTextField;
    private final MultiSelectListBox<Role> rolesMultiSelectListBox;
    private final DatePicker lockDatePicker;
    private final DatePicker expireDatePicker;
    private final DatePicker credentialsExpireDatePicker;
    private final Button saveButton;
    private final Binder<User> binder;
    private final Label validationErrorLabel;

    private User user;

    public EditUserComponent(IUserService userService, IRoleService roleService,
            ConfigurableFilterDataProvider<User, Void, UserFilterValue> dataProvider) {

        this.emailField = new EmailField();
        this.emailField.setPlaceholder("example@test.net");
        this.lastnameTextField = new TextField();
        this.firstnameTextField = new TextField();
        this.rolesMultiSelectListBox = new MultiSelectListBox<>();
        this.rolesMultiSelectListBox.setItems(roleService.findAll());
        this.rolesMultiSelectListBox.addValueChangeListener(event -> {
            if (this.user != null) {
                this.user.setRoles(event.getValue());
            }
        });
        this.rolesMultiSelectListBox.setRenderer(
                new ComponentRenderer<Text, Role>(role -> new Text(role.getAuthority())));
        this.lockDatePicker = new DatePicker();
        this.expireDatePicker = new DatePicker();
        this.credentialsExpireDatePicker = new DatePicker();
        this.binder = getBinder();
        this.validationErrorLabel = new Label();

        this.saveButton = new Button();
        this.saveButton.addClickListener(e -> {
            try {
                this.binder.writeBean(this.user);
                userService.save(this.user);
            } catch (final ValidationException | ConstraintViolationException ex) {
                final Dialog dialog = new Dialog();
                dialog.add(this.validationErrorLabel);
                dialog.setCloseOnOutsideClick(true);
                dialog.setOpened(true);
            } finally {
                dataProvider.refreshAll();
            }
        });
        add(getContent());
        setVisible(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void localeChange(LocaleChangeEvent event) {
        this.emailField.setLabel(getTranslation("label.user.email"));
        this.lastnameTextField.setLabel(getTranslation("label.user.lastname"));
        this.firstnameTextField.setLabel(getTranslation("label.user.firstname"));
        this.lockDatePicker.setLabel(getTranslation("label.user.account.lockdate"));
        this.expireDatePicker.setLabel(getTranslation("label.user.account.expiredate"));
        this.credentialsExpireDatePicker.setLabel(getTranslation("label.user.credentials.expiredate"));
        this.saveButton.setText(getTranslation("label.btn.save"));
        this.validationErrorLabel.setText(getTranslation("error.user.validation"));
    }

    public void setUser(User user) {
        if (user != null) {
            this.binder.readBean(user);
        }
        this.user = user;
    }

    private Binder<User> getBinder() {
        final Binder<User> binder = new Binder<>(User.class);
        binder.forField(this.emailField).bind(User::getEmail, User::setEmail);
        binder.forField(this.lastnameTextField).bind(User::getLastname, User::setLastname);
        binder.forField(this.firstnameTextField).bind(User::getFirstname, User::setFirstname);
        binder.forField(this.rolesMultiSelectListBox).bind(User::getRoles, User::setRoles);
        binder.forField(this.lockDatePicker).bind(User::getLockDate, User::setLockDate);
        binder.forField(this.expireDatePicker).bind(User::getAccountExpireDate, User::setAccountExpireDate);
        binder.forField(this.credentialsExpireDatePicker).bind(User::getCredentialsExpireDate,
                User::setCredentialsExpireDate);
        return binder;
    }

    private VerticalLayout getContent() {
        final HorizontalLayout upperEditBox = new HorizontalLayout();
        upperEditBox.setPadding(false);
        upperEditBox.add(this.emailField);
        upperEditBox.add(this.lastnameTextField);
        upperEditBox.add(this.firstnameTextField);
        upperEditBox.add(this.rolesMultiSelectListBox);

        final HorizontalLayout lowerEditBox = new HorizontalLayout();
        lowerEditBox.setPadding(false);
        lowerEditBox.add(this.lockDatePicker);
        lowerEditBox.add(this.expireDatePicker);
        lowerEditBox.add(this.credentialsExpireDatePicker);

        final VerticalLayout layout = new VerticalLayout();
        layout.setPadding(false);
        layout.add(upperEditBox);
        layout.add(lowerEditBox);
        layout.add(this.saveButton);
        return layout;
    }

}
