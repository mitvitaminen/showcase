package net.croware.showcase.backend.services;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import net.croware.showcase.backend.data.Role;
import net.croware.showcase.backend.data.User;
import net.croware.showcase.ui.views.components.UserFilterValue;

public interface IUserService extends UserDetailsService, IFilterableCrudService<User> {

    Long countByUserFilter(Optional<UserFilterValue> userFilterValue);

    Page<User> findByRolesIn(Set<Role> inRoles, Pageable pageable);

    Stream<User> findByUserFilter(Optional<UserFilterValue> userFilterValue, Pageable pageable);
}
