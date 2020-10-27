package net.croware.showcase.backend.services;

import java.util.List;

import net.croware.showcase.backend.data.Role;

public interface IRoleService extends ICrudService<Role> {

    List<Role> findAll();

    Role findByType(Role.Type roleType);
}
