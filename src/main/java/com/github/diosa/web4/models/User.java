package com.github.diosa.web4.models;

import lombok.*;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = User.FIND_BY_USERNAME_PASSWORD, query = "SELECT u FROM User u WHERE u.username = :username and u.password = :password"),
        @NamedQuery(name = User.FIND_BY_USERNAME, query = "SELECT u FROM User u WHERE u.username = :username ")
})
@Data
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class User {
    public static final String FIND_BY_USERNAME_PASSWORD = "User.findByUserAndPassword";
    public static final String FIND_BY_USERNAME = "User.findByUser";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private boolean authenticated;


}


