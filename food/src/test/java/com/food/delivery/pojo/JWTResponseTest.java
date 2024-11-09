package com.food.delivery.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JWTResponseTest {

    private JWTResponse jwtResponse;
    private List<String> roles;

    @BeforeEach
    void setUp() {
        roles = Arrays.asList("ROLE_USER", "ROLE_ADMIN");
        jwtResponse = new JWTResponse("sampleToken", 1L, "johnDoe", "johndoe@example.com", roles);
    }

    @Test
    void testConstructor() {
        // Verify that the constructor correctly initializes the values
        assertEquals("sampleToken", jwtResponse.getAccessToken());
        assertEquals("Bearer", jwtResponse.getTokenType());
        assertEquals(1L, jwtResponse.getId());
        assertEquals("johnDoe", jwtResponse.getUsername());
        assertEquals("johndoe@example.com", jwtResponse.getEmail());
        assertEquals(roles, jwtResponse.getRoles());
    }

    @Test
    void testAccessToken() {
        String newToken = "newToken";
        jwtResponse.setAccessToken(newToken);
        assertEquals(newToken, jwtResponse.getAccessToken());
    }

    @Test
    void testTokenType() {
        String newType = "JWT";
        jwtResponse.setTokenType(newType);
        assertEquals(newType, jwtResponse.getTokenType());
    }

    @Test
    void testId() {
        Long newId = 2L;
        jwtResponse.setId(newId);
        assertEquals(newId, jwtResponse.getId());
    }

    @Test
    void testUsername() {
        String newUsername = "janeDoe";
        jwtResponse.setUsername(newUsername);
        assertEquals(newUsername, jwtResponse.getUsername());
    }

    @Test
    void testEmail() {
        String newEmail = "janedoe@example.com";
        jwtResponse.setEmail(newEmail);
        assertEquals(newEmail, jwtResponse.getEmail());
    }
}
