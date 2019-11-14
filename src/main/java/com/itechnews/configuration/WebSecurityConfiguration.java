package com.itechnews.configuration;

import com.itechnews.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;

//https://spring.io/guides/gs/securing-web/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth);
        BCryptPasswordEncoder encoder = passwordEncoder();
        //create user for demo
//        auth.
//                inMemoryAuthentication()
//                    .withUser("admin")
//                    .password(encoder.encode("123"))
//                    .roles("ADMIN")
//                    .and()
//                    .passwordEncoder(encoder);
        //authenticate from db
        auth.inMemoryAuthentication();//ignore default login form
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //super.configure(web);
        web.ignoring().antMatchers("/css/**", "/js/**", "/images/**");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*jwt*/
        /*http.authorizeRequests().anyRequest().authenticated().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.NEVER);*/
        //super.configure(http);
        http.authorizeRequests().antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')");
        http.authorizeRequests().antMatchers("/demo").access("hasRole('ROLE_ADMIN')");
        http.authorizeRequests().antMatchers("/abc/**").access("hasAnyRole('ROLE_AMDIN', 'ROLE_USER')");
        http.authorizeRequests()
                .antMatchers("/api/*", "/logout", "/login").permitAll();

        //the other pages are protected
        //http.authorizeRequests().anyRequest().authenticated();

        //the other pages are not protected
        http.authorizeRequests().anyRequest().permitAll();

        //access denied page: 403 page
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

        //remember me
        http.rememberMe().rememberMeCookieName("remember-me").rememberMeParameter("remember-me").key("uniqueAndSecret").tokenValiditySeconds(1296000);

        //cors, csrf api call
        //login, logout
        http
                .cors().and()/*rest api*/
                .csrf().ignoringAntMatchers("/api/**", "/assets/public/lib/**").and()//csrf TODO
                //.httpBasic() //don't use default form
                .formLogin()
                .loginPage("/login") //get method
                .loginProcessingUrl("/login") //post method
                .usernameParameter("username")
                .passwordParameter("password")
                //.defaultSuccessUrl("/admin/dashboard", false) //false: always-use-default-target="false"
                .failureUrl("/login?error=loginErr")
                .successHandler(authenticationSuccessHandler)
                //.failureHandler()
                .and()
                .logout()
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");

        http.apply(new SpringSocialConfigurer())
                .signupUrl("/signup");

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        //config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
