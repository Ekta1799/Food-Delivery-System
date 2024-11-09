package com.food.delivery.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RestaurantOwnerProfileTest {

    private RestaurantOwnerProfile restaurantOwnerProfile;

    @BeforeEach
    void setUp() {
        restaurantOwnerProfile = new RestaurantOwnerProfile();
    }

    @Test
    void testSetAndGetId() {
        restaurantOwnerProfile.setId(1L);
        assertEquals(1L, restaurantOwnerProfile.getId());
    }

    @Test
    void testSetAndGetFirstname() {
        restaurantOwnerProfile.setFirstname("John");
        assertEquals("John", restaurantOwnerProfile.getFirstname());
    }

    @Test
    void testSetAndGetLastname() {
        restaurantOwnerProfile.setLastname("Doe");
        assertEquals("Doe", restaurantOwnerProfile.getLastname());
    }

    @Test
    void testSetAndGetUsername() {
        restaurantOwnerProfile.setUsername("john_doe");
        assertEquals("john_doe", restaurantOwnerProfile.getUsername());
    }

    @Test
    void testSetAndGetRestaurantName() {
        restaurantOwnerProfile.setRestaurant_name("Foodies");
        assertEquals("Foodies", restaurantOwnerProfile.getRestaurant_name());
    }

    @Test
    void testSetAndGetPhoneNo() {
        restaurantOwnerProfile.setPhone_no("1234567890");
        assertEquals("1234567890", restaurantOwnerProfile.getPhone_no());
    }

    @Test
    void testSetAndGetAddress() {
        restaurantOwnerProfile.setAddress("123 Main St, Springfield");
        assertEquals("123 Main St, Springfield", restaurantOwnerProfile.getAddress());
    }

    @Test
    void testSetAndGetHoursOfOperation() {
        restaurantOwnerProfile.setHours_of_operation(10);
        assertEquals(10, restaurantOwnerProfile.getHours_of_operation());
    }

    @Test
    void testSetAndGetUserId() {
        restaurantOwnerProfile.setUser_id(5L);
        assertEquals(5L, restaurantOwnerProfile.getUser_id());
    }
}
