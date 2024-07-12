package com.brian.jobapp1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // to change configuration now we have to return object of security filter chain
    //authentication provider for the dao(database)
    @Autowired //its object injected in service layer
    private UserDetailsService userDetailsService;
    @Autowired
    private  JwtFilter jwtFilter;

    @Bean
    public AuthenticationProvider authenticationProvider(){
           DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
           provider.setUserDetailsService(userDetailsService); //connects with the users table
          // provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
           provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
           return  provider;
    }

    @Bean // spring creates its object
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(request -> request
                .requestMatchers("register","login") //any user can register without the authentication
                .permitAll()
                .anyRequest().authenticated());
        //http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);;
        // u dont need form login wen we using stateless
        return http.build();
        //there is a default class called userDetailsService that checks if there is a password in the application properties
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

//    @Bean // we want to return the object of user details service
//    public UserDetailsService  userDetailsService(){
//        UserDetails user = User.withDefaultPasswordEncoder().username("brian").password("1234").roles("USER").build();
//        //build returns the user details object
//        return  new InMemoryUserDetailsManager(user);
//    }
    // no need to put the credentials in the properties since  we have returned our own use details service
    // if u did not put user details service by default it will  be there , it will check the credentials in the application properties class
}

        //imperative(normal method)
//        Customizer<CsrfConfigurer<HttpSecurity>> csrfConfigurerCustomizer = new Customizer<CsrfConfigurer<HttpSecurity>>() {
//            //anonymous inner class used
//            @Override
//            public void customize(CsrfConfigurer<HttpSecurity> httpSecurityCsrfConfigurer) {
//                httpSecurityCsrfConfigurer.disable();
//            }
//        };
//        http.csrf(csrfConfigurerCustomizer);
//        Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> customizer = new Customizer<AuthorizeHttpRequestsConfigurer<org.springframework.security.config.annotation.web.builders.HttpSecurity>.AuthorizationManagerRequestMatcherRegistry>() {
//            @Override
//            public void customize(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorizationManagerRequestMatcherRegistry) {
//                authorizationManagerRequestMatcherRegistry.anyRequest().authenticated();
//            }
//        };
//        http.authorizeHttpRequests(customizer);
//
//        //u get a new session every time
//        return http.build();
//
//    }


