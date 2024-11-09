package com.food.delivery.pojo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RestaurantProfileResourceTest {

    private RestaurantProfileResource restaurantProfileResource;

    @BeforeEach
    void setUp() {
        // Initialize the RestaurantProfileResource object before each test
        restaurantProfileResource = new RestaurantProfileResource();
    }

    @Test
    void testGetAndSetFirstname() {
        // Test setter and getter for firstname
        String firstname = "John";
        restaurantProfileResource.setFirstname(firstname);
        assertEquals(firstname, restaurantProfileResource.getFirstname());
    }

    @Test
    void testGetAndSetLastname() {
        // Test setter and getter for lastname
        String lastname = "Doe";
        restaurantProfileResource.setLastname(lastname);
        assertEquals(lastname, restaurantProfileResource.getLastname());
    }

    @Test
    void testGetAndSetUsername() {
        // Test setter and getter for username
        String username = "john_doe_restaurant";
        restaurantProfileResource.setUsername(username);
        assertEquals(username, restaurantProfileResource.getUsername());
    }

    @Test
    void testGetAndSetRestaurantName() {
        // Test setter and getter for restaurant_name
        String restaurantName = "Pizza Palace";
        restaurantProfileResource.setRestaurant_name(restaurantName);
        assertEquals(restaurantName, restaurantProfileResource.getRestaurant_name());
    }

    @Test
    void testGetAndSetPhoneNo() {
        // Test setter and getter for phone_no
        String phoneNo = "123-456-7890";
        restaurantProfileResource.setPhone_no(phoneNo);
        assertEquals(phoneNo, restaurantProfileResource.getPhone_no());
    }

    @Test
    void testGetAndSetAddress() {
        // Test setter and getter for address
        String address = "123 Pizza Street, Food City";
        restaurantProfileResource.setAddress(address);
        assertEquals(address, restaurantProfileResource.getAddress());
    }

    @Test
    void testGetAndSetHoursOfOperation() {
        // Test setter and getter for hours_of_operation
        int hoursOfOperation = 12;
        restaurantProfileResource.setHours_of_operation(hoursOfOperation);
        assertEquals(hoursOfOperation, restaurantProfileResource.getHours_of_operation());
    }
}
