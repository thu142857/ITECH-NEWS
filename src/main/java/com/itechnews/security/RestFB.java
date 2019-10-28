//package com.itechnews.security;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.restfb.DefaultFacebookClient;
//import com.restfb.FacebookClient;
//import com.restfb.Parameter;
//import com.restfb.Version;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.env.Environment;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class RestFB {
//
//    @Autowired
//    private Environment env;
//
//    public String getToken(final String code) throws IOException {
//        String link = String.format(env.getProperty("facebook.link.get.token"), env.getProperty("facebook.app.id"),
//                env.getProperty("facebook.app.secret"), env.getProperty("facebook.redirect.uri"), code);
//
//        RestTemplate restTemplate = new RestTemplate();
//        String response = restTemplate.getForObject(link, String.class);
//
//        ObjectMapper mapper = new ObjectMapper();
//        return mapper.readTree(response).get("access_token").asText();
//    }
//
//    public com.restfb.types.User getUserInfo(final String accessToken) {
//        FacebookClient facebookClient = new DefaultFacebookClient(accessToken, env.getProperty("facebook.app.secret"),
//                Version.LATEST);
//        return facebookClient.fetchObject("me", com.restfb.types.User.class,
//                Parameter.with("fields", "name,email,gender,locale,timezone"));
//
//    }
//
//    public UserDetails buildUser(com.restfb.types.User user) {
//        boolean enabled = true;
//        boolean accountNonExpired = true;
//        boolean credentialsNonExpired = true;
//        boolean accountNonLocked = true;
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//
////        UserDetailsImpl userDetails = new UserDetailsImpl(null, user.getId() + user.getName(),
////                null, null, true, null, authorities);
//
////        UserDetails userDetail = new User(user.getId() + user.getName(), "", enabled,
////                accountNonExpired, credentialsNonExpired,
////                accountNonLocked, authorities);
//
//
//
//        return null;
//    }
//}