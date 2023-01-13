package com.github.diosa.web4.filter;

import com.github.diosa.web4.secure.KeyGenerator;
import io.jsonwebtoken.Jwts;
import org.json.JSONObject;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.security.Key;


@Provider
@JWTTokenNeeded
@Priority(Priorities.AUTHENTICATION)
public class JWTTokenNeededFilter implements ContainerRequestFilter {

    @Inject
    private KeyGenerator keyGenerator;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        String token = authorizationHeader.substring("Bearer".length()).trim();
        java.util.Base64.Decoder decoder = java.util.Base64.getUrlDecoder();
        String[] parts = token.split("\\.");
        String payloadJson = new String(decoder.decode(parts[1]));
        JSONObject jsonObject = new JSONObject(payloadJson);
        String name = jsonObject.getString("sub");
        requestContext.setProperty("username", name);

        try {
            Key key = keyGenerator.generateKey();
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);

        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}
