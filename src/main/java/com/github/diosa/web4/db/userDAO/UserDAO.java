package com.github.diosa.web4.db.userDAO;

import com.github.diosa.web4.models.User;

import javax.ejb.Stateless;

@Stateless
public interface UserDAO {
    User create(User dto);

    User updateAutheticated(User user);

    void delete(User user);

    User get(int id);

    User get(String username);

    User get(String username, String password);
}
