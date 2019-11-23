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
import java.util.stream.Collectors;

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
                "tigersama2205+sonthh@gmail.com", "Quang Nam","sonthh.jpg", roleAdmin,
                null, null, null, null, null)
        );
        users.add(new User(null, "thinhtnb", "thinhtnb",
                passwordEncoder.encode("thinhtnb"), true,
                "tigersama2205+thinhtnb@gmail.com", "Hue","thinhtnb.png", roleAdmin,
                null, null, null, null, null)
        );
        users.add(new User(null, "thuydtm", "thuydtm",
                passwordEncoder.encode("thuydtm"), true,
                "tigersama2205+thuydtm@gmail.com", "Da Nang","thuydtm.png", roleAdmin,
                null, null, null, null, null)
        );
        users.add(new User(null, "thuta", "thuta",
                passwordEncoder.encode("thuta"), true,
                "tigersama2205+thuta@gmail.com", "Da Nang","thuta.png", roleUser,
                null, null, null, null, null)
        );
        users.add(new User(null, "trangntt", "trangntt",
                passwordEncoder.encode("trangntt"), true,
                "tigersama2205+trangttt@gmail.com", "Hue","trangntt.jpg", roleUser,
                null, null, null, null, null)
        );

        //fake 20 user account
        for (int i = 0; i < 20; i++) {
            String username = faker.name().username() + i;
            Boolean status = i % 2 == 0;
            users.add(new User(null, username, username,
                    passwordEncoder.encode(username), status,
                    "tigersama2205+" + username + "@gmail.com", faker.address().cityName(),"default-user.png", roleUser,
                    null, null, null, null, null)
            );
        }
        userRepository.saveAll(users);


        List<User> listUser1 = userRepository.findByIdLessThan(6);
        List<User> listUser2 = userRepository.findByIdGreaterThan(10);
        listUser1.forEach(user -> {
            List<User> list = new ArrayList<>();
            List<User> u = listUser1.stream()
                    .filter(item ->
                        !item.getUsername().equals(user.getUsername()))
                    .collect(Collectors.toList());
            list.addAll(u);
            list.addAll(listUser2);
            user.setFollower(list);
        });
        listUser2.forEach(user -> {
            user.setFollower(listUser1);
        });
        userRepository.saveAll(listUser1);
        userRepository.saveAll(listUser2);
    }
}
