package com.korolevs.weblab.web4.secure;

import io.jsonwebtoken.Jwts;
import org.json.JSONObject;

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

    @Override
    public String decodeKey(String authorizationHeader) {
        try {
            String token = authorizationHeader.substring("Bearer".length()).trim();
            java.util.Base64.Decoder decoder = java.util.Base64.getUrlDecoder();
            String[] parts = token.split("\\.");
            String payloadJson = new String(decoder.decode(parts[1]));
            JSONObject jsonObject = new JSONObject(payloadJson);
            Key key = this.generateKey();
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);

            return jsonObject.getString("sub");
        } catch (Exception e) {
            return null;
        }
    }
}