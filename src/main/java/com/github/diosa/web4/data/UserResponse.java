package com.github.diosa.web4.data;

import com.github.diosa.web4.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    private String token;
    private User user;
}
