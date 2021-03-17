package net.croware.showcase.ui.views.components;

import java.util.Optional;

import net.croware.showcase.backend.data.Role;

public class UserFilterValue {

    String filterString;
    Role roleFilter;

    public UserFilterValue(String filterString, Role roleFilter) {
        this.filterString = filterString;
        this.roleFilter = roleFilter;
    }

    public Optional<String> getFilterString() {
        return Optional.ofNullable(this.filterString);
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
