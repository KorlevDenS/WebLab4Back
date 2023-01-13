package com.github.diosa.web4.services.impl;

import com.github.diosa.web4.dao.userDAO.UserDAOImpl;
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
    public User register(User dto) {
        if (userDAO.get(dto.getUsername()) != null)
            throw new ApiException("User with login: \"" + dto.getUsername() + "\" already exists",
                    Response.Status.CONFLICT);
        dto.setPassword(hashAlgorithm.makeHash(dto.getPassword()));
        return userDAO.create(dto);
    }

    @Override
    public User login(User dto) {
        User user = userDAO.get(dto.getUsername());
        if (user == null || !user.getPassword().equals(this.hashAlgorithm.makeHash(dto.getPassword())))
            throw new ApiException("Incorrect login or password",
                    Response.Status.UNAUTHORIZED);
        return user;
    }

    @Override
    public String issueToken(String username) {
        Key key = keyGenerator.generateKey();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
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

