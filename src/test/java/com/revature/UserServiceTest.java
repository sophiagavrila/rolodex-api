package com.revature;

import com.revature.data.UserRepository;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllUsers() {
        // arrange
        User user1 = new User();
        user1.setUsername("user1");

        User user2 = new User();
        user2.setUsername("user2");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        // act
        List<User> users = userService.findAll().stream().collect(Collectors.toList());

        // assert
        assertEquals(2, users.size());
        assertEquals("user1", users.get(0).getUsername());
        assertEquals("user2", users.get(1).getUsername());
    }

    @Test
    public void testGetUserById() {
        // arrange
        User user = new User();
        user.setUsername("user1");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // act
        User result = userService.getById(1);

        // assert
        assertEquals("user1", result.getUsername());
    }

    @Test
    public void testGetById_UserNotFound() {
        // arrange
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // act and assert
        UserNotFoundException ex = assertThrows(UserNotFoundException.class, () -> userService.getById(1));
        assertEquals("No user found with id 1", ex.getMessage());
    }


    @Test
    public void testCreateUser() {
        // arrange
        User user = new User();
        user.setUsername("user1");

        when(userRepository.save(user)).thenReturn(user);

        // act
        User result = userService.add(user);

        // assert
        assertEquals("user1", result.getUsername());
    }
}