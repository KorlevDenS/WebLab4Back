package com.github.diosa.web4.services;

import com.github.diosa.web4.models.User;

import javax.ejb.Stateless;

@Stateless
public interface UserService {
    User register(User dto);

    User login(User dto);

    User logout(String username);

    String issueToken(String username);

    User getUser(String username);
}
