package com.itechnews.database.seed;

import com.github.javafaker.Faker;
import com.itechnews.entity.Role;
import com.itechnews.entity.User;
import com.itechnews.repository.RoleRepository;
import com.itechnews.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class UsersTableSeeder implements Seeder {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private Faker faker;

    @Override
    public void run() {
        Role roleAdmin = roleRepository.findOneByName("ADMIN");
        Role roleUser = roleRepository.findOneByName("USER");
        List<User> users = new ArrayList<>();

        users.add(new User(null, "sonthh", "sonthh",
                passwordEncoder.encode("sonthh"), true,
                "tigersama2205+sonthh@gmail.com", "Quang Name", roleAdmin,
                null, null, null, null, null)
        );
        users.add(new User(null, "thinhtnb", "thinhtnb",
                passwordEncoder.encode("thinhtnb"), true,
                "tigersama2205+thinhtnb@gmail.com", "Hue", roleAdmin,
                null, null, null, null, null)
        );
        users.add(new User(null, "thuydtm", "thuydtm",
                passwordEncoder.encode("thuydtm"), true,
                "tigersama2205+thuydtm@gmail.com", "Da Nang", roleAdmin,
                null, null, null, null, null)
        );
        users.add(new User(null, "thuta", "thuta",
                passwordEncoder.encode("thuta"), true,
                "tigersama2205+thuta@gmail.com", "Da Nang", roleUser,
                null, null, null, null, null)
        );
        users.add(new User(null, "trangntt", "trangntt",
                passwordEncoder.encode("trangntt"), true,
                "tigersama2205+trangttt@gmail.com", "Hue", roleUser,
                null, null, null, null, null)
        );

        //fake 20 user account
        for (int i = 0; i < 20; i++) {
            String username = faker.name().username() + i;
            Boolean status = i % 2 == 0;
            users.add(new User(null, username, username,
                    passwordEncoder.encode(username), status,
                    "tigersama2205+" + username + "@gmail.com", faker.address().cityName(), roleUser,
                    null, null, null, null, null)
            );
        }
        userRepository.saveAll(users);


        User sonthh = userRepository.findOneByUsername("sonthh");
        User thinhtbn = userRepository.findOneByUsername("thinhtnb");
        User thuydm = userRepository.findOneByUsername("thuydtm");
        User trangntt = userRepository.findOneByUsername("trangntt");
        User thuta = userRepository.findOneByUsername("thuta");

        sonthh.setFollower(Arrays.asList(thinhtbn, thuydm, trangntt, thuta));
        userRepository.save(sonthh);

        thinhtbn.setFollower(Arrays.asList(sonthh, thuydm, trangntt, thuta));
        userRepository.save(thinhtbn);
    }
}
