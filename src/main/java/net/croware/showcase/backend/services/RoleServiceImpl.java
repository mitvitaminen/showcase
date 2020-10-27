package net.croware.showcase.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import net.croware.showcase.backend.data.Role;
import net.croware.showcase.backend.data.Role.Type;
import net.croware.showcase.backend.repositories.RoleRepository;

@Service
public class RoleServiceImpl implements IRoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Role createNew() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Role> findAll() {
        return this.roleRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Role findByType(Type roleType) {
        return this.roleRepository.findByType(roleType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JpaRepository<Role, Long> getRepository() {
        return this.roleRepository;
    }

}
