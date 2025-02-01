package com.project.gestionecole;

import com.project.gestionecole.respositories.RoleRepository;
import com.project.gestionecole.respositories.UserRepository;
import com.project.gestionecole.models.Role;
import com.project.gestionecole.models.RoleNames;
import com.project.gestionecole.models.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class GestionEcoleApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionEcoleApplication.class, args);
    }
    @Bean
    public CommandLineRunner dataLoader(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Create default roles
            if (roleRepository.findByName(RoleNames.valueOf("ADMIN")) == null) {
                Role adminRole = new Role();
                adminRole.setName(RoleNames.valueOf("ADMIN"));
                roleRepository.save(adminRole);
            }

            if (roleRepository.findByName(RoleNames.valueOf("STUDENT")) == null) {
                Role secretaryRole = new Role();
                secretaryRole.setName(RoleNames.valueOf("STUDENT"));
                roleRepository.save(secretaryRole);
            }

            if (roleRepository.findByName(RoleNames.valueOf("PROFESSOR")) == null) {
                Role professorRole = new Role();
                professorRole.setName(RoleNames.valueOf("PROFESSOR"));
                roleRepository.save(professorRole);
            }

            // Create a default admin user if not exists
            if (userRepository.findByEmail("admin@example.com").isEmpty()) {
                User admin = new User();
                admin.setFirstName("Admin");
                admin.setLastName("User");
                admin.setEmail("admin@example.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setActive(true);
                admin.setCreatedAt(new java.util.Date());
                admin.setUpdatedAt(new java.util.Date());
                admin.setRoles(List.of(roleRepository.findByName(RoleNames.valueOf("ADMIN"))));
                userRepository.save(admin);
            }
        };
    }
}
