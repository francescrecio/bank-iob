package com.iobuilder.shared.infrastructure.security;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PasswordManager {

    public String encodePassword(String password) {
        String encodedPassword = password + 123;

        return encodedPassword;
    }

}
