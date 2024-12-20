package com.food.delivery.pojo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserRequestTest {

    private UserRequest userRequest;

    @BeforeEach
    void setUp() {
        userRequest = new UserRequest();
    }

    @Test
    void testGetAndSetUsername() {
        String username = "testUser";
        userRequest.setUsername(username);
        assertEquals(username, userRequest.getUsername());
    }

    @Test
    void testGetAndSetPassword() {
        String password = "testPassword";
        userRequest.setPassword(password);
        assertEquals(password, userRequest.getPassword());
    }
}
