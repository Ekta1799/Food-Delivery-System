package com.food.delivery.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    private Role role;

    @BeforeEach
    void setUp() {
        role = new Role();
    }

    @Test
    void testSetAndGetId() {
        role.setId(1);
        assertEquals(1, role.getId());
    }

    @Test
    void testSetAndGetName() {
        role.setName(ERole.ROLE_CUSTOMER);
        assertEquals(ERole.ROLE_CUSTOMER, role.getName());
    }

    @Test
    void testRoleConstructor() {
        Role newRole = new Role(ERole.ROLE_ADMIN);
        assertEquals(ERole.ROLE_ADMIN, newRole.getName());
    }
}
