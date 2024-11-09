package com.food.delivery.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.food.delivery.model.User;
import com.food.delivery.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Initializing a User object to be used in the tests
        user = new User();
        user.setUsername("john_doe");
        user.setPassword("password");
    }

    @Test
    void testLoadUserByUsername_UserExists() {
        // Arrange
        String username = "john_doe";
        when(userRepository.findByUsername(username)).thenReturn(java.util.Optional.of(user));

        // Act
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);

        // Assert
        assertNotNull(userDetails);
        assertEquals("john_doe", userDetails.getUsername());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        // Arrange
        String username = "non_existing_user";
        when(userRepository.findByUsername(username)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername(username);
        });

        assertEquals("User Not Found with username: non_existing_user", exception.getMessage());
        verify(userRepository, times(1)).findByUsername(username);
    }
}
