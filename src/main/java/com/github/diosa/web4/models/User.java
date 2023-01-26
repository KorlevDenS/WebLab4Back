package com.github.diosa.web4.models;

import lombok.*;

import javax.persistence.*;
import javax.ws.rs.DefaultValue;

@Entity
@Table(name = "user")
@NamedQueries({
        @NamedQuery(name = User.FIND_BY_USERNAME_PASSWORD, query = "SELECT u FROM User u WHERE u.username = :username and u.password = :password"),
        @NamedQuery(name = User.FIND_BY_USERNAME, query = "SELECT u FROM User u WHERE u.username = :username ")
})
@Data
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@EqualsAndHashCode
public class User {
    public static final String FIND_BY_USERNAME_PASSWORD = "User.findByUserAndPassword";
    public static final String FIND_BY_USERNAME = "User.findByUser";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;
    @Column(unique = true)
    private String username;
    private String password;

}


