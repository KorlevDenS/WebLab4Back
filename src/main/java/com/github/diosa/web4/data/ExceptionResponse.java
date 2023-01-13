package com.github.diosa.web4.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ExceptionResponse {
    private String type;
    private String message;
}
