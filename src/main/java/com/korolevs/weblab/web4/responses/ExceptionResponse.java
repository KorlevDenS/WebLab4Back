package com.korolevs.weblab.web4.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ExceptionResponse {
    private String type;
    private String message;
}
