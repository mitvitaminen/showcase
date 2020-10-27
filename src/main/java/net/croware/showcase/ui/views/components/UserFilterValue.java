package net.croware.showcase.ui.views.components;

import java.util.Optional;

import org.apache.logging.log4j.util.Strings;

import net.croware.showcase.backend.data.Role;

public class UserFilterValue {

    String filterString;
    Role roleFilter;

    public UserFilterValue(String filterString, Role roleFilter) {
        if (!Strings.isEmpty(filterString)) {
            this.filterString = filterString;
        } else {
            this.filterString = null;
        }
        this.roleFilter = roleFilter;
    }

    public Optional<String> getFilterString() {
        if (!Strings.isEmpty(this.filterString)) {
            return Optional.ofNullable(this.filterString);
        } else {
            return Optional.ofNullable(null);
        }
    }

    public Optional<Role> getRoleFilter() {
        return Optional.ofNullable(this.roleFilter);
    }

    public void setFilterString(String filterString) {
        this.filterString = filterString;
    }

    public void setRoleFilter(Role roleFilter) {
        this.roleFilter = roleFilter;
    }

}
