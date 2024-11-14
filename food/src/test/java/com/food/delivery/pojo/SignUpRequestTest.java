package com.food.delivery.pojo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SignUpRequestTest {

    private SignUpRequest signUpRequest;

    @BeforeEach
    void setUp() {
        signUpRequest = new SignUpRequest();
    }

    @Test
    void testGetAndSetUsername() {
        String username = "abc12";
        signUpRequest.setUsername(username);
        assertEquals(username, signUpRequest.getUsername());
    }

    @Test
    void testGetAndSetEmail() {
        String email = "abc12.example.com";
        signUpRequest.setEmail(email);
        assertEquals(email, signUpRequest.getEmail());
    }

    @Test
    void testGetAndSetPassword() {
        String password = "securePassword123";
        signUpRequest.setPassword(password);
        assertEquals(password, signUpRequest.getPassword());
    }

    @Test
    void testGetAndSetRole() {
        String role = "USER";
        signUpRequest.setRole(role);
        assertEquals(role, signUpRequest.getRole());
    }
}
