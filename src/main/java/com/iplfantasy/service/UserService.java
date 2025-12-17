package com.iplfantasy.service;

import com.iplfantasy.entity.User;

public interface UserService {

    User register(String username, String email, String plainPassword);

    User login(String username, String plainPassword);

    User loginByEmail(String email, String plainPassword);

    User getById(Long id);

    User getByUsername(String username);

    boolean adminExists();

    void createUser(String username, String email, String password, String userType);
}
