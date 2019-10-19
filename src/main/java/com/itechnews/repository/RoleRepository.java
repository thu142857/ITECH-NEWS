package com.itechnews.repository;

import com.itechnews.entity.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository
        extends CrudRepository<Role, Integer> {

    Role findOneByName(String name);
}
