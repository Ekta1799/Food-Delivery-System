package com.food.delivery.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

class UserTest {

    @InjectMocks
    private User user;

    @Mock
    private Role role;

    @Mock
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User("testuser", "testuser@example.com", "password123");
    }

    @Test
    void testUserCreation() {
        // Assert that the user has the correct username, email, and password
        assertEquals("testuser", user.getUsername());
        assertEquals("testuser@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
    }

    @Test
    void testUserRoleAssociation() {
        // Create a new role and associate it with the user
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        assertEquals(1, user.getRoles().size());
        assertTrue(user.getRoles().contains(role));
    }

    @Test
    void testAddMultipleRoles() {
        // Creating a set of roles and adding them to the user
        Set<Role> roles = new HashSet<>();
        Role role1 = new Role(ERole.ROLE_CUSTOMER);
        Role role2 = new Role(ERole.ROLE_ADMIN);
        roles.add(role1);
        roles.add(role2);

        user.setRoles(roles);

        assertEquals(2, user.getRoles().size());
        assertTrue(user.getRoles().contains(role1));
        assertTrue(user.getRoles().contains(role2));
    }

    @Test
    void testGetUserId() {
        // Test ID getter and setter
        user.setId(1L);
        assertEquals(1L, user.getId());
    }

    @Test
    void testAddAndRemoveRole() {
        // Test adding and removing a role
        Role customerRole = new Role(ERole.ROLE_CUSTOMER);
        user.getRoles().add(customerRole);
        assertTrue(user.getRoles().contains(customerRole));

        user.getRoles().remove(customerRole);
        assertFalse(user.getRoles().contains(customerRole));
    }

    @Test
    void testEmailFormat() {
        // Test for valid email format
        String validEmail = "valid@example.com";
        String invalidEmail = "invalid-email";

        user.setEmail(validEmail);
        assertTrue(user.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"));

        user.setEmail(invalidEmail);
        assertFalse(user.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"));
    }

    @Test
    void testUsernameFormat() {
        // Test for valid username format (non-empty)
        String validUsername = "validuser";
        String invalidUsername = "";

        user.setUsername(validUsername);
        assertTrue(user.getUsername().length() > 0);

        user.setUsername(invalidUsername);
        assertTrue(user.getUsername().length() == 0);
    }
}
