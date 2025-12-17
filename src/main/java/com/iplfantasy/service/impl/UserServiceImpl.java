package com.iplfantasy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iplfantasy.entity.User;
import com.iplfantasy.entity.UserType;
import com.iplfantasy.repository.UserRepository;
import com.iplfantasy.service.UserService;

@Service
@Transactional

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public User register(String username, String email, String plainPassword) {

        if (repo.findByEmail(email) != null || repo.findByUsername(username) != null) {
            return null;
        }

        User user = User.builder()
                .username(username)
                .email(email)
                .password(encoder.encode(plainPassword))
                .userType(UserType.USER)
                .build();

        repo.save(user);
        return user;
    }

    @Override
    public User login(String username, String plainPassword) {
        User user = repo.findByUsername(username);
        if (user == null) return null;

        return encoder.matches(plainPassword, user.getPassword()) ? user : null;
    }

    @Override
    public User getById(Long id) {
        return repo.findById(id);
    }

    @Override
    public User getByUsername(String username) {
        return repo.findByUsername(username);
    }

    @Override
    public boolean adminExists() {
        return repo.findAdmin() != null;
    }

    @Override
    public void createUser(String username, String email, String password, String userType) {

        if (repo.findByUsername(username) != null) return;

        User user = User.builder()
                .username(username)
                .email(email)
                .password(encoder.encode(password))
                .userType(UserType.valueOf(userType))
                .build();

        repo.save(user);
    }
}
