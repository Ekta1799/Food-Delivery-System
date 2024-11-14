package com.food.delivery.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import com.food.delivery.exception.GeneralException;
import com.food.delivery.model.User;
import com.food.delivery.repository.UserRepository;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUsername("ekta");
        user.setPassword("ekta1");
    }

    @Test
    void testSaveUser() {

        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertNotNull(savedUser);
        assertEquals("ekta", savedUser.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testFindUserByUsername_UserFound() {

        String username = "ekta";
        when(userRepository.findByUsername(username)).thenReturn(java.util.Optional.of(user));

        User foundUser = userService.findUserByUsername(username);

        assertNotNull(foundUser);
        assertEquals("ekta", foundUser.getUsername());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void testFindUserByUsername_UserNotFound() {

        String username = "non_existing_user";
        when(userRepository.findByUsername(username)).thenReturn(java.util.Optional.empty());

        GeneralException exception = assertThrows(GeneralException.class, () -> {
            userService.findUserByUsername(username);
        });

        assertEquals("User not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void testExistsByUsername_UserExists() {

        String username = "ekta";
        when(userRepository.findByUsername(username)).thenReturn(java.util.Optional.of(user));

        Boolean exists = userService.existsByUsername(username);

        assertTrue(exists);
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void testExistsByUsername_UserNotExists() {

        String username = "non_existing_user";
        when(userRepository.findByUsername(username)).thenReturn(java.util.Optional.empty());

        Boolean exists = userService.existsByUsername(username);

        assertFalse(exists);
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void testUpdatePassword() {
    	String newPassword = "new_password";
        String username = "ekta";
        userService.updatePassword(newPassword, username);

        verify(userRepository, times(1)).updatePassword(newPassword, username);
    }
}
