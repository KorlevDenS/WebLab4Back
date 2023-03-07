package com.korolevs.weblab.web4.secure;
import java.security.Key;


public interface KeyGenerator {
    Key generateKey();

    String decodeKey(String authorizationHeader);
}
