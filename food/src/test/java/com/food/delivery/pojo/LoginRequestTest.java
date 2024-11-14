package com.food.delivery.pojo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoginRequestTest {

    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        loginRequest = new LoginRequest();
    }

    @Test
    void testGetUsername() {
        String expectedUsername = "abc12";
        loginRequest.setUsername(expectedUsername);
        assertEquals(expectedUsername, loginRequest.getUsername());
    }

    @Test
    void testSetUsername() {
        String username = "abc12";
        loginRequest.setUsername(username);
        assertEquals(username, loginRequest.getUsername());
    }

    @Test
    void testGetPassword() {
        String expectedPassword = "password123";
        loginRequest.setPassword(expectedPassword);
        assertEquals(expectedPassword, loginRequest.getPassword());
    }

    @Test
    void testSetPassword() {
        String password = "newPassword";
        loginRequest.setPassword(password);
        assertEquals(password, loginRequest.getPassword());
    }

    @Test
    void testLoginRequestConstructor() {
        String username = "admin";
        String password = "adminPass";
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);

        assertEquals(username, loginRequest.getUsername());
        assertEquals(password, loginRequest.getPassword());
    }
}
