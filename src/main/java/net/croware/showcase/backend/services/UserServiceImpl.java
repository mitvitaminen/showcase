package net.croware.showcase.backend.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.google.common.collect.Streams;

import net.croware.showcase.backend.data.Role;
import net.croware.showcase.backend.data.User;
import net.croware.showcase.backend.repositories.UserRepository;
import net.croware.showcase.ui.views.components.UserFilterValue;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long countAnyMatching(Optional<String> filterString) {
        return this.userRepository
                .countByEmailContainingIgnoreCaseOrFirstnameContainingIgnoreCaseOrLastnameContainingIgnoreCase(
                        filterString.orElse(""), filterString.orElse(""), filterString.orElse(""));
    }

    /**
     * {@inheritDoc}
     */
    public Long countByRolesIn(Set<Role> inROles) {
        return this.userRepository.countByRolesIn(inROles);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long countByUserFilter(Optional<UserFilterValue> userFilterValue) {
        final UserFilterValue ufv = userFilterValue.orElse(new UserFilterValue(null, null));
        if (ufv.getFilterString().isPresent() && !Strings.isEmpty(ufv.getFilterString().get())
                && ufv.getRoleFilter().isPresent()) {
            final String filterString = ufv.getFilterString().get();
            final Set<Role> roles = new HashSet<>();
            roles.add(ufv.getRoleFilter().get());

            return this.userRepository.countByRolesInAndEmailContainingIgnoreCase(roles, filterString)
                    + this.userRepository.countByRolesInAndFirstnameContainingIgnoreCase(roles, filterString)
                    + this.userRepository.countByRolesInAndLastnameContainingIgnoreCase(roles, filterString);

        } else if (ufv.getRoleFilter().isPresent()) {
            final Set<Role> roles = new HashSet<>();
            roles.add(ufv.getRoleFilter().get());
            return countByRolesIn(roles);
        } else {
            return countAnyMatching(ufv.getFilterString());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User createNew() {
        return new User();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<User> findAnyMatching(Optional<String> filterString, Pageable pageable) {
        return this.userRepository
                .findByEmailContainingIgnoreCaseOrFirstnameContainingIgnoreCaseOrLastnameContainingIgnoreCase(
                        filterString.orElse(""), filterString.orElse(""), filterString.orElse(""), pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<User> findByRolesIn(Set<Role> inRoles, Pageable pageable) {
        return this.userRepository.findByRolesIn(inRoles, pageable);
    }

    /**
     * TODO duplicate aussortieren evtl mit NamedQuery {@inheritDoc}
     */
    @Override
    public Stream<User> findByUserFilter(Optional<UserFilterValue> userFilterValue, Pageable pageable) {
        final UserFilterValue ufv = userFilterValue.orElse(new UserFilterValue(null, null));

        if (ufv.getFilterString().isPresent() && !Strings.isEmpty(ufv.getFilterString().get())
                && ufv.getRoleFilter().isPresent()) {
            final Set<Role> roles = new HashSet<>();
            roles.add(ufv.getRoleFilter().get());
            return Streams.concat(
                    this.userRepository
                            .findByRolesInAndEmailContainingIgnoreCase(roles, ufv.getFilterString().orElse(""))
                            .stream(),
                    this.userRepository
                            .findByRolesInAndFirstnameContainingIgnoreCase(roles, ufv.getFilterString().orElse(""))
                            .stream(),
                    this.userRepository
                            .findByRolesInAndLastnameContainingIgnoreCase(roles, ufv.getFilterString().orElse(""))
                            .stream());
        } else if (ufv.getRoleFilter().isPresent()) {
            final Set<Role> roles = new HashSet<>();
            roles.add(ufv.getRoleFilter().get());
            return this.userRepository.findByRolesIn(roles, pageable).stream();
        } else {
            return findAnyMatching(ufv.getFilterString(), pageable).stream();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JpaRepository<User, Long> getRepository() {
        return this.userRepository;
    }

    /**
     * Note: email is username. {@inheritDoc}
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final Optional<User> user = Optional.ofNullable(this.userRepository.findByEmail(email));
        return user.orElseThrow(() -> new UsernameNotFoundException("exception.username.notfound"));
    }

}
