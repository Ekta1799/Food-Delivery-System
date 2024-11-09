package com.food.delivery.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ERoleTest {

    @Test
    void testEnumValues() {
        // Verify that the enum contains all expected roles
        ERole[] expectedRoles = ERole.values();
        assertEquals(4, expectedRoles.length);
        assertTrue(contains(expectedRoles, ERole.ROLE_CUSTOMER));
        assertTrue(contains(expectedRoles, ERole.ROLE_RESTAURANT_OWNER));
        assertTrue(contains(expectedRoles, ERole.ROLE_DELIVERY_PERSONNEL));
        assertTrue(contains(expectedRoles, ERole.ROLE_ADMIN));
    }

    @Test
    void testEnumName() {
        // Verify that each role has the correct name
        assertEquals("ROLE_CUSTOMER", ERole.ROLE_CUSTOMER.name());
        assertEquals("ROLE_RESTAURANT_OWNER", ERole.ROLE_RESTAURANT_OWNER.name());
        assertEquals("ROLE_DELIVERY_PERSONNEL", ERole.ROLE_DELIVERY_PERSONNEL.name());
        assertEquals("ROLE_ADMIN", ERole.ROLE_ADMIN.name());
    }

    @Test
    void testEnumOrdinal() {
        // Verify the ordinal values of the enum constants
        assertEquals(0, ERole.ROLE_CUSTOMER.ordinal());
        assertEquals(1, ERole.ROLE_RESTAURANT_OWNER.ordinal());
        assertEquals(2, ERole.ROLE_DELIVERY_PERSONNEL.ordinal());
        assertEquals(3, ERole.ROLE_ADMIN.ordinal());
    }

    // Helper method to check if the enum contains a specific value
    private boolean contains(ERole[] array, ERole value) {
        for (ERole role : array) {
            if (role == value) {
                return true;
            }
        }
        return false;
    }
}