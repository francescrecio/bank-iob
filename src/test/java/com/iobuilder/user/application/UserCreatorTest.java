package com.iobuilder.user.application;

import com.iobuilder.user.domain.User;
import com.iobuilder.user.domain.UserRepository;
import com.iobuilder.user.domain.exception.UserAlreadyExistsException;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import javax.inject.Inject;

@QuarkusTest
public class UserCreatorTest {

    @InjectMock
    UserRepository userRepository;

    @Inject
    UserCreator userCreator;

    @Test
    void testCreateUser_givenUserPassword_whenIsCorrect_thenReturnUser() {
        User user = User.builder().build();
        UserDTO userDTO = UserDTO.builder().username("pepe").build();
        when(userRepository.create(Mockito.any())).thenReturn(user);

        UserDTO userCreated = userCreator.create("pepe","password");

        assertThat(userDTO.getUsername(), is(userCreated.getUsername()));
    }

    @Test
    void testCreateUser_givenUserPassword_whenUserAlreadyExists_thenThrowUserAlreadyExistsException() {
        User user = User.builder().build();
        when(userRepository.findByUsername(Mockito.any())).thenReturn(user);

        try {
            userCreator.create("pepe","password");
        } catch (UserAlreadyExistsException userAlreadyExistsException) {
            assertThat(userAlreadyExistsException.getMessage(), is("User already exists"));
        }
    }
}
