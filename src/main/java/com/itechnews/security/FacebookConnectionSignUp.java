package com.itechnews.security;

import com.itechnews.entity.Role;
import com.itechnews.entity.User;
import com.itechnews.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Service;

@Service
public class FacebookConnectionSignUp implements ConnectionSignUp {

    @Autowired
    private UserService userService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public String execute(Connection<?> connection) {
        //ConnectionKey key = connection.getKey();
        //System.out.println("key= (" + key.getProviderId() + "," + key.getProviderUserId() + ")");
        Facebook facebook = (Facebook) connection.getApi();

        String[] fields = {"id", "email", "address", "first_name", "last_name"};
        org.springframework.social.facebook.api.User userProfile = facebook.fetchObject("me",
                org.springframework.social.facebook.api.User.class, fields);

        String username = userProfile.getFirstName().trim().toLowerCase()
                + userProfile.getLastName().trim().toLowerCase();

        User user = userService.findOneByUsername(username);
        if (user != null) {
            return user.getUsername();
        }

        user = new User(null, username, connection.getDisplayName(),
                passwordEncoder.encode("1234567"), true, userProfile.getEmail(), null,
                connection.getImageUrl(), new Role(2, null, null), null, null,
                null, null, null);

        user = userService.save(user);
        return user.getUsername();
    }

}