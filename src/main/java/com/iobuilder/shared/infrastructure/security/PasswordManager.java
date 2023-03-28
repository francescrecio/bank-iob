package com.iobuilder.shared.infrastructure.security;

import javax.enterprise.context.ApplicationScoped;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@ApplicationScoped
public class PasswordManager {

    /**
     * Here would apply a strong encoder, but is only for test purposes
     */
    private final String salt = "xktlkle8z2mlisd";

    public String encodePassword(String password) {
        String encodedPassword = Base64.getEncoder().encodeToString(password.concat(salt).getBytes(StandardCharsets.UTF_8));

        return encodedPassword;
    }

}
