package net.croware.showcase.ui.views.components;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;

import net.croware.showcase.app.util.ShowcaseConstants;
import net.croware.showcase.backend.data.Role;
import net.croware.showcase.backend.services.IRoleService;

public class UserFilterField extends CustomField<UserFilterValue> implements LocaleChangeObserver {

    private static final long serialVersionUID = ShowcaseConstants.SERIAL_VERSION_UID;

    private final TextField inputTextField;
    private final ComboBox<Role> roleComboBox;

    public UserFilterField(IRoleService roleService) {
        this.roleComboBox = new ComboBox<>();
        this.roleComboBox.setItems(roleService.findAll());
        this.roleComboBox.setItemLabelGenerator(Role::getAuthority);
        this.inputTextField = new TextField();

        final HorizontalLayout hl = new HorizontalLayout();
        hl.add(this.inputTextField);
        hl.add(this.roleComboBox);
        add(hl);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserFilterValue generateModelValue() {
        return new UserFilterValue(this.inputTextField.getValue(), this.roleComboBox.getValue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void localeChange(LocaleChangeEvent event) {
        this.inputTextField.setPlaceholder(getTranslation("label.stringpattern"));
        this.roleComboBox.setPlaceholder(getTranslation("label.role"));

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPresentationValue(UserFilterValue userFilterValue) {
        if (userFilterValue == null) {
            this.inputTextField.setValue(null);
            this.roleComboBox.setValue(null);
        } else {
            userFilterValue.getFilterString().filter(""::equals).ifPresent(s -> this.inputTextField.setValue(s));
            this.roleComboBox.setValue(userFilterValue.roleFilter);
        }

    }

}
