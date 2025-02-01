package com.project.gestionecole.configurations;

import com.project.gestionecole.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                // Public endpoints
                .requestMatchers("/register", "/login").permitAll()
                // Role-specific endpoints
                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/professors/**").hasAnyAuthority("PROFESSOR", "ADMIN")
                .requestMatchers("/modules/**").hasAnyAuthority("PROFESSOR", "ADMIN")
                .requestMatchers("/students/**").hasAnyAuthority("STUDENT", "PROFESSOR", "ADMIN")
                        .requestMatchers("/enrollments/**").hasAnyAuthority( "PROFESSOR", "ADMIN")

                        // Grant access to /home for all authenticated users
                .requestMatchers("/home","/change-password").authenticated()
                // Default rule: Any other request must be authenticated
                .anyRequest().authenticated()
        )
                .csrf(withDefaults())
//                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF explicitly if not needed
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("email").passwordParameter("password")
                        .failureUrl("/login?error=true")
                        .defaultSuccessUrl("/home", true) // Redirect to home after successful login
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                );

        return http.build();
    }


    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}

