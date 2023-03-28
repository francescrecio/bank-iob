package com.iobuilder.user.application;

import com.iobuilder.user.domain.User;
import com.iobuilder.user.domain.UserRepository;
import com.iobuilder.user.domain.exception.UserNotExistsException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class UserFinder {

    @Inject
    UserRepository userRepository;

    public UserDTO find(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotExistsException("User not exists");
        }

        return UserDTO.builder().id(user.getId()).username(user.getUsername()).build();
    }
}
