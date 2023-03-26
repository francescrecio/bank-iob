package com.iobuilder.user.application;

import com.iobuilder.user.domain.User;
import com.iobuilder.user.domain.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class UserFinder {

    @Inject
    UserRepository userRepository;

    public User find(String username) {
        User user = userRepository.findByUsername(username);

        return user;
    }
}
