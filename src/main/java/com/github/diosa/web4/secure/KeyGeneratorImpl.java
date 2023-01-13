package com.github.diosa.web4.secure;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;


public class KeyGeneratorImpl implements KeyGenerator {

    @Override
    public Key generateKey() {
        String keyString = "da_trash";
        return new SecretKeySpec(
                keyString.getBytes(),
                0,
                keyString.getBytes().length,
                "DES");
    }
}