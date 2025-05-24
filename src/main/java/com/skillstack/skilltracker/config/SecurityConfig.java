package com.skillstack.skilltracker.config;

import com.skillstack.skilltracker.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

     @Autowired
     private UserDetailsServiceImpl userDetailsService;

     @Bean
     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
          http
                  .authenticationProvider(authenticationProvider())
                  .cors(Customizer.withDefaults())
                  .csrf(AbstractHttpConfigurer::disable)
                  .authorizeHttpRequests(auth -> auth
                          .requestMatchers("/api/v1/skills/**", "/api/v1/users/**").authenticated()
                          .requestMatchers("/api/v1/admin/all-users").hasRole("ADMIN")
                          .anyRequest().permitAll()
                  )
                  .httpBasic(Customizer.withDefaults())
                  .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

          return http.build();
     }

     @Bean
     public PasswordEncoder passwordEncoder() {
          return new BCryptPasswordEncoder();
     }

     @Bean
     public DaoAuthenticationProvider authenticationProvider() {
          DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
          authProvider.setUserDetailsService(userDetailsService);
          authProvider.setPasswordEncoder(passwordEncoder());
          return authProvider;
     }

     @Bean
     public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
          return config.getAuthenticationManager();
     }
}
