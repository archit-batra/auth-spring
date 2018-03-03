package com.hello.auth.service;

import com.hello.auth.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
