package com.iobuilder.shared.infrastructure.security;

import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.jwt.Claims;

import javax.enterprise.context.ApplicationScoped;
import java.util.Arrays;
import java.util.HashSet;

@ApplicationScoped
public class JWTManager {

    public String generateToken(String username) {
        String token = Jwt.issuer("https://example.com/issuer")
                        .upn(username)
                        .groups(new HashSet<>(Arrays.asList("User")))
                        .claim(Claims.birthdate.name(), "2001-07-13")
                        .sign();

        return token;
    }
}
