package net.croware.showcase.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.croware.showcase.backend.data.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Long countByType(Role.Type role);

    Role findByType(Role.Type role);

}
