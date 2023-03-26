package com.iobuilder.user.infrastructure;

import com.iobuilder.user.domain.User;
import com.iobuilder.user.domain.UserRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class H2UserRepository implements UserRepository, PanacheRepositoryBase<User, String> {

    @Override
    public User create(User user) {
        persistAndFlush(user);
        return user;
    }

    @Override
    public User login(String username, String password) {
        User user =  find("username = :username and password = :password",
                Parameters.with("username", username).and("password", password)).firstResult();

        return user;
    }

    @Override
    public User findByUsername(String username) {
        User user =  find("username = :username",
                Parameters.with("username", username)).firstResult();

        return user;
    }
}
