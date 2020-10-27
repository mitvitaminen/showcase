package net.croware.showcase.backend.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.croware.showcase.backend.data.Role;
import net.croware.showcase.backend.data.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Long countByEmailContainingIgnoreCaseOrFirstnameContainingIgnoreCaseOrLastnameContainingIgnoreCase(
            String emailLike, String firstnameLike, String lastnameLike);

    Long countByRolesIn(Set<Role> roles);

    Long countByRolesInAndEmailContainingIgnoreCase(Set<Role> inRoles, String emailLike);

    Long countByRolesInAndFirstnameContainingIgnoreCase(Set<Role> inRoles, String firstnameLike);

    Long countByRolesInAndLastnameContainingIgnoreCase(Set<Role> inRoles, String lastnameLike);

    User findByEmail(String email);

    Page<User> findByEmailContainingIgnoreCaseOrFirstnameContainingIgnoreCaseOrLastnameContainingIgnoreCase(
            String emailLike, String firstnameLike, String lastnameLike, Pageable pageable);

    Page<User> findByRolesIn(Set<Role> inRoles, Pageable pageable);

    List<User> findByRolesInAndEmailContainingIgnoreCase(Set<Role> inRoles, String emailLike);

    List<User> findByRolesInAndFirstnameContainingIgnoreCase(Set<Role> inRoles, String firstnameLike);

    List<User> findByRolesInAndLastnameContainingIgnoreCase(Set<Role> inRoles, String lastnameLike);

}
