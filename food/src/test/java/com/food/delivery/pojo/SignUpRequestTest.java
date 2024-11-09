package com.food.delivery.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SignUpRequestTest {

    private SignUpRequest signUpRequest;

    @BeforeEach
    void setUp() {
        // Initialize the SignUpRequest object before each test
        signUpRequest = new SignUpRequest();
    }

    @Test
    void testGetAndSetUsername() {
        // Test setter and getter for username
        String username = "john_doe";
        signUpRequest.setUsername(username);
        assertEquals(username, signUpRequest.getUsername());
    }

    @Test
    void testGetAndSetEmail() {
        // Test setter and getter for email
        String email = "john.doe@example.com";
        signUpRequest.setEmail(email);
        assertEquals(email, signUpRequest.getEmail());
    }

    @Test
    void testGetAndSetPassword() {
        // Test setter and getter for password
        String password = "securePassword123";
        signUpRequest.setPassword(password);
        assertEquals(password, signUpRequest.getPassword());
    }

    @Test
    void testGetAndSetRole() {
        // Test setter and getter for role
        String role = "USER";
        signUpRequest.setRole(role);
        assertEquals(role, signUpRequest.getRole());
    }
}
