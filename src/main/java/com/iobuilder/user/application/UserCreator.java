package com.iobuilder.user.application;

import com.iobuilder.shared.infrastructure.security.PasswordManager;
import com.iobuilder.user.domain.User;
import com.iobuilder.user.domain.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class UserCreator {

    @Inject
    UserRepository userRepository;

    @Inject
    PasswordManager passwordManager;

    @Transactional
    public User create(String username, String password) {
       User user = userRepository.create(User.builder()
               .username(username)
               .password(passwordManager.encodePassword(password))
               .build());

       return user;
    }
}
