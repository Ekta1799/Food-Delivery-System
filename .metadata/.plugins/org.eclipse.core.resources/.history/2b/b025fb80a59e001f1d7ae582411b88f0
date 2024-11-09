package com.food.delivery.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginRequestTest {

    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        loginRequest = new LoginRequest();
    }

    @Test
    void testGetUsername() {
        // Set a username and check if the getter returns the correct value
        String expectedUsername = "johnDoe";
        loginRequest.setUsername(expectedUsername);
        assertEquals(expectedUsername, loginRequest.getUsername());
    }

    @Test
    void testSetUsername() {
        // Test the setter method for username
        String username = "janeDoe";
        loginRequest.setUsername(username);
        assertEquals(username, loginRequest.getUsername());
    }

    @Test
    void testGetPassword() {
        // Set a password and check if the getter returns the correct value
        String expectedPassword = "password123";
        loginRequest.setPassword(expectedPassword);
        assertEquals(expectedPassword, loginRequest.getPassword());
    }

    @Test
    void testSetPassword() {
        // Test the setter method for password
        String password = "newPassword";
        loginRequest.setPassword(password);
        assertEquals(password, loginRequest.getPassword());
    }

    @Test
    void testLoginRequestConstructor() {
        // Test the constructor by creating a new LoginRequest and setting both fields
        String username = "admin";
        String password = "adminPass";
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);

        assertEquals(username, loginRequest.getUsername());
        assertEquals(password, loginRequest.getPassword());
    }
}
