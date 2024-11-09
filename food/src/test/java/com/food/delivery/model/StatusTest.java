package com.food.delivery.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class StatusTest {

    @Test
    void testEnumValues() {
        // Verify all enum values are correctly defined
        assertEquals(Status.PREPARING, Status.valueOf("PREPARING"));
        assertEquals(Status.OUT_FOR_DELIVERY, Status.valueOf("OUT_FOR_DELIVERY"));
        assertEquals(Status.DELIVERED, Status.valueOf("DELIVERED"));
    }

    @Test
    void testEnumOrdinal() {
        // Verify the ordinal value of each enum constant
        assertEquals(0, Status.PREPARING.ordinal());
        assertEquals(1, Status.OUT_FOR_DELIVERY.ordinal());
        assertEquals(2, Status.DELIVERED.ordinal());
    }

    @Test
    void testEnumName() {
        // Verify the name of each enum constant
        assertEquals("PREPARING", Status.PREPARING.name());
        assertEquals("OUT_FOR_DELIVERY", Status.OUT_FOR_DELIVERY.name());
        assertEquals("DELIVERED", Status.DELIVERED.name());
    }

    @Test
    void testEnumValuesLength() {
        // Verify the number of enum values
        assertEquals(3, Status.values().length);
    }

    @Test
    void testEnumValueOf() {
        // Test if valueOf correctly returns enum for a valid value
        Status status = Status.valueOf("DELIVERED");
        assertEquals(Status.DELIVERED, status);
    }

    @Test
    void testEnumInvalidValueOf() {
        // Test if valueOf throws IllegalArgumentException for an invalid value
        assertThrows(IllegalArgumentException.class, () -> Status.valueOf("INVALID_STATUS"));
    }
}
