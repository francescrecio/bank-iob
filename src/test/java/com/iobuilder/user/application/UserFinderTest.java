package com.iobuilder.user.application;

import com.iobuilder.user.domain.User;
import com.iobuilder.user.domain.UserRepository;
import com.iobuilder.user.domain.exception.UserNotExistsException;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@QuarkusTest
public class UserFinderTest {

    @InjectMock
    UserRepository userRepository;

    @Inject
    UserFinder userFinder;

    @Test
    void testFinderUser_givenUsername_whenExists_thenReturnUser() {
        User userExpected = User.builder().username("fran").build();
        when(userRepository.findByUsername(Mockito.any())).thenReturn(userExpected);

        UserDTO user = userFinder.find("fran");

        assertThat(userExpected.getUsername(), is(user.getUsername()));
    }

    @Test
    void testFinderUser_givenUsername_whenNotExists_thenThrowUserNotExistsException() {
        when(userRepository.findByUsername(Mockito.any())).thenReturn(null);

        try {
            userFinder.find("fran");
        } catch (UserNotExistsException userNotExistsException) {
            assertThat(userNotExistsException.getMessage(), is("User not exists"));
        }
    }
}
