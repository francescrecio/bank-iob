package com.iobuilder.user.application;

import com.iobuilder.shared.infrastructure.security.PasswordManager;
import com.iobuilder.user.domain.User;
import com.iobuilder.user.domain.UserRepository;
import com.iobuilder.user.domain.exception.UserAlreadyExistsException;

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
    public UserDTO create(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            throw new UserAlreadyExistsException("User already exists");
        }

        user = userRepository.create(User.builder()
               .username(username)
               .password(passwordManager.encodePassword(password))
               .build());

       return UserDTO.builder().id(user.getId()).username(username).build();
    }
}
