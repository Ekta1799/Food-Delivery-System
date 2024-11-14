package com.food.delivery.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class StatusTest {

    @Test
    void testEnumValues() {
        assertEquals(Status.PREPARING, Status.valueOf("PREPARING"));
        assertEquals(Status.OUT_FOR_DELIVERY, Status.valueOf("OUT_FOR_DELIVERY"));
        assertEquals(Status.DELIVERED, Status.valueOf("DELIVERED"));
    }

    @Test
    void testEnumOrdinal() {
        assertEquals(0, Status.PREPARING.ordinal());
        assertEquals(1, Status.OUT_FOR_DELIVERY.ordinal());
        assertEquals(2, Status.DELIVERED.ordinal());
    }

    @Test
    void testEnumName() {
        assertEquals("PREPARING", Status.PREPARING.name());
        assertEquals("OUT_FOR_DELIVERY", Status.OUT_FOR_DELIVERY.name());
        assertEquals("DELIVERED", Status.DELIVERED.name());
    }

    @Test
    void testEnumValuesLength() {
        assertEquals(5, Status.values().length);
    }

    @Test
    void testEnumValueOf() {
        Status status = Status.valueOf("DELIVERED");
        assertEquals(Status.DELIVERED, status);
    }

    @Test
    void testEnumInvalidValueOf() {
        assertThrows(IllegalArgumentException.class, () -> Status.valueOf("INVALID_STATUS"));
    }
}
