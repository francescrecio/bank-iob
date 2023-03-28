package com.iobuilder.user.application;

import com.iobuilder.shared.infrastructure.security.JWTManager;
import com.iobuilder.shared.infrastructure.security.PasswordManager;
import com.iobuilder.user.domain.User;
import com.iobuilder.user.domain.UserRepository;
import com.iobuilder.user.domain.exception.UserNotExistsException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class UserLogin {

    @Inject
    UserRepository userRepository;

    @Inject
    PasswordManager passwordManager;

    @Inject
    JWTManager jwtManager;

    @Transactional
    public String login(String username, String password) {
        User user = userRepository.login(username, passwordManager.encodePassword(password));
        if (user == null) {
            throw new UserNotExistsException("User or password is not correct");
        }

        return jwtManager.generateToken(username);
    }
}
