package com.itechnews.database.seed;

import com.itechnews.entity.Role;
import com.itechnews.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RolesTableSeeder implements Seeder {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(null, "ADMIN", null));
        roles.add(new Role(null, "USER", null));
        roleRepository.saveAll(roles);
    }
}
