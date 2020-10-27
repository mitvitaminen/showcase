package net.croware.showcase.app.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.croware.showcase.backend.data.Role;
import net.croware.showcase.backend.data.User;
import net.croware.showcase.backend.services.IRoleService;
import net.croware.showcase.backend.services.IUserService;
import net.croware.showcase.ui.views.HasLogger;

@Component
public class DataCreator implements HasLogger {

    private final IRoleService roleService;
    private final IUserService userService;

    @Autowired
    public DataCreator(IRoleService roleService, IUserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    private User createAdmin() {

        final Role adminRole = roleService.findByType(Role.Type.ADMIN);

        final Role userRole = roleService.findByType(Role.Type.USER);

        final Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminRoles.add(userRole);
        return userService
                .save(new User("admin@admin.de", "1234567890123456", "Ad", "Min", adminRoles, null, null, null));
    }

    private void createRoles() {
        final Role admin = new Role(Role.Type.ADMIN);
        final Role user = new Role(Role.Type.USER);
        roleService.save(admin);
        roleService.save(user);
    }

    private void createUsers() {
        final Role userRole = roleService.findByType(Role.Type.USER);

        final Set<Role> singleUserRole = new HashSet<>();
        singleUserRole.add(userRole);

        final List<User> l = new ArrayList<>();
        l.add(new User("user@user.de", "1234567890123456", "Somename", "Müller", singleUserRole,
                null, null, null));
        l.add(new User("karl@use.me", "1234567890123456", "Karl", "IsLocked", singleUserRole,
                null, null, LocalDate.now()));
        l.add(new User("karla@use.me", "1234567890123456", "Karla", "IsAccountExpired", singleUserRole,
                LocalDate.now(), null, null));
        l.add(new User("karlson@use.me", "1234567890123456", "Karlson", "IsCredentialsExpired", singleUserRole,
                null, LocalDate.now(), null));
        for (int i = 0; i < 25; i++) {
            l.add(new User("test" + i + "@test.de", "1234567890123456", "dummyFirstame", "dummyLastname" + i,
                    singleUserRole, null, null, null));
        }
        l.forEach(u -> userService.save(u));
    }

    @PostConstruct
    private void fillDatabase() {
        getLogger().error("Generating demo data in database.");
        getLogger().error("...generating  Roles");
        createRoles();

        getLogger().error("...generating admin user");
        createAdmin();
        getLogger().error("...generating user users");
        createUsers();
        getLogger().error("...done!");
    }

}
