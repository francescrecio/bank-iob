package com.iobuilder.user.domain;

public interface UserRepository {

    User create(User user);

    User login(String username, String password);

    User findByUsername(String username);

}
