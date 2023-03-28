package com.iobuilder.user.application;

import com.iobuilder.shared.infrastructure.security.JWTManager;
import com.iobuilder.shared.infrastructure.security.PasswordManager;
import com.iobuilder.user.domain.User;
import com.iobuilder.user.domain.UserRepository;
import com.iobuilder.user.domain.exception.UserAlreadyExistsException;
import com.iobuilder.user.domain.exception.UserNotExistsException;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.smallrye.common.constraint.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@QuarkusTest
public class UserLoginTest {

    @InjectMock
    UserRepository userRepository;

    @Inject
    PasswordManager passwordManager;

    @Inject
    UserLogin userLogin;

    @Test
    void testLoginUser_givenUserPassword_whenExists_thenReturnToken() {
        User user = User.builder().username("fran").password(passwordManager.encodePassword("password")).build();
        when(userRepository.login(Mockito.any(), Mockito.any())).thenReturn(user);

        String token = userLogin.login("fran","password");
        Assert.assertNotNull(token);
    }

    @Test
    void testLoginUser_givenUserPassword_whenNotExists_thenThrowUserNotExistsException() {
        when(userRepository.login(Mockito.any(), Mockito.any())).thenReturn(null);

        try {
            userLogin.login("fran","password");
        } catch (UserNotExistsException userNotExistsException) {
            assertThat(userNotExistsException.getMessage(), is("User or password is not correct"));
        }
    }

}
