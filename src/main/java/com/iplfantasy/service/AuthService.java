package com.iplfantasy.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iplfantasy.entity.User;
import com.iplfantasy.entity.UserType;
import com.iplfantasy.repository.UserRepository;

@Service
@Transactional
public class AuthService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public boolean createAdmin(String username, String email, String plainPassword) {

        if (repo.findAdmin() != null) {
            return false;
        }

        User user = User.builder()
                .username(username)
                .email(email)
                .password(encoder.encode(plainPassword))
                .userType(UserType.ADMIN)
                .createdAt(LocalDateTime.now())
                .build();

        repo.save(user);
        return true;
    }

    public void createDefaultAdminIfNotExists() {
        if (repo.findAdmin() != null) {
            return; 
        }

        String defaultUsername = "admin";
        String defaultEmail = "admin@iplfantasy.com";
        String defaultPassword = "admin123"; // Default password

        if (repo.findByUsername(defaultUsername) != null || 
            repo.findByEmail(defaultEmail) != null) {
            System.out.println("Default admin username/email already exists as regular user. Skipping default admin creation.");
            return;
        }

        User admin = User.builder()
                .username(defaultUsername)
                .email(defaultEmail)
                .password(encoder.encode(defaultPassword))
                .userType(UserType.ADMIN)
                .createdAt(LocalDateTime.now())
                .build();

        repo.save(admin);
        System.out.println("Default admin created successfully!");
        System.out.println("Username: admin");
        System.out.println("Password: admin123");
        System.out.println("Please change the password after first login!");
    }

    public User login(String username, String password) {

        User user = repo.findByUsername(username);
        if (user == null) return null;

        return encoder.matches(password, user.getPassword()) ? user : null;
    }
}
