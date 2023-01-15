package com.github.diosa.web4.services.impl;

import com.github.diosa.web4.db.userDAO.UserDAOImpl;
import com.github.diosa.web4.exceptions.ApiException;
import com.github.diosa.web4.models.User;
import com.github.diosa.web4.secure.KeyGenerator;
import com.github.diosa.web4.secure.PasswordHash;
import com.github.diosa.web4.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

public class UserServiceImpl implements UserService {
    @Inject
    private UserDAOImpl userDAO;
    @Inject
    private PasswordHash hashAlgorithm;
    @Inject
    private KeyGenerator keyGenerator;

    @Override
    public User register(User user) {
        if (userDAO.get(user.getUsername()) != null)
            throw new ApiException("Пользователь с логином: " + user.getUsername() + " уже существует",
                    Response.Status.CONFLICT);
        return userDAO.create(
                user.toBuilder()
                        .password(hashAlgorithm.makeHash(user.getPassword()))
                        .build());
    }

    @Override
    public User login(User dto) {
        User user = userDAO.get(dto.getUsername(), hashAlgorithm.makeHash(dto.getPassword()));
        System.out.println(user);
        if (user == null) throw new ApiException("Неправильный логин или пароль",
                Response.Status.UNAUTHORIZED);
        else if (user.isAuthenticated())
            throw new ApiException("Пользователь уже вошел в систему", Response.Status.CONFLICT);
        return user;
    }

    @Override
    public User logout(String username) {
        User user = userDAO.get(username);
        System.out.println(user);
        if (user != null && user.isAuthenticated())
            return this.userDAO.updateAutheticated(user.toBuilder()
                    .authenticated(false)
                    .build());
        throw new ApiException("Пользователь не вошел в систему", Response.Status.CONFLICT);

    }

    @Override
    public String issueToken(String username) {
        Key key = keyGenerator.generateKey();
        return Jwts.builder()
                .setSubject(username)
//                .setIssuedAt(new Date())
                //.setExpiration(toDate(LocalDateTime.now().plusMinutes(15L)))
                .setExpiration(Date.from(Instant.ofEpochSecond(4622470422L)))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();

    }

    @Override
    public User getUser(String username) {
        return this.userDAO.get(username);
    }
}

