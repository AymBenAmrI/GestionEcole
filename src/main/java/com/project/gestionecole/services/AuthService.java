package com.project.gestionecole.services;
import com.project.gestionecole.models.Role;
import com.project.gestionecole.respositories.RoleRepository;
import com.project.gestionecole.respositories.UserRepository;
import com.project.gestionecole.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public String login(String email, String password) {
        try {
            if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
                throw new IllegalArgumentException("Email or password cannot be empty");
            }
            // Find the user by email
//            User user = userRepository.findByEmail(email).orElse(null);
//            // If user doesn't exist, throw an error
//            if (user == null) {
//                throw new RuntimeException("User not found");
//            }
//            // If the user has no password (first-time login), allow them to set it
//            if (user.getPassword() == null) {
//                user.setPassword(passwordEncoder.encode(password));  // Set the new password
//                user.setActive(true);  // Mark the user as active
//                userRepository.save(user);  // Save the user with the new password
//            }
            // Authenticate the user (after setting the password, if needed)
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            return "Login successful for user: " + authentication.getName();
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid username or password", e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (Exception e) {
            // Catch any other exception and log it
            throw new RuntimeException("An unexpected error occurred", e);
        }
    }

    public void register(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("User with email " + user.getEmail() + " already exists");
        }
        for (Role role : user.getRoles()) {
            Role existingRole = roleRepository.findByName(role.getName());
            if (existingRole == null) {
                throw new RuntimeException("Role not found: " + role.getName());
            }
            role.setId(existingRole.getId());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        userRepository.save(user);
    }
    public void changePassword(String currentPassword, String newPassword, String confirmPassword) {
        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Get the email/username of the logged-in user

        // Retrieve the user from the database
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // Verify the current password
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }
        // Ensure the new password and confirm password match
        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("New password and confirm password do not match");
        }
        System.out.println(user.getEmail());
        // Update the password
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user); // Save the updated user
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow();
    }
}

