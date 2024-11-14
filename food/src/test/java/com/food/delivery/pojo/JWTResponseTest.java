package com.food.delivery.pojo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JWTResponseTest {

    private JWTResponse jwtResponse;
    private List<String> roles;

    @BeforeEach
    void setUp() {
        roles = Arrays.asList("ROLE_USER", "ROLE_ADMIN");
        jwtResponse = new JWTResponse("sampleToken", 1L, "abc1", "abc12@example.com", roles);
    }

    @Test
    void testConstructor() {
        assertEquals("sampleToken", jwtResponse.getAccessToken());
        assertEquals("Bearer", jwtResponse.getTokenType());
        assertEquals(1L, jwtResponse.getId());
        assertEquals("abc1", jwtResponse.getUsername());
        assertEquals("abc12@example.com", jwtResponse.getEmail());
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
        String newUsername = "jane";
        jwtResponse.setUsername(newUsername);
        assertEquals(newUsername, jwtResponse.getUsername());
    }

    @Test
    void testEmail() {
        String newEmail = "jane@example.com";
        jwtResponse.setEmail(newEmail);
        assertEquals(newEmail, jwtResponse.getEmail());
    }
}
